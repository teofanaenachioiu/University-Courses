'''
Created on 10 dec. 2017

@author: USER
'''
def partition(l,left,right):
    pivot = l[left]    
    i = left    
    j = right
    while i!=j:
        while l[j]>=pivot and i<j:            
            j = j-1        
        l[i] = l[j]
        while l[i]<=pivot and i<j:            
            i = i+1        
        l[j] = l[i]    
    l[i] = pivot
    return i

def quickSort(l,left,right):
    pos = partition(l, left, right)
    
    if left<pos-1:           
        quickSort(l, left, pos-1)
        
    if pos+1<right:              
        quickSort(l, pos+1, right)
    return l
       
print(quickSort([7,5,9,1,2,9,8,11,1,9,10],0,10))