'''
Created on 12 dec. 2017

@author: USER
'''
from Repository.ApartamentRepository import ApartamentRepository
from Service.ApartamentService import ApartamentService
from UI.ApartamentUI import ApartamentUI

repo=ApartamentRepository("apartamente.txt")
service=ApartamentService(repo)

if __name__ == '__main__':
    Start=ApartamentUI(service)
    Start.Consola()