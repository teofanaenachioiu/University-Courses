def citire(L):
    n=int(input("n="))
    L=[]
    for i in range(0,n):
        a=int(input("a="))
        L.append(a)
    return L
L=[]
L=citire(L)
print(L)