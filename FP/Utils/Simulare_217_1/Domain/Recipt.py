'''
Created on 11 dec. 2017

@author: USER
'''
import unittest

class Recipe():
    def __init__(self,medicines):
        self.__medicines=medicines
        self.__suma=0
    
    def getTotalPrice(self):
        for medicine in self.__medicines:
            self.__suma+=medicine.getPrice()
        return self.__suma

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testReciptGetTotalPrice(self):
        pass
    
if __name__ == '__main__':
    unittest.main()   