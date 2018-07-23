'''
Created on 16 dec. 2017

@author: USER
'''
def swap(lista, poz1,poz2):
    tmp = lista[poz1]
    lista[poz1] = lista[poz2]
    lista[poz2] = tmp
    

def selectionSort(alist):
    for fillslot in range(len(alist)-1,0,-1):
        positionOfMax=0
        for location in range(1,fillslot+1):
            if alist[location]>alist[positionOfMax]:
                positionOfMax = location
        swap(alist,fillslot,positionOfMax)
    return alist

def directSelectionSortMin(l):
    """
    sort the element of the list
    l - list of element
    return the ordered list (l[0]<l[1]<...)
    """
    for i in range(0,len(l)-1):
    #select the smallest element
        for j in range(i+1,len(l)):
            if l[j]<l[i]:
                swap(l,i,j)
    return l

def directSelectionSortMax(l):
    """
    sort the element of the list
    l - list of element
    return the ordered list (l[0]<l[1]<...)
    """
    for i in range(len(l)-1,0,-1):
    #select the largest element
        for j in range(i-1,-1,-1):
            if l[j]>l[i]:
                swap(l,i,j)
    return l

print(directSelectionSortMin([54,26,93,17,77,31,44,55,20]))
print(directSelectionSortMax([54,26,93,17,77,31,44,55,20]))