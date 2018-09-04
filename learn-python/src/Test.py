'''
Created on 2018年8月14日

@author: FengQing
'''

from log import logger
counter = 100 # 赋值整型变量
miles = 1000.0 # 浮点型
name = "John" # 字符串
 
# print (counter)
# print (miles)
# print (name)


print('[{\"sync_id\":\"00fs8ynv8\",\"position_in_folder\":1}]')
logger.error("1111")


s = 'here is a sample of english text' ;

s=" abc abc aaabbc"
d = {} 
for c in s: 
    d[c] = (d[c] + 1) if (c in d) else (1) 
print (d)
