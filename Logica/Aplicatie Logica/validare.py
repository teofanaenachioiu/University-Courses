'''
Created on 13 feb. 2018

@author: USER
'''
class NumarIncorectError(Exception):
    '''
    Se defineste o noua exceptie.
    Eroarea apare in cazul in care o cifra componenta a numarului este mai mare sau egala cu baza.
    '''   
    pass
 
class CifraError(Exception):
    '''
    Se defineste o noua exceptie.
    Eroarea apare in cazul in care se introduce mai mult de o cifra de la tastatura.
    ''' 
    pass

class ScadereError(Exception):
    '''
    Se defineste o noua exceptie.
    Eroarea apare in cazul in care nu se poate efectua operatia de scadere.
    '''
    pass

class BazaError(Exception):
    '''
    Se defineste o noua exceptie.
    Eroarea apare in cazul in care pentru baza se da un numar mai mic decat 2
    '''
    pass
    
def validareBaza(baza):
    '''
    Se valideaza numarul introdus pentru baza
    :param baza: numar natural >1
    :raise BazaError: numarul este mai mic sau egal cu 1
    '''
    if baza<=1:
        raise BazaError
    
def validare(baza,numar):
    '''
    Se valideaza datele de intrare
    :param baza: numar natural >1
    :param numar: string
    :raise NumarIncorectError: string-ul introdus nu poate reprezenta un numar
    '''
    index=0
    while index<len(numar):  
        cifra=numar[index]  
        if not (cifra>='a' and cifra<='z' or cifra>='A' and cifra<='Z' or  cifra>='0' and cifra<='9'):
            raise NumarIncorectError
        if cifra>='a' and cifra<='z':
            cifra=ord(cifra)
            cifra=cifra-87
        elif cifra>='A' and cifra<='Z':
            cifra=ord(cifra)
            cifra=cifra-54
        elif  cifra>='0' and cifra<='9':
            cifra=ord(cifra)
            cifra=cifra-48
        if int(cifra)>=baza:
            raise NumarIncorectError    
        index+=1
        
def validareCifra(cifra):
    '''
    Se valideaza o cifra introdusa de la tastatura
    :raise CifraError: numarul dat nu contine doar o cifra
    '''
    if len(cifra)>1 or cifra[0]=='0':
        raise CifraError    
    
def validareScadere(numar1,numar2):
    '''
    Se verifica daca se pot scadea cele doua numare
    :raise ScadereError: Descazutul este mai mic decat scazatorul
    :param numar1,numar2: lista de cifre
    '''
    if len(numar1)<len(numar2):
        raise ScadereError
    elif len(numar1)==len(numar2):
        for i in range(0,len(numar1)):
            if numar1[i]<numar2[i]:
                raise ScadereError
            if numar1[i]>numar2[i]:
                break