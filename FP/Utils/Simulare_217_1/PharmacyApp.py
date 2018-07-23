'''
Created on 11 dec. 2017

@author: USER
'''
from UI.PharmacyUI import UI
from UI.PharmacyServices import PharmacyServices
from Repository.MedicineRepository import MedicineRepository
repo=MedicineRepository("medicines.txt")
service=PharmacyServices(repo)
if __name__ == '__main__':
    Start=UI(service)
    Start.consola()