'''
Created on 12 dec. 2017

@author: USER
'''
from Domain.Apartament import Apartament
class InputError(Exception):
    pass


class ApartamentRepository():
    def __init__(self,fileName):
        """
        Initializeaza un obiect de tip ApartamentRepository
        :param fileName: fisier (*.txt)
        """
        self.__fileName=fileName
        self.__apartamente=[]
        self.__loadFromFile()
        
    def __loadFromFile(self):
        """
        Preia toate obiectele de tip Apartament din memorie si le pune in lista de apartamente
        """
        with open(self.__fileName) as f:
            linie=f.readline().strip()
            while linie!="":
                part=linie.split(",")
                nrApart=int(part[0])
                numeFamilie=part[1]
                nrMembri=int(part[2])
                apartement=Apartament(nrApart,numeFamilie,nrMembri)
                self.__apartamente.append(apartement)
                linie=f.readline().strip()
    
    def findByNr(self, nrApartament):
        """
        Cauta un obiect de tip Apartament dupa nr
        :param nrApartament: numar intreg 
        :return apartament: obiect de tip Apartament
        :raise InputError: nrApartament nu a fost gasit
        """
        for apartament in self.__apartamente:
            if apartament.getNrApartament()==nrApartament:
                return apartament
        else:
            raise InputError()
    
    def getAll(self):
        """
        Getter pentru toata lista de apartamente
        :return apartamente: lista de obiecte de tip Apartamente
        """
        return self.__apartamente
    
import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repo=ApartamentRepository("test.txt")
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMet1(self):
        self.assertEqual(self.repo.findByNr(18).getNumeFamilie(), "Manole", "Numar apartament invalid")
    
    def testMet2(self):
        self.assertEqual(len(self.repo.getAll()),10, "Lista invalida")

if __name__ == '__main__':
    unittest.main()