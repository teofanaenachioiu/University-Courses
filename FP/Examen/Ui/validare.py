'''
Created on 22 ian. 2018

@author: USER
'''
class InputError(Exception):
    '''
    Defineste o eroare de input
    '''
    def __init__(self,errors):
        '''
        Initializeaza o lista de erori
        :param errors: lista (de stringuri)
        '''
        self.__errors=errors
        
    def getErrors(self):
        '''
        Getter erori
        '''
        return self.__errors
        
    
class Validare():
    '''
    Valideaza datele de intrare
    '''
    def valid(self,jucator):
        '''
        Valideaza datele introduse pentru un jucator
        :param jucator: obiect de tip jucator
        '''
        errors=[]
        if jucator.getNume()=="":
            errors.append("Numele nu poate fi vid.")
        if jucator.getNume()<'A' or jucator.getNume()>'z':
            errors.append("Numele contine doar litere.")
            
        if jucator.getPrenume()=="":
            errors.append("Prenumele nu poate fi vid.")
        if jucator.getPrenume()<'A' or jucator.getPrenume()>'z':
            errors.append("Prenumele contine doar litere.")
        if jucator.getInaltime()>270 or jucator.getInaltime()<=0:
            errors.append("Inaltimea e un numar strict pozitiv, mai mic decat 270.")
        if jucator.getPost() not in ["Fundas","Pivot","Extrema"]:
            errors.append("Alegeti postul din urmatoarele disponibile: Fundas,Pivot,Extrema")
        if len(errors)>0:
            raise InputError(errors)