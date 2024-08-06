import re

path = 'C:\\Users\\25137\\PycharmProjects\\mywork\\total_result_1.csv'
path2 = 'F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT.csv'
pattern1 = r'(?<=\s)\w+(?=[(])'  # public / void methodName()
pattern2 = r'\w+(?=[(])'  # methodName()
pattern3 = r'\w+(?=[.])'  # methodName.
pattern4 = r'["][,]["]'
pattern5 = r'(?<=["])\w+(?=["])'
pattern6 = r'(?<=[`])\w+(?=[`])'
pattern7 = r'(?<=\s)\w+'
pattern8 = r'\w+[<][T][>](?=[(])'
pattern9 = r'^[0-9a-zA-Z_<>]+$'


def write_line(text):
    try:
        with open(path2, 'a') as file1:
            file1.write(text)
    except Exception as e:
        print(e)


# 当left中有空格和小括号的时候
def strategy_pattern(pattern, left, right):
    try:
        method_name = re.search(pattern, left).group()
        new_line = '"' + method_name + '",' + right
        return [new_line, pattern]
    except Exception as e:
        print(pattern)
        print(left+right)


def split_line(text):
    left = text.split('","')[0] + '"'
    left = left.strip('"')
    right = '"' + text.split('","')[1]
    return [left, right]


def extract(text):
    info = split_line(text)
    left = info[0]
    right = info[1]

    if 'void ' in left:  # left 格式为 'void method_name'
        return strategy_pattern(pattern7, left, right)
    if '`' in left:  # left 格式含有` 特殊符号
        return strategy_pattern(pattern6, left, right)
    if ' method ' in left:  # left 格式为 method name could be "method_name"
        left = left + '"'
        return strategy_pattern(pattern5, left, right)
    if 'public ' in left or ' void' in left:  # left 格式为 public / void methodName()
        if ':' not in left:
            return strategy_pattern(pattern1, left, right)
    if '.' in left and 'Entry' not in left and '...' not in left:  # left 格式为 method_name.
        return strategy_pattern(pattern3, left, right)
    if '<T>(' in left:
        return strategy_pattern(pattern8, left, right)
    if '(' in left:  # left 格式为 method_name(...)
        return strategy_pattern(pattern2, left, right)
    if re.match(pattern9, left) is not None:
        return [text, pattern9]
    return [text, None]

# try:
#     result = extract('"getValueAt(int columnIndex, Class<T> type)","T;int;columnIndex;Class<T>;type"')
#     print(result)
# except Exception as e:
#     print(e)
#     print(result[0])
#     print(result[1])
# 泛型切片 第二个逗号
# Sorry
count = 0
try:
    with open(path, 'r') as file:
        for line in file:
            try:
                if 'import ' in line or 'def ' in line:
                    count += 1
                    continue
                if 'Sorry' in line or 'sorry' in line:
                    count += 1
                    continue
                if '"' not in line:
                    count += 1
                    continue
                if 'return' in line:
                    count += 1
                    continue
                if 'AI language' in line:
                    count += 1
                    continue
                if 'information' in line:
                    count += 1
                    continue
                if 'list of parameters' in line:
                    count += 1
                    continue
                if 'not possible to predict' in line:
                    count += 1
                    continue
                if 'apologize' in line:
                    count += 1
                    continue
                if 'withError' in line:
                    count += 1
                    continue
                if 'Parameters' in line:
                    count += 1
                    continue
                if line.count('"') <= 1:
                    count += 1
                    continue
                result_line = extract(line)
                if result_line[1] is None:
                    count += 1
                    continue
                write_line(result_line[0])
                pattern = result_line[1]
            except Exception as e:
                print(e)
except Exception as e:
    print(e)
#
print(count)
# line = 'void decryptSymmetricKey(String symmetricKeyPassword'
# line2 = 'isInstance(Object o)'
# line3 = 'writeMacroWithTLDs.'
# line4 = '"void applyMappings(Map<String, String> mappings)","void;Map<String, String>;mappings"'
# line5 = '"divideNumbers","double;double;n1;double;n2"'
# m = re.search(pattern3, line3)
# m2 = re.split(pattern4, line4)
# print(m.group())
# print(m2[0])
# print(m2[1])
# print(line5.split(','))
