'''
Created on 12 dec. 2017

@author: USER
'''
from Domain.Plata import Plata
from Repository.ApartamentRepository import ApartamentRepository, InputError

class ApartamentService():
    def __init__(self,repo):
        self.__repo=repo
        
    def sortByFamiliyName(self):
        """
        Sorteaza lista de apartamente dupa numele de familie
        :return aparatmante: lista de obiecte de tip Apartament
        """
        apartamente=self.__repo.getAll()
        apartamente.sort(key=lambda k: k.getNumeFamilie())
        return apartamente

    def plata(self,nrApartament,ultilitati):
        """
        Genereaza obiectul de tip Plata pentru un anumit apartament
        :param nrApartament: numar intreg
        :param utilitati: lista de numare intregi
        :return plata: obiect de tip Plata
        """
        apartament=self.__repo.findByNr(nrApartament)
        plata=Plata(apartament,ultilitati)
        return plata
    
import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repo=ApartamentRepository("test.txt")
        self.service=ApartamentService(self.repo)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMet1(self):
        self.assertEqual(self.service.sortByFamiliyName()[0].getNumeFamilie(), "Corduneanu", "Lista nu a fost sortata")
    
    def testMet2(self):
        self.assertEqual(self.service.plata(15, [20,23,200]).getSumaTotala(), 530, "Plata incorecta")
        self.assertRaises(InputError,self.service.plata,30,[20,23,200])

if __name__ == '__main__':
    unittest.main()