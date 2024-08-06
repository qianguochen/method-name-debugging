path = 'F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT.csv'
result_path = 'F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT_ok.csv'
path_get = 'F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT_get.csv'
path_set = 'F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT_set.csv'
path_is = 'F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT_is.csv'


def write_line(text, write_path):
    try:
        with open(write_path, 'a') as file1:
            file1.write(text)
    except Exception as e:
        print(e)


count_set = 0
count_get = 0
count_is = 0
try:
    with open(path, 'r') as file:
        for line in file:
            params = line.split(',')[0].strip('"')
            try:
                if 'get' == params[:3]:
                    count_get += 1
                    write_line(line, path_get)
                    continue
                if 'set' in params[:3]:
                    count_set += 1
                    write_line(line, path_set)
                    continue
                if 'is' in params[:2]:
                    count_is += 1
                    write_line(line, path_is)
                    continue
                write_line(line, result_path)
            except Exception as e:
                print(e)
except Exception as e:
    print(e)
print(count_get)
print(count_set)
print(count_is)
