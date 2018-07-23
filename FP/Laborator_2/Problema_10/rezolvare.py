#Interfata Utilizator
def StartUI():

    while True:
        print("(1) Rezolvare cerinta")
        print("(0) Iesire")
        comanda=input("Dati comanda: ")
        if comanda=="0":
            print("Iti multumim ca ai utilizat aplicatia!")
            break
        if comanda=="1":
            eroare=False
            try:
               n=int(input("n="))
            except ValueError:
                print("Numarul nu e natural.")
                eroare=True
            if eroare==False:
                if n<0: #Validarea lui n
                    print("Numarul nu este natural")
                elif n==0: #Cazul n=0
                    m=0
                    print("m=",m)
                else: #Cazul n=numar natural nenul
                    L=creareL(n)
                    m=construireM(L)
                    print("m=",m)
        else:
            print("Comanda incorecta!")
        print("")

#Crearea listei de frecventa
def creareL(n):

    L=[0,0,0,0,0,0,0,0,0,0]
    while n>0:
        L[n%10]=L[n%10]+1
        n=n//10
    return L

#Construirea numarului m
def construireM(L):

    if L[0]==0:
        m=0
    else:
        ok=0
        i=1
        while ok==0 and i<10:
            if L[i]!=0:
                m=i
                L[i]=L[i]-1
                ok=1
            else: i=i+1
    for i in range(10):
        while L[i]>0:
            m=m*10+i
            L[i]=L[i]-1
    return m

StartUI()