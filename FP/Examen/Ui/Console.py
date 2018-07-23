'''
Created on 22 ian. 2018

@author: USER
'''
from Domain.Jucator import Jucator
from Ui.validare import InputError
from Repository.JucatorRepository import JucatorNotFound
class Console():
    '''
    Defineste un obiect de tip Console
    '''
    def __init__(self,controller,validare):
        '''
        Initializeaza un obiect de tip Console
        :param controller: obiect de tip JucatorController
        :param validare: obiect de tip Validare
        '''
        self.__controller=controller
        self.__validare=validare
        
    def __callAdauga(self):
        '''
        Adauga un jucator
        '''
        try:
            nume=input("Dati numele: ")
            prenume=input("Dati prenume: ")
            inaltime=int(input("Dati inaltimea: "))
            post=input("Dati postul: ")
            jucator=Jucator(nume,prenume,inaltime,post)
            self.__validare.valid(jucator)
            self.__controller.addJucator(jucator)
            print("Jucator adaugat!")
        except InputError as ex:
            print(ex.getErrors())
        except ValueError:
                print("Inaltimea e un numar!")
            
    def __callModifica(self):
        '''
        Modifica inaltimea unui jucator
        '''  
        try:  
            nume=input("Dati numele: ")
            prenume=input("Dati prenume: ")
            inaltimeNoua=int(input("Dati inaltimea noua: "))
            self.__controller.modificareJucator(nume,prenume,inaltimeNoua)
            print("Inaltimea jucatorului a fost modificata cu succes!")
        except JucatorNotFound:
            print("Jucatorul nu a fost gasit")
        
    def __callTipareste(self):  
        
        rez=self.__controller.veziEchipa()

        if len(rez)<5:
            print("Nu se poate forma echipa")
        else:

            for jucator in rez:
                print(jucator.getNume()+" "+jucator.getPrenume()+" "+jucator.getPost()+" "+str(jucator.getInaltime()))

    def __callImporta(self):
        numefisier=input("Dati nume fisier: ")
        if numefisier!="alt.txt":
            print("Nume incorect!!")
        else:
            self.__controller.importa()
            print("Jucatorii au fost importati")
        
    def start(self):
        '''
        Punctul de intreare in aplicatie
        '''
        while True:
            print("1. Adauga jucator")
            print("2. Modificare inaltime")
            print("3. Tipareste echipa")
            print("4. Importa jucatori ")
            print("x. Iesire")
            cmd=input("Dati comanda: ")
            if cmd=='x':
                print("Multumim ca ati utilizat aplicatia!")
                break
            if cmd=='1':
                self.__callAdauga()
                continue
            if cmd=='2':
                self.__callModifica()
                continue
            if cmd=='3':
                self.__callTipareste()
                continue
            if cmd=='4':
                self.__callImporta()
                continue
            else:
                print("Comanda invalida!")
                continue
        