'''
Created on 12 dec. 2017

@author: USER
'''
import unittest

class Concert():
    def __init__(self,idC,locatie,pret,nrPers):
        """
        Initializeaza un obiect de tip Concert
        """
        self.__id=idC
        self.__locatie=locatie
        self.__pret=pret
        self.__nrPres=nrPers
        
    def getId(self):
        """
        Getter pentru id concert
        :return : self.__id (numar intreg)
        """
        return self.__id
    
    def getLocatie(self):
        """
        Getter pentru locatie concert
        :return: self.__locatie (string)
        """
        return self.__locatie
    
    def getPret(self):
        """
        Getter pentru pret bilet
        :return: self.__pret (numar real)
        """
        return self.__pret
    
    def getNrPers(self):
        """
        Getter pentru numarul de persoane
        :return: self.__nrPers (numar intreg)
        """
        return self.__nrPres


class ConcertDOTS():
    def __init__(self,concert):
        """
        Initializeaza un obiect de tip ConcertDTOS
        """
        self.__id=concert.getId()
        self.__locatie=concert.getLocatie()
        self.__pret=concert.getPret()
        self.__nrPers=concert.getNrPers()
        self.__pretTotal=0
        
    def getId(self):
        """
        Getter pentru id
        :return:  self.__id(numar intreg)
        """  
        return self.__id
      
    def getLocatie(self):
        """
        Getter pentru locatie
        :return: self.__locatie (string)
        """
        return self.__locatie
    
    def getPretTotal(self):
        """
        Getter pentru pretul total
        :return: self.__pretTotal (numar real)
        """
        self.__pretTotal=self.__nrPers * self.__pret
        return self.__pretTotal
    
    

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.concert=Concert(111,'Cluj Arena',11,20)
        self.event=ConcertDOTS(self.concert)
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testConcert(self):
        self.assertEqual(self.concert.getId(), 111, "Id incorect")
        self.assertEqual(self.concert.getLocatie(), "Cluj Arena", "Locatie incorecta")
        self.assertEqual(self.concert.getPret(), 11, "Pret incorect")
        self.assertEqual(self.concert.getNrPers(), 20, "Numar incorect de persoane")
    
    def testConcertDTOS(self):
        self.assertEqual(self.event.getPretTotal(), 220, "Pret total incorect")
        
if __name__ == '__main__':
    unittest.main()