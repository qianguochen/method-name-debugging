import re

# str = 'getFileName","String;File;f'
# if 'import' in str:
#     print(1)
# if 'def' in str:
#     print(1)
# if 'import' or 'def' in str:
#     print(1234)
# pattern5 = r'(?<=["])\w+(?=["])'
# pattern2 = r'\w+(?=[(])'  # methodName()
# pattern9 = r'^[0-9a-zA-Z_]+$'
# line = 'isExpired(cert)'
# line3 = 'The method name could be "parseInvokeMethodArgs" or "getInvokeMethodArgs'
# line4 = 'The method name could be "printCharacter"'
# line2 = '"public Dictionary<String, Object> PredictEnhancements(EnhancementEngine engine, ContentItem ci)","Dictionary<String, Object>;EnhancementEngine;engine;ContentItem;ci"'
# line5 = '"appendSchemaToStringBuilder.","void;StringBuilder;sb;Schema;schema"'
# line6 = 'transform(List<Parameter> parameters)'
# m = re.search(pattern5, line4)
# right = line2.split('","')[0]
# print(right)
# print(m.group())
# left = line5.split('","')[0] + '"'
# left = left.strip('"')
# print(left)
# print(re.search(pattern2, line).group())
# print(re.match(pattern9, line))
# pattern1 = r'(?<=\s)\w+(?=[(])'  # public / void methodName()
# pattern2 = r'\w+(?=[(])'  # methodName()
# pattern3 = r'\w+(?=[.])'  # methodName.
# pattern4 = r'["][,]["]'
# pattern5 = r'(?<=["])\w+(?=["])'
# pattern6 = r'(?<=[`])\w+(?=[`])'
# pattern7 = r'(?<=\s)\w+'
pattern8 = r'\w+[<][T][>](?=[(])'
pattern9 = r'^[0-9a-zA-Z_<>]+$'
# print(re.match(pattern1, line))
# print(re.match(pattern2, line))
# print(re.match(pattern3, line))
# print(re.match(pattern4, line))
# print(re.match(pattern5, line))
# print(re.match(pattern6, line))
# print(re.match(pattern7, line))
# print(re.match(pattern8, line))
# print(re.match(pattern9, line))
# print(re.match(pattern9, line) is not None)
# if re.match(pattern9, line) is not None:
#     print(123)
#
#
# def check(text):
#     if re.match(pattern9, left) is not None:
#         return [text, 9]
#     else:
#         return []
#
#
# print(check(line))
print(re.match(pattern9, 'The method name could be "printCharacter"'))
print(re.search(pattern8, 'getResponseBody<T>(String uri)').group())
print(re.match(pattern9, 'filterByPredicates(Stream<T>[] streams, Predicate<? super T> predicates)') is not None)