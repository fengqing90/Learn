# coding=utf-8
from selenium import webdriver

testdriver5 = webdriver.Chrome();
testdriver5.maximize_window();
testdriver5.implicitly_wait(6);

testdriver5.get("https://www.baidu.com/")

# assert(a==100 and b==36);

# print(111)
# except Exception as e:
#     print('Exception found',format(e));