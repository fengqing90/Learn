'''
Created on 2021年1月27日

@author: FengQing
'''

#coding:utf8
'''
通配符是 shell 命令中的重要功能，
? 表示匹配任意 1 个字符，
*表示匹配 0 个或多个字符。
请使用你熟悉的编程语言实现一个字符串匹配函数，
支持 ? 和 * 通配符。如 “a?cd*d” 匹配 “abcdaccd”
'''

def solution( re_str,test_str ):
    # 如果两个字符串相等 就返回True
    if re_str == test_str :
        return True
    # 标记第一个字母
    r = re_str[0] if re_str != '' else ''
    t = test_str[0] if test_str !='' else ''
    # r 不是？ 也 不是* 的情况
    if r != '?' and r != '*' :
        if r != t :     # 如果不想相等就返回False
            return False
        else :      # 相等 就 删掉第一个单词 递归
            re_str,test_str = re_str[1:],test_str[1:]
            return solution( re_str,test_str )
    # 如果r是? 相当于匹配一个字符 都删掉一个字符 然后 递归
    if r == '?' :
        re_str, test_str = re_str[1:], test_str[1:]
        return solution(re_str, test_str)
    # 如果r是*  re 是n个*  则返回True
    if r == '*' and re_str.strip('*') == '' :
        return True
    # 否则 就是包含* ，*匹配0个字符或多个字符，所以我们返回 递归 0个匹配 与 1个匹配 的逻辑或
    return solution(re_str[1:], test_str) or solution(re_str, test_str[1:])



if __name__ == '__main__':
    re_str = "a?*cd*d*"
    test = "abcdaccd"
    res = solution( re_str,test )
    print(res)