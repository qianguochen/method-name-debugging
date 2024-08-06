# check data

count_line = 0
count_continue = 0
flag = 0
with open('C:\\Users\\25137\\PycharmProjects\\mywork\\trainSetJavaRepos400.csv', 'r', encoding='utf-8') as file:
    for line in file:
        count_line += 1
        line_method_info = line.strip().split(",", 1)
        return_type_and_parameter_line = line_method_info[1]
        return_type_and_parameter = return_type_and_parameter_line.split(";", 1)
        return_type = return_type_and_parameter[0].strip('"')
        if return_type_and_parameter_line == '"ServiceReference;String;name;SolrCore;core"':
            flag = count_line
            print(count_line)
        if count_line == flag + 1:
            print(line)

