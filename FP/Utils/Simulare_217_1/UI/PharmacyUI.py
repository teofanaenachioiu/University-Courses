'''
Created on 11 dec. 2017

@author: USER
'''
class NrError(Exception):
    pass


class Validare():
    
    def valid(self,nr):
        if (nr<0):
            raise NrError()
class UI():
    def __init__(self,service):
        self.__service=service
        self.__validare=Validare()
    
    def __apelCautare(self):
        nume=input("    Dati nume medicament: ")
        medicines= self.__service.lookUp(nume)
        if len(medicines)==0:
            print("    Nu exista medicamente cu acest nume in fisier")
        else:
            for medicine in medicines:
                print(medicine.getName())
            
    def __apelReteta(self):
        try:
            nr=int(input("    Dati numarul de medicamente:"))
            self.__validare.valid(nr)
            #print("Alegeti din urmatoarele medicamente: ")
            #for item in self.__service.getAll():
                #print(item.getName())
            rezultat=[]
            index=0
            while index<nr:
                nume=input("    Dati nume medicament: ")
                medicines= self.__service.lookUp(nume)
                if medicines==[]:
                    print("Nu exista acest medicament!")
                else:
                    rezultat.extend(medicines)
                    index=index+1
            lista=self.__service.createRecipe(rezultat)
            print(lista.getTotalPrice())
        except ValueError:
            print("Dati un numar")
        except NrError():
            print("Numarul nu poate fi negativ")
        
    def consola(self):
        print("1. Cautati medicamente pe baza numelui.")
        print("2. Creare reteta pe baza unor medicamente citite.")
        print("3. Sortare dupa pret")
        print("x. Iesire din aplicatie")
        while True:
            cmd=input("Dati comanda: ")
            if cmd =='1':
                self.__apelCautare()
            if cmd=='2':
                self.__apelReteta()
            if cmd=='x':
                print("Va mai asteptam!")
                break
            if cmd=='3':
                lista=self.__service.sort()
                for item in lista:
                    print(item.getName()+ " " + str(item.getPrice()))
            if cmd not in ('1','2','3','x'):
                print("Comanda incorecta!")