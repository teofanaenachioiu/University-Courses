'''
Created on 12 dec. 2017

@author: USER
'''
from Repository.RepositoryConcerte import ConcerteRepository
from Service.ConcertService import ServiceConcerte
from UI.ConcerteUI import Consola 
repo=ConcerteRepository("concerte.txt")
service=ServiceConcerte(repo)

if __name__ == '__main__':
    Consola=Consola(service)
    Consola.start()