'''
Created on 13 feb. 2018

@author: USER
'''
import unittest
from operatii import inmultire, impartire, conversieImpartiri, adunare, conversieSubstitutie, conversie2to4,conversie2to8,conversie2to16,conversie16to2,conversie4to2,conversie8to2,\
    scadere, zerorizare, eliminareZeroNesemnificativ
from validare import validare, NumarIncorectError, CifraError, validareCifra, ScadereError, validareScadere
from domain import Numar,Rezultat

class TestCase(unittest.TestCase):
    '''
    Se testeaza functiile implementate
    '''
    def setUp(self):
        unittest.TestCase.setUp(self)
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAdunare(self):
        self.assertEqual(adunare([1,15],[1,1],16), [3,0], "Adunare incorecta!")
    
    def testScadere(self):
        self.assertEqual(scadere([15,1],[7],16), [14,10], "Scadere incorecta!")    
        
    def testInmultire(self):
        self.assertEqual(inmultire([8,2],5,9), [4,5,1], "Inmultire incorecta!")

    def testImpartire(self):
        self.assertEqual(impartire([8,2],5,9)['cat'], [1,5], "Cat incorect!")
        self.assertEqual(impartire([8,2],5,9)['rest'], [4], "Rest incorect!")
    
    def testZerorizare(self):
        self.assertEqual(zerorizare([1,5,4],2), [0,0,1,5,4], "Nu s-a zerorizat corespunzator!")
        
    def testEliminareZeroNeseminificativ(self):
        self.assertEqual(eliminareZeroNesemnificativ([0,0,1,5,4]), [1,5,4], "Nu s-au eliminat corect zerourile!")    
        self.assertEqual(eliminareZeroNesemnificativ([0,0,0]), [0], "Nu s-au eliminat corect zerourile!")  
        
    def testConversieImpartire(self):
        self.assertEqual(conversieImpartiri([8,2],9,5), [2,4,4], "Conversie incorecta!")
        
    def testConversieSubstitutie(self):
        self.assertEqual(conversieSubstitutie([1,0,1,0],2,10), [1,0], "Conversie incorecta!")
        self.assertEqual(conversieSubstitutie([1,0,0],10,16), [6,4], "Conversie incorecta!")      
        
    def testConversie2to4(self):
        self.assertEqual(conversie2to4([1,0,1]), [1,1], "Conversie incorecta!")    
     
    def testConversie2to8(self):
        self.assertEqual(conversie2to8([1,1,0,1]), [1,5], "Conversie incorecta!")  
    
    def testConversie2to16(self):
        self.assertEqual(conversie2to16([1,0,1,0,1]), [1,5], "Conversie incorecta!") 
    
    def testConversie4to2(self):
        self.assertEqual(conversie4to2([1,1]), [1,0,1], "Conversie incorecta!")    
     
    def testConversie8to2(self):
        self.assertEqual(conversie8to2([1,5]), [1,1,0,1], "Conversie incorecta!")  
    
    def testConversie16to2(self):
        self.assertEqual(conversie16to2([1,5]), [1,0,1,0,1], "Conversie incorecta!") 
        
    def testValidare(self):
        self.assertRaises(NumarIncorectError,validare,8,'19')
    
    def testValidareCifra(self):
        self.assertRaises(CifraError,validareCifra,'19')
            
    def testValidareScadere(self):
        self.assertRaises(ScadereError, validareScadere, [1,5,4],[1,5,5])        
            
    def testNumar(self):
        nr=Numar('18a5g')
        self.assertEqual(nr.getNumar(), [1,8,10,5,16], "Numar incorect!")
        self.assertEqual(nr.getCifraNumar(1), 8, "Cifra incorecta!")
        
    def testCifra(self):
        cifra=Numar('a')
        self.assertEqual(cifra.getCifra(), 10, "Cifra incorecta!")
        
    def testRezultat(self):
        numar=Rezultat([15,1,9,10])
        self.assertEqual(numar.getNumar(), 'F19A', "Numar incorect creat!")    
        
if __name__ == '__main__':
    unittest.main()