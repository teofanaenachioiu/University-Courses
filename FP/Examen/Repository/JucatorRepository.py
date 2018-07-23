'''

@author: USER
'''
from Domain.Jucator import Jucator
import random
class JucatorRepository():
    '''
    Defineste un obiect de tip JucatorRepository
    '''
    def __init__(self,numeFisir,altfisier):
        '''
        Initializeaza un obiect de tip JucatorRepository
        :param numeFisier: fisier text (*.txt)
        '''
        self.__numeFisier=numeFisir
        self.__altFisier=altfisier
        self.__jucatori=[]
        self.__loadFromFile()
        
    def __loadFromFile(self):
        '''
        Importa date despre jucatori din fisier
        '''
        with open(self.__numeFisier,"r") as f:
            linie=f.readline().strip()
            while linie!="":
                parts=linie.split(",")
                jucator=Jucator(parts[0],parts[1],int(parts[2]),parts[3])
                self.__jucatori.append(jucator)
                linie=f.readline().strip()
    
    def __storeInFile(self):
        '''
        Exporta datele in fisier
        '''
        with open(self.__numeFisier,"w") as f:
            for jucator in self.__jucatori:
                juc=jucator.getNume()+","+jucator.getPrenume()+","+str(jucator.getInaltime())+","+jucator.getPost()+"\n"
                f.write(juc)
    
    def store(self,jucator):
        '''
        Adauga un nou jucator in fisier
        :param jucator: obiect de tip jucator
        :return jucator: un obiect de tip jucator
        '''
        self.__jucatori.append(jucator)
        self.__storeInFile()
        return jucator
        
        
    def delete(self,jucator):
        '''
        Sterge un jucator din fisier
        :param jucator: obiect de tip jucator
        :return jucator: un obiect de tip jucator
        '''
        self.__jucatori.remove(jucator)
        self.__storeInFile()
        return jucator
    
    def search(self,jucator):
        '''
        Cauta un jucator in fisier
        :param jucator: obiect de tip jucator
        :return: jucator
        :raise: JucatorNotFound daca nu exista jucatorul in fisier
        '''
        gasit=False
        for item in self.__jucatori:
            if item == jucator:
                return item
        if gasit==False:
            raise JucatorNotFound
        
    def size(self):
        '''
        Getter dimensiune memorie
        :return: un numar reprezentand dimensiunea memoriei
        '''
        return len(self.__jucatori)
    
    def getAll(self):
        '''
        Getter memorie
        :return: lista de obiectde de tip Jucator
        '''
        return self.__jucatori
    
    def importa(self):
        '''
        Importa jucatori din alt fisier
        '''

        listaInaltime=list(range(1,250))
        listaPost=["Fundas","Pivot","Extrema"]

        with open(self.__altFisier,"r") as g:
            linie=g.readline().strip()
            while linie!="":
                parts=linie.split(",")
                nume=parts[0]
                prenume=parts[1]
                inaltime=random.choice(listaInaltime)  
                post=random.choice(listaPost)
                
                linie=g.readline().strip()
                jucator=Jucator(nume,prenume,inaltime,post)
                try:
                    self.search(jucator)
                    continue
                except JucatorNotFound:
                    self.store(jucator)
    
class JucatorNotFound(Exception):
    pass