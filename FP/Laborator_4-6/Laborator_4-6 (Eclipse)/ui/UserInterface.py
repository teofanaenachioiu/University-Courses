from domain.Validation import *

def initMeniu():
    '''
    Functia creaza un dictionar corespunzator meniului principal.
    Fiecare pereche are ca si cheie numarul corespunzator optiunii din meniu, iar ca valoare, numele functiei de apelat.
    :return dictionar
    '''
    meniu={}
    meniu['1']=initSubmeniuAdauga
    meniu['2']=initSubmeniuSterge
    meniu['3']=initSubmeniuCautare
    meniu['4']=initSubmeniuRapoarte
    meniu['5']=initSubmeniuFiltrare
    return meniu


def printMeniu():
    '''
    Functia tipareste meniul principal.
    '''
    print("(1) Adaugare")
    print("(2) Stergere")
    print("(3) Cautare")
    print("(4) Rapoarte")
    print("(5) Filtrare")
    print("Implicit: ")
    print("(u) Undo")
    print("(x) Iesire")


def initSubmeniu():
    '''
    Functia creaza un dictionar corespunzator submeniurilor.
    Fiecare pereche are ca si cheie numarul corespunzator optiunii din meniu, iar ca valoare, numele functiei de printare de apelat.
    :return dictionarul psubmeniu
    '''
    psubmeniu={}
    psubmeniu['1']=printSubmeniuAdauga
    psubmeniu['2']=printSubmeniuSterge
    psubmeniu['3']=printSubmeniuCautare
    psubmeniu['4']=printSubmeniuRapoarte
    psubmeniu['5']=printSubmeniuFlitrare
    return psubmeniu


def initSubmeniuAdauga():
    '''
    Functia creaza un dictionar corespunzator submeniului optiunii Adauga.
    Fiecare pereche are ca si cheie litera corespunzatoare optiunii din submeniu, iar ca valoare, numele subfunctiei de apelat.
    :return dictionarul submeniu.
    '''
    submeniu={}
    submeniu['a']=apel_cheltuialaNoua
    submeniu['b']=apel_actualizareCheltuiala
    return submeniu


def initSubmeniuSterge():
    '''
    Functia creaza un dictionar corespunzator submeniului optiunii Sterge.
    Fiecare pereche are ca si cheie litera corespunzatoare optiunii din submeniu, iar ca valoare, numele subfunctiei de apelat.
    :return dictionarul submeniu.
    '''
    submeniu = {}
    submeniu['a'] = apel_stergeZi
    submeniu['b'] = apel_stergeInterval
    submeniu['c'] = apel_stergeCheltuieli
    return submeniu


def initSubmeniuCautare():
    '''
    Functia creaza un dictionar corespunzator submeniului optiunii Cautare.
    Fiecare pereche are ca si cheie litera corespunzatoare optiunii din submeniu, iar ca valoare, numele subfunctiei de apelat.
    :return dictionarul submeniu.
    '''
    submeniu = {}
    submeniu['a'] =apel_tiparireSuma
    submeniu['b'] =apel_tiparireZiSuma
    submeniu['c'] =apel_tiparireTip
    return submeniu


def initSubmeniuRapoarte():
    '''
    Functia creaza un dictionar corespunzator submeniului optiunii Rapoarte.
    Fiecare pereche are ca si cheie litera corespunzatoare optiunii din submeniu, iar ca valoare, numele subfunctiei de apelat.
    :return dictionarul submeniu.
    '''
    submeniu = {}
    submeniu['a'] =apel_tiparireSumaTip
    submeniu['b'] =apel_cheltuialaMaxima
    submeniu['c'] =apel_tiparireSumaExacta
    submeniu['d'] =apel_sortareTip
    return submeniu


def initSubmeniuFiltrare():
    '''
    Functia creaza un dictionar corespunzator submeniului optiunii Filtrare.
    Fiecare pereche are ca si cheie litera corespunzatoare optiunii din submeniu, iar ca valoare, numele subfunctiei de apelat.
    :return dictionarul submeniu.
    '''
    submeniu = {}
    submeniu['a'] =apel_eliminaTip
    submeniu['b'] =apel_eliminaSuma
    return submeniu


def printSubmeniuAdauga():
    '''
    Functia tipareste submeniul Adauga.
    '''
    print("     (a) Adauga o noua cheltuiala")
    print("     (b) Actualizeaza o cheltuiala existenta")


def printSubmeniuSterge():
    '''
    Functia tipareste submeniul Sterge.
    '''
    print("     (a) Sterge toate cheltuielile pentru o zi data")
    print("     (b) Sterge cheltuielile pe un anumit interval de timp")
    print("     (c) Sterge toate cheltuielile de un anumit tip")


def printSubmeniuCautare():
    '''
    Functia tipareste submeniul Cautare.
    '''
    print("     (a) Tipareste toate cheltuielile mai mari decat o suma data")
    print("     (b) Tipareste toate cheltuielile efectuate inainte de o zi dată si mai mici decat o suma data")
    print("     (c) Tipareste toate cheltuielile de un anumit tip")


def printSubmeniuRapoarte():
    '''
    Functia tipareste submeniul Rapoarte.
    '''
    print("     (a) Tipareste suma totala pentru un anumit tip de cheltuiala")
    print("     (b) Gaseste ziua in care suma cheltuita este maxima")
    print("     (c) Tipareste toate cheltuielile ce au o anumita suma")
    print("     (d) Tipareste cheltuielile sortate dupa tip")


def printSubmeniuFlitrare():
    '''
    Functia tipareste submeniul Filtrare.
    '''
    print("     (a) Elimina toate cheltuielile de un anumit tip")
    print("     (b) Elimina toate cheltuielile mai mici decat o suma data")
    

def printMesaje(date=0,actualizare=0,faraCheltuieli=0, nou=0,update=0,inceput=0,sfarsit=0,suma=0,ziua=0):
    '''
    Functia printeaza mesaje corespunzatoare valorilor indicilor
    :param date: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param actualizare: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param fara cheltuieli: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param nou: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param update: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param inceput: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param sfarsit: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param suma: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    :param ziua: 0=nu se afiseaza mesajul, 1=se afiseaza mesajul
    '''
    if date==1:
        print("         Date incorecte!")
    if actualizare==1:
        print("         Nu s-a gasit cheltuiala de actualizat!")
    if faraCheltuieli==1:
        print("         Nu s-au gasit cheltuieli!")
    if nou==1:
        print("        Introduceti noua cheltuiala:")
    if update==1:
        print("        Introduceti cheltuiala de actualizat:")
    if inceput==1:
        print("        Inceputul intervalului")
    if sfarsit==1:
        print("        Sfarsitul intervalului")
    if suma==1:
        return 'Suma: '
    if ziua==1:
        return 'Ziua: '

def apel_cheltuialaNoua(cheltuieli,undo):
    '''
    Se executa comanda: Adauga cheltuiala noua.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    printMesaje(nou=1)
    while True:
        zi = citireZi()
        suma = citireSuma()
        tip = citireTip()
        if validareDate(zi,suma,tip,True)==True:
            cheltuialaNouaCitita=creareCheltuiala(zi,suma,tip)
            cheltuialaNoua(cheltuieli, cheltuialaNouaCitita,undo)
            break
        else:
            printMesaje(date=1)


def apel_actualizareCheltuiala(cheltuieli,undo):
    '''
    Se executa comanda: Actualizare cheltuiala.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    printMesaje(update=1)
    while True:
        zi = citireZi()
        suma = citireSuma()
        tip = citireTip()
        if validareDate(zi,suma,tip,True)==True:
            cheltuialaDeActualizat = creareCheltuiala(zi,suma,tip)
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_actualizareCheltuiala(cheltuieli, cheltuialaDeActualizat, False) == True:
        printMesaje(nou=1)
        while True:
            zi = citireZi()
            suma = citireSuma()
            tip = citireTip()
            if validareDate(zi,suma,tip,True)==True:
                cheltuialaNouaCitita=creareCheltuiala(zi,suma,tip)
                break
            else:
                printMesaje(date=1)
                continue
        actualizareCheltuiala(cheltuieli, cheltuialaDeActualizat, cheltuialaNouaCitita,undo)
    else:
        printMesaje(actualizare=1)


def apel_stergeZi(cheltuieli,undo):
    '''
    Se executa comanda: Sterge cheltuiala dintr-o anumita zi.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        zi = citireZi()
        if validareDate(ziValid=zi)==True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_stergeZi(cheltuieli,zi,False)==True:
        stergeZi(cheltuieli,zi,undo)
    else:
        printMesaje(faraCheltuieli=1)


def apel_stergeInterval(cheltuieli,undo):
    '''
    Se executa comanda: Sterge cheltuielile dintr-un anumit interval de timp.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        printMesaje(inceput=1)
        ziInceput = citireZi()
        printMesaje(sfarsit=1)
        ziSfarsit = citireZi()
        if ziInceput<=ziSfarsit and validareDate(ziValid=ziInceput)==True and validareDate(ziValid=ziSfarsit)==True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_stergeInterval(cheltuieli,ziInceput,ziSfarsit,False)==True:
        stergeInterval(cheltuieli,ziInceput,ziSfarsit,undo)
    else:
        printMesaje(faraCheltuieli=1)


def apel_stergeCheltuieli(cheltuieli,undo):
    '''
    Se executa comanda: Sterge cheltuieli de un anumit tip.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        tipSters=citireTip()
        if validareDate(tipValid=tipSters)==True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_stergeCheltuieli(cheltuieli,tipSters,False)==True:
        stergeCheltuieli(cheltuieli,tipSters,undo)
    else:
        printMesaje(faraCheltuieli=1)


def apel_tiparireSuma(cheltuieli,undo):
    '''
    Se executa comanda: Tiparire cheltuieli care au suma mai mica decat cea data.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        sumaCitita=citireSuma()
        if validareDate(sumaValid=sumaCitita)==True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_tiparireSuma(cheltuieli,sumaCitita,False) == True:
        afisareCheltuieli(tiparireSuma(cheltuieli,sumaCitita,undo))
    else:
        printMesaje(faraCheltuieli=1)


def apel_tiparireZiSuma(cheltuieli,undo):
    '''
    Se executa comanda: Tipareste toate cheltuielile ce sunt efectuate inainte de o zi data si mai mici decat o suma.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        ziCitita=citireZi()
        sumaCitita=citireSuma()
        if validareDate(ziValid=ziCitita,sumaValid=sumaCitita)==True:
            break
        else:
            printMesaje(date=1)
        continue
    if verificare_tiparireZiSuma(cheltuieli,ziCitita,sumaCitita,False)==True:
        afisareCheltuieli(tiparireZiSuma(cheltuieli, ziCitita,sumaCitita,undo))
    else:
        printMesaje(faraCheltuieli=1)


def apel_tiparireTip(cheltuieli,undo):
    '''
    Se executa comanda: Tipareste cheltuielile de un anumit tip.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        tipCitit=citireTip()
        if validareDate(tipValid=tipCitit)==True:
            break
        else:
            printMesaje(date=1)
        continue
    if verificare_tiparireTip(cheltuieli,tipCitit,False)==True:
        afisareCheltuieli(tiparireTip(cheltuieli, tipCitit,undo))
    else:
        printMesaje(faraCheltuieli=1)


def apel_tiparireSumaTip(cheltuieli,undo):
    '''
    Se executa comanda: tiparire suma unui anumit tip de cheltuiala
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        tipCitit=citireTip()
        if validareDate(tipValid=tipCitit)==True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_tiparireSumaTip(cheltuieli,tipCitit,False)==True:
        print(printMesaje(suma=1), tiparireSumaTip(cheltuieli,tipCitit,undo))
    else:
        printMesaje(faraCheltuieli=1)


def apel_cheltuialaMaxima(cheltuieli,undo):
    '''
    Se executa comanda: cauta cheltuiala maxima
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    if verificare_listaNevida(cheltuieli,True)==True:
        print(printMesaje(ziua=1),cheltuialaMaxima(cheltuieli,0,undo))
    else:
        printMesaje(faraCheltuieli=1)


def apel_tiparireSumaExacta(cheltuieli,undo):
    '''
    Se executa comanda: tiparire cheltuieli ce au o suma fixata
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        sumaCitita = citireSuma()
        if validareDate(sumaValid=sumaCitita) == True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_tiparireSumaExacta(cheltuieli, sumaCitita,False) == True:
        afisareCheltuieli(tiparireSumaExacta(cheltuieli, sumaCitita,undo))
    else:
        printMesaje(faraCheltuieli=1)


def apel_sortareTip(cheltuieli,undo):
    '''
    Se executa comanda: sortare dupa tip
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    if verificare_listaNevida(cheltuieli, True) == True:
        afisareCheltuieli(sortareTip(cheltuieli,undo))
    else:
        printMesaje(faraCheltuieli=1)


def apel_eliminaTip(cheltuieli,undo):
    '''
    Se executa comanda: Elimina cheltuielile diferite de suma data.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        tipCitit =citireTip()
        if validareDate(tipValid=tipCitit) == True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_eliminaTip(cheltuieli,tipCitit,False)==True:
        eliminaTip(cheltuieli,tipCitit,undo)
    else:
        printMesaje(faraCheltuieli=1)


def apel_eliminaSuma(cheltuieli,undo):
    '''
    Se executa comanda: Elimina cheltuielile diferite de suma data.
    Daca datele introduse sunt corecte se apeleaza functia, in caz contrar se printeaza un mesaj.
    '''
    while True:
        sumaCitita = citireSuma()
        if validareDate(sumaValid=sumaCitita) == True:
            break
        else:
            printMesaje(date=1)
            continue
    if verificare_eliminaSuma(cheltuieli,sumaCitita,False)==True:
        eliminaSuma(cheltuieli,sumaCitita,undo)
    else:
        printMesaje(faraCheltuieli=1)
        
    
def citireZi():
    '''
    Functia citeste o zi din luna.
    :return zi (numar intreg)
    '''
    zi = int(input("        - Dati ziua: "))
    return zi


def citireSuma():
    '''
    Functia citeste o suma de bani
    :return suma (numar intreg)
    '''
    suma = float(input("        - Dati suma: "))
    return suma


def citireTip():
    '''
    Functia citeste un tip de cheltuiala
    :return tip (string)
    '''
    tip = input("        - Dati tipul (mancare, intretinere, imbracaminte, telefon, altele): ")
    return tip


def creareCheltuiala(zi,suma,tip):
    '''
    Functia creaza un dictionar corespunzator unei cheltuieli.
    Fiecare pereche are ca si cheie 'ziua','suma',respectiv 'tipul', iar ca valoare, ziua (numar natural >0 si <32), suma(numar real pozitiv), respectiv tipul (sir de caractere ce respecta sabolanele date).
    Functia returneaza un dictionar.
    '''
    
    cheltuiala={}
    cheltuiala["ziua"]=zi
    cheltuiala["suma"]=suma
    cheltuiala["tipul"]=tip
    return cheltuiala


def afisareCheltuieli(cheltuieli):
    '''
    Functia afiseaza lista de cheltuieli sau un mesaj, in cazul in care lista este vida.
    Parametrul 'cheltuieli' este o lista ce contine mai multe dictionare.
    Functia nu returneaza niciun obiect.
    '''
    if cheltuieli==[] or getTip(cheltuieli, 0)=='fara':
        print("Nu mai exista cheltuieli!")
    else:
        print("Lista de cheltuieli: ")
        for i in range(0,len(cheltuieli)):
            print(" * Cheltuiala: ",getZiua(cheltuieli,i)," ", getSuma(cheltuieli, i)," ", getTip(cheltuieli, i))


def StartUI():
    '''
    Functia creaza o interfata grafica pentru aplicatie.
    '''
    cheltuieli=initCheltuiala()
    undo=initUndo()
    meniu=initMeniu()
    while True:
        printMeniu()
        comanda1=input("Operatia dorita: ")
        if comanda1 == 'x':
            print("Multumim ca ati utilizat aplicatia!")
            break
        if comanda1=='u':
            cheltuieli=apel_undo(cheltuieli,undo) 
            afisareCheltuieli(cheltuieli) 
            continue
        while True:
            try:
                printare=initSubmeniu() #dictionar cu functiile de printare a submeniurilor
                printSubmeniu=printare[comanda1] #ia o anumita functie de printare
                printSubmeniu() #printeaza submeniul
                submeniuAles=meniu[comanda1] #initializeaza submeniul ales
                submeniu=submeniuAles() #ia un anumit submeniu cu functii 
                comanda2=input("     Operatia dorita din submeniu:") 
                if comanda2=='x':
                    break  
                if comanda2=='u':
                    cheltuieli=apel_undo(cheltuieli,undo) 
                    afisareCheltuieli(cheltuieli)
                    continue
                try:
                    apel=submeniu[comanda2] #ia o anumita comanda din submeniu
                    apel(cheltuieli,undo)
                    cheltuieli=copy.deepcopy(undo[-1]) 
                    afisareCheltuieli(cheltuieli)   
                except KeyError:
                    print("     Comanda gresita!")
                    continue                      
            except KeyError:
                print("Comanda gresita!")
                break
          
          
def StartUI2():
    
    '''
    Functia creaza o alta interfata grafica pentru aplicatie.
    '''
    
    cheltuieli=initCheltuiala()
    meniu=initMeniu()
    undo=initUndo()
    while True:
        print(": Adauga 1")
        print(": Sterge 2")
        print(": Cauta 3 ")
        print(": Rapoarte 4")
        print(": Filtreaza 5")
        print(": Undo u")
        print(": Exit e")
        comanda1=input("Comanda 1: ")
        if comanda1 == 'x':
            print("Bye!")
            break
        if comanda1=='u':
            cheltuieli=apel_undo(cheltuieli,undo) 
            afisareCheltuieli(cheltuieli) #vreau sa-mi afiseze noua lista de cheltuieli
            continue
        while True:
            try:
                if comanda1=='1':
                    print("      a: Cheltuiala noua")
                    print("      b: Actualizare cheltuiala")
                    print("      x: Exit")
                    print("      u: Undo")
                else:
                    if comanda1=='2':
                        print("     a: Sterge cheltuieli in functie de zi")
                        print("     b: Sterge cheltuieli in functie de interval de timp")
                        print("     c: Sterge in functie de tip")
                        print("     u: Undo")
                    else:
                        if comanda1=='3':
                            print("     a: Printeaza cheltuieli >suma")
                            print("     b: Printeaza cheltuieli <zi si <suma")
                            print("     c: Printeaza cheltuieli =tip")
                            print("     x: Exit")
                            print("     u: Undo")
                        elif comanda1=='4':
                            print("     a: Printeaza suma unui tip de cheltuiala")
                            print("     b: Gaseste suma maxima cheltuita")
                            print("     c: Printeaza cheltuielile =suma")
                            print("     d: Sortare dupa tip")
                            print("     x: Exit")
                            print("     u: Undo")
                        else:
                            if comanda1=='5':
                                print("     a: Izoleaza cheltuielile =tip")
                                print("     b: Izoleaza cheltuielile <suma")
                                print("     x: Exit")
                                print("     u: Undo")
                submeniuAles=meniu[comanda1] #initializeaza submeniul ales
                submeniu=submeniuAles()
                try:
                    comanda2=input("     Comanda 2:") 
                    if comanda2=='x':
                        break  
                    if comanda2=='u':
                        cheltuieli=apel_undo(cheltuieli,undo) 
                        afisareCheltuieli(cheltuieli) 
                        continue
                    else:
                        apel=submeniu[comanda2]
                        apel(cheltuieli,undo)
                        cheltuieli=copy.deepcopy(undo[-1]) 
                        
                        afisareCheltuieli(cheltuieli)    
                        
                except KeyError:
                    print("     Comanda incorecta!")
                    continue
            except KeyError:
                print("Comanda incorecta!")
                break