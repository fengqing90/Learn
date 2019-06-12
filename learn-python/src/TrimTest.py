'''
Created on 2018年9月18日

@author: FengQing
'''


# 去除收尾空格
def trim(s):
    if len(s) == 0:
        return s
    elif s[0] == ' ':
        s = s[1:]
        s = trim(s)
#     if len(s) == 0:
#         return s
    if s[-1] == ' ':
        s = s[:-1]
        s = trim(s)
    print(s)
    return s


# 测试:
if trim('hello  ') != 'hello':
    print('测试失败!')
elif trim('  hello') != 'hello':
    print('测试失败!')
elif trim('  hello  ') != 'hello':
    print('测试失败!')
elif trim('  hello  world  ') != 'hello  world':
    print('测试失败!')
elif trim('') != '':
    print('测试失败!')
elif trim('    ') != '':
    print('测试失败!')
elif trim('  hello  world  ') != 'hello  world':
    print('测试失败!')
else:
    print('测试成功!')
