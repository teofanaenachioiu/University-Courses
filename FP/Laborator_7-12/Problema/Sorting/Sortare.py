'''
Created on 18 dec. 2017

@author: USER
'''
def swap(lista, poz1,poz2):
    """
    Functia interschimba elementele de pe pozitiile date din lista.
    :param lista: lista
    :param poz1: numar intreg
    :param poz2: numar intreg
    """
    tmp = lista[poz1]
    lista[poz1] = lista[poz2]
    lista[poz2] = tmp
    return lista

def bingoSort(alist,*,key=lambda x: x, reverse=False):
    """
    Implementarea algoritmului de sortare BingoSort
    :param alist: lista
    :param key: functie dupa care se face sortarea
    :param reverse: metoda dupa care se face storarea (cresc, descresc)
    :return alist: lista sortata
    """
    poz=len(alist)-1
    maxValue=alist[poz]
    #calculeaza valoarea maxima din lista
    for i in range(poz-1,-1,-1):
        if reverse==False:
            if key(alist[i])>key(maxValue):
                maxValue=alist[i]
        elif reverse ==True:
            if key(alist[i])<key(maxValue):
                maxValue=alist[i]
    while poz>0 and alist[poz]==maxValue: #maximul e pe pozitia sa finala
        poz=poz-1
    while poz>0:
        value=maxValue #retinem maximul anterior
        maxValue=alist[poz] #incepem cautarea urmatorului maxim de pe pozitia poz
        for i in range(poz-1,-1,-1):
            if key(alist[i])==key(value):
                swap(alist, i, poz)
                poz=poz-1
            else:
                if reverse==False:
                    if key(alist[i])>key(maxValue):
                        maxValue=alist[i] #retinem noul maxim
                else:
                    if reverse==True:
                        if key(alist[i])<key(maxValue):
                            maxValue=alist[i] #retinem noul maxim
        while poz>0 and key(alist[poz])==key(maxValue): #maximul e pe pozitia sa finala
            poz=poz-1
    return alist

def mergeSort(alist,*,key=lambda x:x, reverse=False):
    """
    Implementarea algoritmului de sortare mergeSort
    :param alist: lista
    :param key: functie dupa care se face sortarea
    :param reverse: metoda dupa care se face storarea (cresc, descresc)
    :return alist: lista sortata
    """
    if len(alist)>1:
        mid = len(alist)//2
        lefthalf = alist[:mid]
        righthalf = alist[mid:]

        mergeSort(lefthalf)
        mergeSort(righthalf)

        i=0
        j=0
        k=0
        while i < len(lefthalf) and j < len(righthalf):
            if reverse==False:
                if key(lefthalf[i]) < key(righthalf[j]):
                    alist[k]=lefthalf[i]
                    i=i+1
                else:
                    alist[k]=righthalf[j]
                    j=j+1
                k=k+1
            elif reverse==True:
                if key(lefthalf[i]) > key(righthalf[j]):
                    alist[k]=lefthalf[i]
                    i=i+1
                else:
                    alist[k]=righthalf[j]
                    j=j+1
                k=k+1
        while i < len(lefthalf):
            alist[k]=lefthalf[i]
            i=i+1
            k=k+1

        while j < len(righthalf):
            alist[k]=righthalf[j]
            j=j+1
            k=k+1
    return alist       


