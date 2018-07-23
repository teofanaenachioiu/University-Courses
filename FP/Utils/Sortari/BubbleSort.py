'''
Created on 10 dec. 2017

@author: USER
'''
def swap(lista, poz1,poz2):
    tmp = lista[poz1]
    lista[poz1] = lista[poz2]
    lista[poz2] = tmp

            
def bubbleSort(l): 
    sortedd=False
    while not sortedd:        
        sortedd = True #presupunem ca lista e sortata
        for i in range(1,len(l)):
            if l[i-1]>l[i]:                
                swap(l, i, i-1)                
                sortedd = False #lista inca nu e sortata
    return l

print(bubbleSort([7,5,9,1,2]))