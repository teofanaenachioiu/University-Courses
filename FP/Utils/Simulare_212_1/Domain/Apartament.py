'''
Created on 12 dec. 2017

@author: USER
'''
class Apartament():
    def __init__(self,nrApartament,numeFamilie,nrMembri):
        """
        Initializeaza un obiect de tip Apartament
        :param nrApartament: numar intreg
        :param numeFamilie: string
        :param nrMembri: numar intreg
        """
        self.__nrApartament=nrApartament
        self.__numeFamilie=numeFamilie
        self.__nrMembri=nrMembri
        
    def getNrApartament(self):
        """
        Getter pentru nrApartament
        :return self.__nrApartament: numar intreg
        """
        return self.__nrApartament
    
    def getNumeFamilie(self):
        """
        Getter pentru numaFamilie
        :return self.__numeFamilie
        """
        return self.__numeFamilie
    
    def getNrMembri(self):
        """
        Getter pentru nrMemebri
        :return self.__nrMembri
        """
        return self.__nrMembri
    
import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.apartament=Apartament(13,"Popescu",3)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMet1(self):
        self.assertEqual(self.apartament.getNrApartament(), 13, "Numarul apartamentului e incorect")
    
    def testMet2(self):
        self.assertEqual(self.apartament.getNrMembri(), 3, "Numarul de memebri e incorect")
        
    def testMet3(self):
        self.assertEqual(self.apartament.getNumeFamilie(), "Popescu", "Numele de familie e incorect")

if __name__ == '__main__':
    unittest.main()