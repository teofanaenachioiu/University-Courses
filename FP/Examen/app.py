'''
Created on 22 ian. 2018

@author: USER
'''
from Ui.Console import Console
from Repository.JucatorRepository import JucatorRepository
from Service.JucatorController import JucatorController
from Ui.validare import Validare

repo=JucatorRepository("jucatori.txt","alt.txt")
controller=JucatorController(repo)
validare=Validare()

if __name__ == '__main__':
    Console=Console(controller,validare)
    Console.start()