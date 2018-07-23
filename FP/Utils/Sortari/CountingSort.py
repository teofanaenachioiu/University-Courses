'''
Created on 10 dec. 2017

@author: USER
'''
def countingSort(l):
    count =[0] * len(l)
    
    for i in range(len(l)-1):
        for j in range(i+1,len(l)):
            if l[j]>l[i]:
                count[j]+=1
            else:
                count[i]+=1
          
    rez=[0] * len(l)  
            
    for i in range(len(l)):
        poz=count[i]
        rez[poz]=l[i]
        
    return rez

print(countingSort([7,5,9,1,2]))