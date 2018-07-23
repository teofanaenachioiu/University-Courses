'''
Created on 22 ian. 2018

@author: USER
'''
from Domain.Jucator import Jucator
from Repository.JucatorRepository import JucatorRepository

import unittest
from Service.JucatorController import JucatorController

class TestCaseJucatori(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.jucator=Jucator("Pop","Ion",186,"Fundas")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMetGetNume(self):
        self.assertEqual(self.jucator.getNume(), "Pop", "Nume incorect!")
    
    def testMetGetPrenume(self):
        self.assertEqual(self.jucator.getPrenume(), "Ion", "Prenume incorect!")
    
    def testMetGetInaltime(self):
        self.assertEqual(self.jucator.getInaltime(), 186, "Inaltime incorecta!")
    
    def testMetGetPost(self):
        self.assertEqual(self.jucator.getPost(), "Fundas", "Post incorect!")
        
    def testMetSetNume(self):
        self.assertEqual(self.jucator.setInaltime(200), 200, "Inaltimea nu a fost modificata!")

class TestCaseRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repo=JucatorRepository("testJucatori.txt","alt.txt")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMetSize(self):
        self.assertEqual(self.repo.size(), 9, "Lista nu e completa!!")
      
    def testMetStoreDelete(self):
        juc=Jucator("Ene","Gabi",200,"Fundas")
        self.assertEqual(self.repo.store(juc).getNume(), "Ene", "Jucatorul nu a fost adaugat!") 
        self.assertEqual(self.repo.delete(juc).getNume(), "Ene", "Jucatorul nu a fost sters!")

class TestCaseController(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repo=JucatorRepository("testJucatori.txt","alt.txt")
        self.controller=JucatorController(self.repo)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMetAdd(self):
        juc=Jucator("Enescu","Gabriel",200,"Fundas")
        self.assertEqual(self.controller.addJucator(juc), juc, "Jucatorul nu a fost adaugat")
        self.assertEqual(self.controller.deleteJucator(juc), juc, "Jucatorul nu a fost sters") 
        
    def testMetModifica(self):
        self.assertEqual(self.controller.modificareJucator("Dumitrescu", "Tudor", 191).getNume(), "Dumitrescu", "Inaltimea nu a fost modificata!")       
    
    def testMet1(self):
        pass
        
    def testMet(self):
        self.assertEqual(len(self.controller.veziEchipa()), 5, "Nu s-a fosrmat echipa")

    
if __name__ == '__main__':
    unittest.main()