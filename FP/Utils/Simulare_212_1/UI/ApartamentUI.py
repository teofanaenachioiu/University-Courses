'''
Created on 12 dec. 2017

@author: USER
'''
from Repository.ApartamentRepository import InputError
class ApartamentUI():
    def __init__(self,service):
        self.__service=service
    
    def __validareDate(self,nrApartament=1,nrMetriiApa=1,nrMetriiGaz=1,intretinere=1):
        if nrApartament<=0:
            raise ValueError()
        if nrMetriiApa<0:
            raise ValueError()  
        if nrMetriiGaz<0:
            raise ValueError()  
        if intretinere<0:
            raise ValueError() 
            
    def __apelSortare(self):
        """
        Sortarea apartamentelor dupa numele de familie
        """
        apartamente=self.__service.sortByFamiliyName()
        for apartament in apartamente:
            print(str(apartament.getNrApartament())+" "+apartament.getNumeFamilie()+" "+str(apartament.getNrMembri()))
        
    def __apelPlata(self):
        """
        Viziualizare plata per apartament
        """
        try:
            nrApartament=int(input("Dati numarul apartamentului: "))
            self.__validareDate(nrApartament=nrApartament)
            self.__service.plata(nrApartament,[0,0,0]) 
            nrMetriiApa=int(input("Dati numarul de metri cubi de apa consumata: "))
            self.__validareDate(nrMetriiApa=nrMetriiApa)
            nrMetriiGaz=int(input("Dati numarul de metri cubi de gaz consumat: "))
            self.__validareDate(nrMetriiGaz=nrMetriiGaz)
            intretinere=int(input("Dati pretul intretinerii: "))
            self.__validareDate(intretinere=intretinere)
            plata=self.__service.plata(nrApartament,[nrMetriiApa,nrMetriiGaz,intretinere]) 
            print(str(plata.getApatmanet().getNrApartament())+" "+plata.getApatmanet().getNumeFamilie()+" "+str(plata.getApatmanet().getNrMembri())+" - "+str(plata.getSumaTotala()))   
          
        except InputError:
            print("Nu sa gasit apartamentul.") 
        except ValueError:
            print("Dati un numar natural!!")
        
    def Consola(self):
        print("1. Sortare apartamente dupa numele de familie")
        print("2. Afisare suma totala de plata")
        print("x. Iesire")
        while True:
            cmd=input("Dati comanda: ")
            if cmd=='1':
                self.__apelSortare()
            if cmd=='2':
                self.__apelPlata()
            if cmd=='x':
                print("Pe curand!")
                break
            if cmd not in ['1','2','x']:
                print("Comanda incorecta!!")