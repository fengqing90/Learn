'''
Created on 2018年9月5日

@author: FengQing
'''
import unittest
# from test2 import test.testcase2
# from test.Testcase2 import *
from test.DataUtils import *


class Testcase1(unittest.TestCase):

    def testName(self):
        data = DataUtils.getData(self)
        print(data['name'])
    
    def testAge(self):
        data = DataUtils.getData(self)
        print(data['age'])

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
