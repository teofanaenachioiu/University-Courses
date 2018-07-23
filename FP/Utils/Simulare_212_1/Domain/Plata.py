'''
Created on 12 dec. 2017

@author: USER
'''
from Domain.Apartament import Apartament

class Plata():
    def __init__(self,apartament,utilitati):
        """
        Initializeaza un obiect de tip Plata
        :param apartament: obiect de tip Apartament
        :param ultilitati: lista de ce contine 3 numere intregi corespunzatoare pentru mc apa, mc gaz si intretinere
        """
        self.__apartament=apartament
        self.__ultilitati=utilitati
        
    def getApatmanet(self):
        """
        Getter pentru apartament
        :return apartament: obiect de tip Apartament
        """
        return self.__apartament   
     
    def getApa(self):
        """
        Getter mc apa
        :return utilitati[0]: numar intreg
        """
        return self.__ultilitati[0]    
        
    def getGaz(self):
        """
        Getter mc gaz
        :return utilitati[1]: numar intreg
        """
        return self.__ultilitati[1] 
    
    def getIntretinere(self):
        """
        Getter intretinere
        :return utilitati[2]: numar intreg
        """
        return self.__ultilitati[2]
        
    def getSumaTotala(self):
        """
        Getter suma totala de plata
        :return Suma: numar intreg
        """
        Suma=self.getApa()*5+self.getGaz()*10+self.getIntretinere()
        return Suma
    
import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.apartament=Apartament(13,"Popescu",3)
        self.plata=Plata(self.apartament,[23,20,200])

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMet1(self):
        self.assertEqual(self.plata.getApatmanet(), self.apartament, "Apartament invalid")
        self.assertEqual(self.plata.getApa(), 23, "Consum incorect")
        self.assertEqual(self.plata.getGaz(), 20, "Consum incorect")
        self.assertEqual(self.plata.getIntretinere(), 200, "Consum incorect")
        self.assertEqual(self.plata.getSumaTotala(), 515, "Suma incorecta")

if __name__ == '__main__':
    unittest.main()