n=input("Dati o valare lui n: ")
n=int(n)
s=0
if n>0:
    for i in range (0,n):
        x=int(input("Numar",i,": "))
        s=s+x
    print("Suma numerelor este: ",s)
else:
    print('0')
