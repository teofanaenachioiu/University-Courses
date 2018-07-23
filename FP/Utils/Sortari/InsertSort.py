'''
Created on 10 dec. 2017

@author: USER
'''
def insertSort(l):
    for i in range(1,len(l)):
        ind = i-1        
        a = l[i]
        while ind>=0 and l[ind]>a:            
            l[ind+1] = l[ind]            
            ind = ind-1        
        l[ind+1] = a
    return l

print(insertSort([7,5,9,1,2]))