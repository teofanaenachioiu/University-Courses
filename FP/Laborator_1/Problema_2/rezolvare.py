from math import sqrt
n=input("Dati o valare lui n: ")
n=int(n)
d=2
nr=0
if n==0 or n==1:
    print("Numarul nu este prim")
else:
    while d<=int(sqrt(n)+1):
        if n%d==0:
            nr=nr+1
        d=d+1
    if nr==0:
        print("Numar prim")
    else:
        print("Numarul nu e prim")
