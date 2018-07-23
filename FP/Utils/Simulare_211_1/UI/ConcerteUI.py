'''
Created on 12 dec. 2017

@author: USER
'''
class Consola():
    def __init__(self,service):
        """
        Initializeaza un obicet de tip Consola
        """
        self.__service=service
        
    def __callShowLocatii(self):
        locatie=input("Dati locatia: ")
        concerte=self.__service.ShowConcerte(locatie)
        if len(concerte)==0:
            print("    Nu exista concerte la aceasta locatie")
        else:
            for concert in concerte:
                print(str(concert.getId())+" "+concert.getLocatie()+" "+str(concert.getPret())+" "+str(concert.getNrPers()))
                
    def __callReport(self):
        concerte=self.__service.ReportConcerte()           
        for concert in concerte:
            print(str(concert.getId())+" "+concert.getLocatie()+" "+str(concert.getPretTotal()))        
                
    def start(self):
        print("1. Vizualizati concertele dintr-o anumita locatie")
        print("2. Raport")
        print("x. Iesire")
        while True:
            cmd=input("Dati comanda: ")
            if cmd=='1':
                self.__callShowLocatii()
            if cmd=='2':
                self.__callReport()
            if cmd=='x':
                break
            if cmd not in ['1','2','x']:
                print("Comanda incorecta")
                continue
            
        