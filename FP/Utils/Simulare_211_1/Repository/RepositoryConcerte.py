'''
Created on 12 dec. 2017

@author: USER
'''
from Domain.Concert import Concert 
class ConcerteRepository():
    def __init__(self,fileName):
        """
        Initializeaza un obiect de tip ConcerteRepository
        """
        self.__fileName=fileName
        self.__listaConcerte=[]
        self.__loadFromFile()
        
    def __loadFromFile(self):
        """
        Preia toate datele din fisier si le pune in lista concertelor.
        """
        try:
            f=open(self.__fileName,"r")
        except IOError:
            self.__listaConcerte=[]
        linie=f.readline().strip()
        while linie!="":
            part=linie.split(",")
            concert=Concert(int(part[0]),part[1],float(part[2]),int(part[3]))
            self.__listaConcerte.append(concert)
            linie=f.readline().strip()
        f.close()
                
    def getAll(self):
        """
        Getter pentru toata lista de concerte
        :return: self.__listaConcerte (lista de obiecte de tip Concert)
        """
        return self.__listaConcerte
    
import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repo=ConcerteRepository("test.txt")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMet1(self):
        self.assertEqual(len(self.repo.getAll()), 5, "Dimensiune incorecta")

if __name__ == '__main__':
    unittest.main()