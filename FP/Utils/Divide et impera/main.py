'''
Created on 8 ian. 2018

@author: USER
'''
#De cate ori apare un element in lista
def cate (l,elem):
    if l==0:
        return 0
    if len(l)==1:
        if l[0]==elem:
            return 1
        else:
            return 0
    else:
        n=len(l)//2
        return cate(l[:n],elem)+cate(l[n:],elem)

print(cate([0,1,2],5))

#Produsul elementelor de pe pozitii divizibile cu 3
def prod3(l):
    return prod3i(l,0,len(l)-1)

def prod3i(l,st,dr):
    if dr<st:
        return 1
    if dr-st==0:
        if st%3==0:
            return l[st]
        else:
            return 1
    m=(dr+st)//2
    return prod3i(l, st, m)*prod3i(l, m+1, dr)

print(prod3([1,2,3,4,5,6,7]))
