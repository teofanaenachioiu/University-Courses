def StartUI():
    '''
    Interfata grafica pentru aplicatie
    Conditii pentru elementele listei: numere intregi
    :return:
    '''
    while True:
        print("(1) Citirea unei liste de numere intregi")
        print("(2) Gasirea secventelor de lungime maxima care respecta proprietatea 15")
        print("(3) Gasirea secventelor de lungime maxima care respecta proprietatea 16")
        print("(4) Gasirea secventelor de lungime maxima care respecta proprietatea 8")
        print("(5) Iesire din aplicatie")
        comanda = input("Dati comanda: ")
        if comanda == "1":
            print("Introduceti elementele: ")
            try:
                lista = [int(x) for x in input().split()]
                '''Citeste elementele listei'''
            except ValueError:
                print("Valorile trebuie sa fie intregi.")
        else:
            if comanda == "2":
                print("Rezultat:")
                try:
                    subsecventaMax(prop_15,lista)
                    '''Determina secventa de lungime maxima cu proprietatea 15'''
                except NameError:
                    print("Cititi lista mai intai.")
            else:
                if comanda == "3":
                    print("Rezultat:")
                    try:
                        subsecventaMax(prop_16,lista)
                        '''Determina secventa de lungime maxima cu proprietatea 16'''
                    except NameError:
                        print("Cititi lista mai intai.")
                else:
                    if comanda == "5":
                        print("Iti multumim ca ai utilizat aplicatia!")
                        break
                        '''Iesirea din aplicatie'''
                    else:
                        if comanda == "4":
                            print("Rezultat: ")
                            try:
                                subsecventaMax(prop_8, lista)
                            except NameError:
                                print("Cititi lista mai intai")
                        else:
                            print("Comanda incorecta!")
        print()

def subsecventaMax(prop,lista):
    '''
    Determina secventa de lungime maxima cu proprietatea prop data
    :param prop: prop_15 sau prop_16
    :param lista: elementele listei sunt numere intregi
    :return: mesaj: secventa de lungime maxima sau "Nu exista secventa"
    '''
    sMax=[]
    sCurenta=[]
    nrMax=0
    nrCurent=0
    for i in range (len(lista)):
        for j in range(i,len(lista)):
            '''Se considera fiecare secventa posibila si se verifica daca indeplineste proprietatea "prop"'''
            sCurenta=lista[i:(j+1)]
            nrCurent=len(sCurenta)
            if prop(sCurenta)==True and nrCurent>nrMax:
                '''Se reactualizeaza secventa maxima in cazul in care secventa curenta este mai lunga decat cea maxima'''
                sMax=sCurenta
                nrMax=nrCurent
    if nrMax>1:
        print(sMax)
    else:
        print("Nu exista secventa")

def prop_15(L):
    '''
    Precizeaza daca lista este o secventa de tip munte
    :param L: o lista
    :return: True sau False
    '''
    ok=1
    cresc=1
    for k in range(0,len(L)-1):
        if L[k]>L[k+1]:
            if k==0:
                ok=0
                break
            cresc=0
        if L[k]<L[k+1] and cresc==0:
            ok=0
            break
    if ok==1 and cresc==0:
        return True
    else:
        return False

def prop_15Test():
    '''
    Se verifica daca proprietatea 15 returneaza rezultatul corect in functie de lista citita
    :return: True sau False
    '''
    assert prop_15([72,2,52])==False
    assert prop_15([2,5,3])==True
    assert prop_15([2,3,4,1])==True

def lista_cifre(x):
    '''
    Determina cifrele din care este format un numar
    :param x: numar intreg
    :return: o lista
    '''
    cifre = [0,0,0,0,0,0,0,0,0,0]
    if x<0:
        abs=x*(-1)
    else:
        abs=x
    while abs > 0:
        if cifre[int(abs % 10)] == 0:
            cifre[int(abs % 10)] = 1
        abs = abs // 10
    return cifre

def prop_16(L):
    '''
    Precizeaza daca lista contine doar elemente formate din aceleasi cifre
    :param L: o lista
    :return: True sau False
    '''
    ok=1
    for k in range(0,len(L)-1):
        if lista_cifre(L[k]) != lista_cifre(L[k+1]):
            '''Verifica daca listele sunt distincte. In caz afirmativ secventa nu indeplineste conditia 16'''
            ok=0
            break
    if ok==1:
        return True
    else:
        return False

def prop_16Test():
    '''
    Se verifica daca proprietatea 15 returneaza rezultatul corect in functie de lista citita
    :return: True sau False
    '''
    assert prop_16([3313, 113,331,11333])==True
    assert prop_16([3313, 113,331,11332])==False

def prop_8(L):
    '''
    Precizeaza daca lista contine toate elementele in intervalul [0,10]
    :param L: o lista
    :return: True sau False
    '''
    ok=1
    for k in range(0,len(L)):
        if L[k]>10 or L[k]<0:
            ok=0
            break
    if ok==1:
        return True
    else:
        return False

if __name__ == '__main__':

    a = []
    lista = []
    StartUI()