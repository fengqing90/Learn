'''
Created on 2018年9月5日

@author: FengQing
'''
import unittest

class TestAdd(unittest.TestCase):

    def test_add_01(self):
        self.assertEqual(1 + 2, 3)

    def test_add_02(self):
        self.assertEqual(2 + 2, 5)

    def test_add_03(self):
        self.assertEqual(3 + 3, 6)


if __name__ == '__main__':
    unittest.main()