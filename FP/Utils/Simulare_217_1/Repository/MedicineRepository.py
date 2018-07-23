'''
Created on 11 dec. 2017

@author: USER
'''
from Domain.Medicine import Medicine
import unittest

class MedicineRepository():
    def __init__(self,fileName):
        """
        Initializeaza clasa MedicineRepository. 
        :param: fileName - numele fisierului (*.txt)
        """
        self.__fileName=fileName
        self.__medicines=[]
        self.__loadFromFile()
        
    def __loadFromFile(self):
        """
        Extrage obiectele de tip Medicine din fisier si le retine in lista.
        """
        with open(self.__fileName) as f:
            line=f.readline().strip()
            while line!="":
                part=line.split(",")
                nume=part[0]
                pret=float(part[1])
                medicine=Medicine(nume,pret)
                self.__medicines.append(medicine)
                line=f.readline().strip()
    
    def getAll(self):
        """
        Getter pentru toate obiectele de tip Medicine
        :return: self.__medicines - lista de obiecte de tip Medicine
        """
        return self.__medicines
    
    def findByName(self,name):
        """
        Cauta daca medicamentul cu numele dat este in fisier
        :param: name - string
        :return: medicine - obiect de tip Medicine
        """
        for medicine in self.__medicines:
            if name in medicine.getName():
                return medicine
        return None

class TestCase(unittest.TestCase):

    def setUp(self):
        self.repo=MedicineRepository("medicines.txt")
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testGetAll(self):
        self.assertEqual(len(self.repo.getAll()), 3, "Lista incorecta de obiecte de tip Medicine")

    def testFindByName(self):
        self.assertEqual(self.repo.findByName("Nurofen").getName(), "Nurofen 200mg", "Lista incorecta de obiecte")
        
if __name__ == '__main__':
    unittest.main()
    