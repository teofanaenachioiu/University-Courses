'''
Created on 14 feb. 2018

@author: USER
'''
class Numar():
    '''
    Se defineste un nou tip de data - Numar
    '''
    def __init__(self,numar):
        '''
        Se initializeaz datele
        :param numar: string (numarul e pozitiv)
        '''
        self.__numar=self.__listaDeCifre(numar)
        
    def __listaDeCifre(self,numar):   
        '''
        Se creaza o lista cu ciferle numarului, pornind de la codul ascii al sirului de caractere ce reprezinta numarul.
        :param numar: string (reprezinta un numar ce poate format atat din cifre cat si din litere)
        :return lista: lista de numare naturale(corespunzatoare cifrelor numarului)
        ''' 
        lista=[]
        while len(numar)>0:  
            cifra=numar[:1]   
            if cifra>='a' and cifra<='z':
                cifra=ord(cifra)
                cifra=cifra-87
            elif cifra>='A' and cifra<='Z':
                cifra=ord(cifra)
                cifra=cifra-55
            elif  cifra>='0' and cifra<='9':
                cifra=ord(cifra)
                cifra=cifra-48
            lista.append(cifra)
            numar=numar[1:]
        return lista
    
    def getCifra(self):
        '''
        Getter pentru un numar format dintr-o singura cifra
        Este util pentru operatia de inmultire, impartire
        :return: un numar natural
        '''
        return self.__numar[0]
    
    def getNumar(self):
        '''
        Getter pentru numar
        :return: lista de numare naturale
        '''
        return self.__numar
    
    def getCifraNumar(self,poz):
        '''
        Getter pentru cifrele numarului
        :param poz: numar natural 
        :return: cifra de pe pozitia poz
        '''
        return self.__numar[poz]
    
    def size(self):
        '''
        Returneaza numarul de cifre din numar
        :return: un numar natural
        '''
        return len(self.__numar)
    
class Rezultat():
    '''
    Se defineste un nou tip de data - Rezultat
    '''
    def __init__(self,numar):
        '''
        Se initializeaza datele de intrare
        :param numar: lista de cifre
        '''
        self.__numar=self.__creareNumar(numar)
        
    def __creareNumar(self,numar):
        '''
        Se creaza un string corespunzator cifrelor numarului
        :return nr: string
        '''
        nr=''
        while len(numar)>0:
            cifra=numar[0]
            if cifra>9:
                cifra=chr(cifra+55)
            else:
                cifra=chr(cifra+48)
            nr=nr+cifra
            numar=numar[1:]
        return nr
    
    def getNumar(self):
        '''
        Getter pentru numar
        :return: string
        '''
        return self.__numar 