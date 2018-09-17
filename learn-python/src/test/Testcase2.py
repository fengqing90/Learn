'''
Created on 2018年9月5日

@author: FengQing
'''
import unittest

class Testcase2(unittest.TestCase):


    def setUp(self):
        print('setUp')

    def tearDown(self):
        print('tearDown')

    def test1(self):
        print('test1')

    def test2(self):
        print('test2')

if __name__ == "__main__":
#     import sys;sys.argv = ['', 'Test.testName']
    unittest.main()