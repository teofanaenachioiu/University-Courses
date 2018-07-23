'''
Created on 12 dec. 2017

@author: USER
'''
from Repository.RepositoryConcerte import ConcerteRepository
from Domain.Concert import ConcertDOTS

class ServiceConcerte():
    def __init__(self,repo):
        """
        Initializeaza un obiect de tip ServiceConcerte
        """
        self.__repo=repo
        
    def ShowConcerte(self,locatie):
        """
        Cauta toate concertele ce au o anumita locatie(citita)
        :param: locatie (string)
        :return: rezultat (lista de obiecte de tip locatie)
        """
        concerte=self.__repo.getAll()
        rezultat=[]
        for concert in concerte:
            if concert.getLocatie() == locatie:
                rezultat.append(concert)
        return rezultat

    def ReportConcerte(self):
        """
        Creare raport.
        :return lista de obiecte ConcertDOTS
        """
        concerte=self.__repo.getAll()
        rezultat=[]
        for concert in concerte:
            newObj=ConcertDOTS(concert)
            rezultat.append(newObj)
        rezultat.sort(key=lambda k: k.getPretTotal(), reverse=True)
        return rezultat
        
import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repo=ConcerteRepository("test.txt")
        self.service=ServiceConcerte(self.repo)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMet1(self):
        self.assertEqual(len(self.service.ShowConcerte("Cluj Arena")), 2, "Lista incorecta de concerte")

    def testMet(self):
        self.assertEqual(len(self.service.ReportConcerte()), 6, "Lista incorecta")
        self.assertEqual(self.service.ReportConcerte()[0].getPretTotal(), 5000, "Lista incorecta")
        
if __name__ == '__main__':
    unittest.main()