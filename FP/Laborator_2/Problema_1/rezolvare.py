#Verificare numar prim
def prim(nr):
    if nr==1 or nr==0:
        return False
    d=2
    while d<=nr/2:
        if nr%d==0:
            return False
        else:
            d=d+1
    return True

#Cautarea primului numar prim mai mare decat nr
def cauta_next_prim(nr):
    num=nr+1
    while not prim(num):
        num+=1
    return num

#Functia UI
def StartUI():
    while True:
        print("(1) Rezolvare cerinta")
        print("(0) Iesire")
        comanda=input("Dati comanda: ")
        if comanda=="0":
            print("Multumim ca ai uitilizat aplicatia!")
            break
        if comanda=="1":
            nr=int(input("Introduceti numarul: "))
            print("Numarul cautat: ", cauta_next_prim(nr))
        else:
            print("Comanda incorecta!")

StartUI()