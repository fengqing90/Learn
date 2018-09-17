'''
Created on 2018年9月5日

@author: FengQing
'''
import unittest
class DataUtils(unittest.TestCase):
    '''
    classdocs
    '''

#     def __init__(self, params):
#         '''
#         Constructor
#         '''
        
    def getData(self):
        data = {'name':DataUtils.getName(self), 'age':DataUtils.getAge(self)}
        print('@',data)
        return data;
    
    def getName(self):
        return 'FengQing'
    
    def getAge(self):
        return 10
    
if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
