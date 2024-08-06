import time
import threading
import requests
import concurrent.futures

lock = threading.Lock()
# 设置API访问参数
api_key = "sk-ADwWzUpS60bQbED46cCfF16bE44c41E9B67412Bc4b98586e"
api_url = "https://ngapi.fun/v1/chat/completions"
tread_pool = concurrent.futures.ThreadPoolExecutor(max_workers=30)
fail_dict = {}


def pars_line(line):
    line_method_info = line.strip().split(",", 1)
    return_type_and_parameter_line = line_method_info[1]
    return_type_and_parameter = return_type_and_parameter_line.split(";", 1)
    return_type = return_type_and_parameter[0].strip('"')
    parameter_list = ''
    if len(return_type_and_parameter) > 1:
        method_parameter_list = return_type_and_parameter[1].split(";")
        for word in method_parameter_list:
            parameter_list += word.strip('"') + ' '
    question = "please use returns the value type :" + return_type + " and this list of parameters: " + parameter_list + " to predict a method name,Just answer me a method name"
    return [question, return_type_and_parameter_line, return_type, return_type_and_parameter]


# 发送API请求
def send_chat_request(message, fail_line):
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {api_key}"
    }
    data = {
        "messages": [{"role": "system", "content": message}],
        "max_tokens": 60,
        'model': 'gpt-3.5-turbo',
        "temperature": 0.2
    }
    proxies = {
        "http": "http://127.0.0.1:33210",
        "https": "http://127.0.0.1:33210",
    }

    try:
        response = requests.post(api_url, headers=headers, json=data, proxies=proxies)
        response_json = response.json()
        return response_json
    except Exception:
        print("try again send")
        if fail_line not in fail_dict.keys():
            fail_dict[fail_line] = 1
        else:
            value = fail_dict[fail_line]
            fail_dict[fail_line] = value + 1
        count_fail = fail_dict[fail_line]
        if count_fail < 15:
            values = pars_line(fail_line)
            return_type_and_parameter_line = values[1]
            question = values[0]
            tread_pool.submit(task(question, return_type_and_parameter_line, fail_line))


count = 0
total_tokens = 0


def task(question, line, fail_line):
    try:
        response = send_chat_request(question, fail_line)
        result = response["choices"][0]["message"]["content"]
        token = response["usage"]["total_tokens"]
        global total_tokens
        total_tokens += token
        global count
        lock.acquire()
        count += 1
        lock.release()
        print("ChatGPT: ", result)
        try:
            with open('total_result_batch_one.csv', 'a') as file1:
                line = '"' + result + '"' + "," + '"' + line.strip('"') + '"'
                file1.write(line + "\n")
        except Exception as e:
            print("文件未找到")
            print(e)
    except Exception as e:
        print("try again task")
        if fail_line not in fail_dict.keys():
            fail_dict[fail_line] = 1
        else:
            value = fail_dict[fail_line]
            fail_dict[fail_line] = value + 1
        count_fail = fail_dict[fail_line]
        if count_fail < 15:
            values = pars_line(fail_line)
            return_type_and_parameter_line = values[1]
            question = values[0]
            tread_pool.submit(task(question, return_type_and_parameter_line, fail_line))


def read_file_lines(file_path):
    count_line = 0
    count_continue = 0
    with open(file_path, 'r', encoding='utf-8') as file:
        for line in file:
            count_line += 1
            if count_line < 1512487 or count_line > 1570000:
                continue
            global count
            values = pars_line(line)
            return_type = values[2]
            return_type_and_parameter = values[3]
            return_type_and_parameter_line = values[1]
            question = values[0]
            if return_type == "void" and len(return_type_and_parameter) == 1:
                count_continue += 1
                continue
            if count % 20 == 0:
                time.sleep(5)
            tread_pool.submit(task(question, return_type_and_parameter_line, line))
    print(count_continue)
    print("average token :", total_tokens / count)
    print("all token", total_tokens)
    print(count)


# 与ChatGPT进行对话
def chat_with_gpt():
    print("ChatGPT is ready to assist you!")
    read_file_lines("C:\\Users\\25137\\PycharmProjects\\mywork\\trainSetJavaRepos400.csv")


# 运行对话
chat_with_gpt()
