'''
Created on 11 dec. 2017

@author: USER
'''
import unittest

class Medicine():
    def __init__(self,name,price):
        """
        Initializeaza un obiect de tip Medicine
        :param: name - string
        :param: price - float
        """
        self.__name=name
        self.__price=price
        
    def getName(self):
        """
        Getter pentru numele medicamentului
        :return: self.__name
        """
        return self.__name
    
    def getPrice(self):
        """
        Getter pentru pretul medicamentului
        :return: self.__price
        """
        return self.__price
    

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.med=Medicine("Nurofen",17.50)
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMedicineGetName(self):
        self.assertEqual(self.med.getName(), "Nurofen", "Numele e incorect")
        
    def testMedicineGetPrice(self):
        self.assertEqual(self.med.getPrice(), 17.50, "Pretul e incorect")
    
if __name__ == '__main__':
    unittest.main()   