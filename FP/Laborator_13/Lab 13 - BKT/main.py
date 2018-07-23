'''
Created on 31 dec. 2017

@author: USER
'''
'''
Se dau doua numere naturale m si n. 
Generati liste formate din numere de la 1 la n 
cu proprietatea ca diferenta (in valoare absoluta) 
intre doua numere consecutive din lista este m. 
Tipariti un mesaj daca problema nu are solutie.
'''

class InputError(Exception):
    pass

def citire():
    """
    Input data
    :return a:list
    """
    while True:
        try:
            n = int(input("n = "))
            if n<=1:
                raise ValueError
        except ValueError:
            print("Dati un numar natural >1 pentru n")
            continue
        break
    a=[n]
    while True:
        try:
            m = int(input("m = "))
            if m<=0:
                raise ValueError
            if m>=n:
                raise InputError()
        except ValueError:
            print("Dati un numar natural nenul pentru m")
            continue
        except InputError:
            print("Atentie m>=n! Problema nu are solutie.")
            continue
        break
    a.append(m)
    return a

def init(l):
    """
    Add a new element in list
    :param l: list
    """
    l.append(0)
                 
def valid(l,m,k):  
    """
    Validate the candidate solution
    :param l: list
    :param m: int
    :param k: int
    :return: bool
    """
    for j in range(0, k):
        if l[j] == l[k]:
            return False
    for j in range(0, k):
        dif = l[j] - l[j+1]
        if dif== m or dif == -m:
            continue
        else:
            return False
    return True    
     
def solutie(n, k):
    """
    Verifies if current list is one of problem's solution 
    :param l: list
    :param n: int
    :param k: int
    :return: bool
    """
    if k>0 and k<n:
        return True
    else:
        return False
       
def bkt_recursiv(l,n,m):       
    """
    Recursive BackTrack
    :param n: int
    :param m: int
    """
    if len(l)>n:
        return 
    init(l)
    for i in range(1,n+1):
        l[-1]=i
        poz=len(l)-1
        if valid(l, m, poz):
            if solutie(n, poz):
                print(l)
            bkt_recursiv(l, n, m)
    l.pop()   
         
def bkt_iterativ(l,n,m,generate_all=True):
    """
    Iterative BackTrack
    :param n: int
    :param m: int
    """
    init(l)
    while len(l)>0:
        choosed=False
        poz=len(l)-1
        while not choosed and l[-1]<n:
            l[-1]=l[-1]+1
            choosed=valid(l, m, poz)
        if choosed:
            if solutie(n, poz): 
                if generate_all is True:
                    print(l)
                else:  
                    print(l)  
                    generate_all=False
                    break
            init(l)
        else:
            l=l[:-1]
         
def main():
    """
    Entry point.
    Console UI.
    """
    while True:
        print("1. Recursiv")
        print("2. Iterativ")
        print("x. Exit")
        cmd=input("    Dati comanda: ")
        if cmd=='x':
            print("Multumim ca ati utilizat aplicatia! :)")
            break
        elif cmd=='1':
            inp=citire()
            n=inp[0]
            m=inp[1]
            bkt_recursiv([],n,m)
        elif cmd == "2":
            inp=citire()
            n=inp[0]
            m=inp[1]
            bkt_iterativ([],n,m)
        else:
            print("Comanda gresita!")
            continue
        
if __name__ == '__main__':
    main()
    
import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.l=[1,3]
        self.n=5
        self.m=2

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testInit(self):
        self.assertEqual(len(self.l), 2, "Nu se initializeaza o noua solutie")
        init(self.l)
        self.assertEqual(len(self.l), 3, "Nu se initializeaza o noua solutie")
        
    
    def testValid(self):    
        self.assertEqual(valid(self.l,self.m, len(self.l)-1), True, "Nu se valideaza lista")
        init(self.l)
        self.assertEqual(valid(self.l,self.m, len(self.l)-1), False, "Nu se valideaza lista")
        
    def testSolutie(self):
        self.assertEqual(solutie(self.n, len(self.l)-1), True, "Nu se construieste corect solutia")
        
if __name__ == '__main__':
    unittest.main()
        