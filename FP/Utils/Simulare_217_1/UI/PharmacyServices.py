'''
Created on 11 dec. 2017

@author: USER
'''
from Repository.MedicineRepository import MedicineRepository
from Domain.Recipt import Recipe
from Domain.Medicine import Medicine
import unittest

class PharmacyServices():
    def __init__(self,repo):
        """
        Initializeaza service-ul cu elementele din fisier
        :param: repo - clasa MedicineRepository din Repository
        """
        self.__repo=repo
    
    def lookUp(self,name):
        """
        Cauta medicament in lista de medicamente
        :param: name - string
        """
        medicines=[]
        for item in self.__repo.getAll():
            if name in item.getName():
                medicines.append(item)
        return medicines
    
    def getAll(self): #e in plus
        """
        Getter pentru toate medicamentele
        """
        return self.__repo.getAll()
    
    def sort(self):
        listaOb=self.__repo.getAll()
        newlist=sorted(listaOb, key=lambda k: k.getPrice())
        return newlist
            
    
    def createRecipe(self,medicines):
        """
        Creaza reteta pe baza unei liste de medicamente
        :param: medicines - lista de medicamente
        """
        meds=Recipe(medicines)
        return meds

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repo=MedicineRepository("medicines.txt")
        self.pharma=PharmacyServices(self.repo)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testLookUp(self):
        self.assertEqual(len(self.pharma.lookUp("Nurofen")), 2, "Lista incorecta")
    
    def testCreateRecipe(self):
        medicines=[Medicine("Nurofen 200mg",24.50)]
        self.pharma.createRecipe(medicines)
        self.assertEqual(self.pharma.createRecipe(medicines).getTotalPrice(), 24.50, "Reteta invalida")

if __name__ == '__main__':
    unittest.main()