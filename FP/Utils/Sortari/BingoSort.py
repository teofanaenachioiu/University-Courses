'''
Created on 16 dec. 2017

@author: USER
'''
def swap(lista, poz1,poz2):
    tmp = lista[poz1]
    lista[poz1] = lista[poz2]
    lista[poz2] = tmp
    return lista

def bingoSort(alist):
    poz=len(alist)-1
    maxValue=alist[poz]
    #calculeaza valoarea maxima din lista
    for i in range(poz-1,-1,-1):
        if alist[i]>maxValue:
            maxValue=alist[i]
    while poz>0 and alist[poz]==maxValue: #maximul e pe pozitia sa finala
        poz=poz-1
    while poz>0:
        value=maxValue #retinem maximul anterior
        maxValue=alist[poz] #incepem cautarea urmatorului maxim de pe pozitia poz
        for i in range(poz-1,-1,-1):
            if alist[i]==value:
                swap(alist, i, poz)
                poz=poz-1
            elif alist[i]>maxValue:
                maxValue=alist[i] #retinem noul maxim
        while poz>0 and alist[poz]==maxValue: #maximul e pe pozitia sa finala
            poz=poz-1
    return alist

print(bingoSort([27,19,25,1,19,5,6,8,99,18,84,65,15,158,6,9,45,68]))