'''
Created on 22 ian. 2018

@author: USER
'''
from Domain.Jucator import Jucator,Media


class JucatorController():
    '''
    Defineste un obiect de tip JucatorController
    '''
    def __init__(self,repo):
        '''
        Initializeaza un obiect de tip JucatorController
        :param repo: Repository
        '''
        self.__repo=repo
        
    def addJucator(self,jucator):
        '''
        Adauga un nou jucator
        :param jucator: obiect de tip jucator
        :return: jucator (jucatorul adaugat)
        '''
        return self.__repo.store(jucator)
    
    def deleteJucator(self,jucator):
        '''
        Sterge un jucator
        :param jucator: obiect de tip jucator
        :return: jucator (jucatorul sters)
        '''
        return self.__repo.delete(jucator)
    
    def modificareJucator(self,nume,prenume,inaltimeNoua):
        '''
        Modifica inaltimea unui jucator
        :param nume: string
        :param prenume: string
        :param inaltime: numar intreg >0
        :return: jucator (obiect de tip jucator)
        '''
        jucator=self.__repo.search(Jucator(nume,prenume,inaltimeNoua,"Fundas"))
        self.__repo.delete(jucator)
        jucator.setInaltime(inaltimeNoua)
        self.__repo.store(jucator)
        return jucator
    
    def getAll(self):
        '''
        Returneaza lista de jucatori
        '''
        return self.__repo.getAll()
    
    def veziEchipa(self):
        '''
        Tipareste Echipa de jucatori
        :return: rez (echipa de baschet)
        '''
        fundas=[]
        pivot=[]
        extrema=[]
        
        jucatori=self.getAll()
        for jucator in jucatori:
            if jucator.getPost()=='Fundas':
                fundas.append(jucator)
            if jucator.getPost()=='Extrema':
                extrema.append(jucator)
            if jucator.getPost()=='Pivot':
                pivot.append(jucator)
        fundas=sorted(fundas, key=lambda x:x.getInaltime(), reverse=True)
        extrema=sorted(extrema, key=lambda x:x.getInaltime(), reverse=True)
        pivot=sorted(pivot, key=lambda x:x.getInaltime(), reverse=True)   
         
        fundas=fundas[:2]
        pivot=pivot[:1]
        extrema=extrema[:2]
        
        rez=[]
        rez.extend(fundas)
        rez.extend(pivot)
        rez.extend(extrema)
        return rez
            
    def importa(self):
        '''
        Adauga noi jucatori
        '''
        
        self.__repo.importa()
