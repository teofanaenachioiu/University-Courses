'''
Created on 22 ian. 2018

@author: USER
'''
class Jucator():
    '''
    Defineste un nout tip de date - Jucator
    '''
    def __init__(self,nume,prenume,inaltime,post):
        '''
        Initializeaza un obiect de tip Jucator
        :param nume: string
        :param prenume: string
        :param inaltime: nr intreg (>0)
        :param post: string (Fundas,Pivot,Extrema)
        '''
        self.__nume=nume
        self.__prenume=prenume
        self.__inaltime=inaltime
        self.__post=post
    
    def __eq__(self,ot):
        '''
        Defineste cand doi jucatori sunt egali
        :return: True (egali)/False (diferiti)
        '''
        return self.__nume==ot.getNume() and self.__prenume==ot.getPrenume()
    
    def getNume(self):
        '''
        Getter nume jucator
        '''
        return self.__nume
    
    def getPrenume(self):
        '''
        Getter prenume jucator
        '''
        return self.__prenume
    
    def getInaltime(self):
        '''
        Getter inaltime jucator 
        '''
        return self.__inaltime
    
    def getPost(self):
        '''
        Getter post
        '''
        return self.__post
    
    
    def setInaltime(self,inaltimeNoua):
        '''
        Reseteaza inaltimea
        :param inaltimeNoua: numar intreg >0
        '''
        self.__inaltime=inaltimeNoua
        return self.__inaltime
    
class Media():
    S=0
    nr=0
    '''
    Defineste un obiect de tip Medie
    '''
    def __init__(self,nume,prenume,inaltime,post):
        '''
        Initializeaza un obiect de tip Medie
        :param nume: string
        :param prenume: string
        :param inaltime: numar intreg >0
        :param post: string
        '''
        self.__nume=nume
        self.__prenume=prenume
        self.__inaltime=inaltime
        self.__post=post
        
    def getNume(self):
        '''
        Getter nume jucator
        '''
        return self.__nume
    
    def getPrenume(self):
        '''
        Getter prenume jucator
        '''
        return self.__prenume
    
    def getInaltime(self):
        '''
        Getter inaltime jucator
        '''
        return self.__inaltime
    
    def getPost(self):
        '''
        Getter post jucator
        '''
        return self.__post
    
    def getSuma(self):
        '''
        Adauga inaltimea pentru a fi calculata
        '''
        return S+self.__inaltime
        
    def getNumar(self,nr):
        '''
        Incrementeaza
        '''
        return nr+1
      
    def getMedia(self):
        '''
        Getter inaltime medie
        '''
        return float(self.getSuma()/self.getInaltime)