'''
Created on 13 feb. 2018

@author: USER
'''
from validare import validare,validareCifra,NumarIncorectError,CifraError,ScadereError,validareScadere,\
    BazaError, validareBaza
from domain import Numar, Rezultat
from operatii import conversie,adunare,scadere, inmultire,impartire

def meniu():
    '''
    Meniul aplicatiei. Se specifica operatiile ce pot fi aplicate aspura numerelor date.
    '''
    print("Operatiile disponibile: ")
    print("1. Adunare")
    print("2. Scadere")
    print("3. Inmultire")
    print("4. Impartire")
    print("5. Conversie simpla")
    print("6. Conversie utilizand baza intermediara")
    print("x. Iesire din aplicatie")

def citireBaza():
    '''
    Citirea bazei unui numar (cu validare)
    :return baza: numar natural
    '''
    while True:
        try:
            baza=int(input("Baza: "))
            validareBaza(baza)
            break
        except ValueError:
            print("Dati un numar natural pentru baza!")
            continue
        except BazaError:
            print("Dati un numar >1 pentru baza!")
            continue
    return baza    
        
def citireNr(baza):
    '''
    Citirea de la tastatura a unui numar in baza 'baza' (cu validare)
    :param baza: numar natural
    :return numar: string
    '''
    while True:    
        try:
            numar=input("Numar=")
            validare(baza, numar)
            break
        except NumarIncorectError:
            print("Cifrele numarului trebuie sa fie mai mari ca 0 si mai mici strict decat baza",baza)
            continue
    return numar
    
def citireCifra(baza):
    '''
    Citirea unei cifra de la tastatura (cu validare)
    :param baza: numar natural
    :return cifra: string
    '''
    while True:    
        try:
            cifra=input("Cifra=")
            validareCifra(cifra)
            validare(baza, cifra)
            break
        except NumarIncorectError:
            print("Cifra trebuie sa fie mai mica strict decat baza",baza)
            continue
        except CifraError:
            print("Introduceti o singura cifra diferita de 0!")
            continue
    return cifra
    
    
def citireBazaOperatii():
    '''
    Citirea bazei in care se vor efectua operatiile (cu validare)
    :return baza: numar natural
    '''    
    while True:
        try:
            baza=int(input("Baza in care se va efectua operatia: "))
            validareBaza(baza)
            break
        except ValueError:
            print("Dati un numar natural pentru baza!")
            continue
        except BazaError:
            print("Dati un numar >1 pentru baza!")
            continue  
    return baza


def apel_adunare():
    '''
    Apelul operatiei de anunare.
    Se citesc datele de intrare (3 numare naturale pentru baze, 2 stringuri pentru numere) si se afiseaza rezultatul corespunzator.
    '''
    baza1=citireBaza()
    numar1=Numar(citireNr(baza1))
    baza2=citireBaza()
    numar2=Numar(citireNr(baza2))
    bazaOp=citireBazaOperatii()
    
    numar1_convert=conversie(numar1.getNumar(),baza1,bazaOp)
    numar2_convert=conversie(numar2.getNumar(),baza2,bazaOp)
    suma=adunare(numar1_convert,numar2_convert,bazaOp)
    #Crearea obiectelor de tip Rezultat(stringuri de afisat)
    numar1=Rezultat(numar1.getNumar())
    numar2=Rezultat(numar2.getNumar())
    numar1_convert=Rezultat(numar1_convert)
    numar2_convert=Rezultat(numar2_convert)
    suma=Rezultat(suma)
    
    print(numar1.getNumar()+'('+str(baza1)+') + '+numar2.getNumar()+'('+str(baza2)+') = '+ numar1_convert.getNumar()+'('+str(bazaOp)+') + '+numar2_convert.getNumar()+'('+str(bazaOp)+') = '+suma.getNumar()+'('+str(bazaOp)+')')   

def apel_scadere():
    '''
    Apelul operatiei de scadere.
    Se citesc datele de intrare (3 numare naturale pentru baze, 2 stringuri pentru numere) si se afiseaza rezultatul corespunzator.
    '''
    baza1=citireBaza()
    numar1=Numar(citireNr(baza1))
    baza2=citireBaza()
    numar2=Numar(citireNr(baza2))
    bazaOp=citireBazaOperatii()
    
    numar1_convert=conversie(numar1.getNumar(),baza1,bazaOp)
    numar2_convert=conversie(numar2.getNumar(),baza2,bazaOp)
    
    try:
        validareScadere(numar1_convert,numar2_convert)
        diferenta=scadere(numar1_convert,numar2_convert,bazaOp)
        #Crearea obiectelor de tip Rezultat(stringuri de afisat)
        numar1=Rezultat(numar1.getNumar())
        numar2=Rezultat(numar2.getNumar())
        numar1_convert=Rezultat(numar1_convert)
        numar2_convert=Rezultat(numar2_convert)
        diferenta=Rezultat(diferenta)
        
        print(numar1.getNumar()+'('+str(baza1)+') - '+numar2.getNumar()+'('+str(baza2)+") = "+ numar1_convert.getNumar()+'('+str(bazaOp)+') - '+numar2_convert.getNumar()+'('+str(bazaOp)+') = '+diferenta.getNumar()+'('+str(bazaOp)+')')   
    except ScadereError:
        print("Nu se poate efectua scaderea. Descazutul este mai mic decat scazatorul.")

def apel_inmultire():
    '''
    Apelul operatiei de inmultire.
    Se citesc datele de intrare (3 numare naturale pentru baze, 2 stringuri pentru numere) si se afiseaza rezultatul corespunzator.
    '''
    baza1=citireBaza()
    numar=Numar(citireNr(baza1))
    baza2=citireBaza()
    cifra=Numar(citireCifra(baza2))
    while True:
        bazaOp=citireBazaOperatii()
        if bazaOp>cifra.getCifra():
            break
        else:
            print("Dati un numar mai mare decat",str(cifra.getCifra()),"pentru baza!")
            continue
        
    numar_convert=conversie(numar.getNumar(),baza1,bazaOp) 
    cifra_convert=cifra.getCifra() 
    produs=inmultire(numar_convert,cifra_convert,bazaOp)
    #Crearea obiectelor de tip Rezultat(stringuri de afisat)
    numar=Rezultat(numar.getNumar())
    cifra=Rezultat(cifra.getNumar())
    produs=Rezultat(produs)
    
    print(numar.getNumar()+'('+str(baza1)+") * "+cifra.getNumar()+'('+str(baza2)+") = "+produs.getNumar()+'('+str(bazaOp)+")")   

def apel_impartire():
    '''
    Apelul operatiei de impartire.
    Se citesc datele de intrare (3 numare naturale pentru baze, 2 stringuri pentru numere) si se afiseaza rezultatul corespunzator.
    '''
    baza1=citireBaza()
    numar=Numar(citireNr(baza1))
    baza2=citireBaza()
    cifra=Numar(citireCifra(baza2))
    while True:
        bazaOp=citireBazaOperatii()
        if bazaOp>cifra.getCifra():
            break
        else:
            print("Dati un numar mai mare decat",str(cifra.getCifra()),"pentru baza!")
            continue
    numar_convert=conversie(numar.getNumar(),baza1,bazaOp) 
    cifra_convert=cifra.getCifra()
        
    rezultat=impartire(numar_convert,cifra_convert,bazaOp)
    #Crearea obiectelor de tip Rezultat(stringuri de afisat)
    numar=Rezultat(numar.getNumar())
    cifra=Rezultat(cifra.getNumar())
    cat=Rezultat(rezultat['cat'])
    rest=Rezultat(rezultat['rest'])
    
    print(numar.getNumar()+'('+str(baza1)+") : "+cifra.getNumar()+'('+str(baza2)+") = "+cat.getNumar()+'('+str(bazaOp)+")"+ " rest "+rest.getNumar()+'('+str(bazaOp)+')')   

def apel_conversie():
    '''
    Se converteste un numar dintr-o baza in alta
    '''       
    baza=citireBaza()
    numar=Numar(citireNr(baza))
    bazaOp=citireBazaOperatii()
    numar_convert=conversie(numar.getNumar(),baza,bazaOp) #Conversia din baza sursa in baza destinatie

    #Crearea obiectelor de tip Rezultat(stringuri de afisat)
    numar=Rezultat(numar.getNumar())
    numar_convert=Rezultat(numar_convert)
    
    print(numar.getNumar()+'('+str(baza)+') = '+numar_convert.getNumar()+'('+str(bazaOp)+')')
    

def apel_conversieInterm():
    '''
    Se converteste un numar dintr-o baza in alta utilizand o baza intermediara
    '''       
    baza1=citireBaza()
    numar=Numar(citireNr(baza1))
    baza2=citireBaza()
    bazaOp=citireBazaOperatii()
    
    numar_convert1=conversie(numar.getNumar(),baza1,bazaOp) #Conversia din baza sursa in baza intermediara
    numar_convert2=conversie(numar_convert1,bazaOp,baza2) #Conversia din baza intermediara in baza destinatie
    #Crearea obiectelor de tip Rezultat(stringuri de afisat)
    numar=Rezultat(numar.getNumar())
    numar_convert1=Rezultat(numar_convert1)
    numar_convert2=Rezultat(numar_convert2)
    
    print(numar.getNumar()+'('+str(baza1)+') = '+numar_convert1.getNumar()+'('+str(bazaOp)+') = '+numar_convert2.getNumar()+'('+str(baza2)+')')
    

            
def start():
    '''
    Punctul de intrare in aplictie
    Se executa subprogramele corespunzatoare operatiilor dorite din meniu.
    Se introduce de la tastatura: 1,2,3,4,5,6 sau x.
    '''   
    while True:
        meniu()
        comanda=input("Alegeti un numar de la 1 la 6: ")
        if comanda=='1':
            apel_adunare()            
        if comanda=='2':
            apel_scadere()
        if comanda=='3':
            apel_inmultire()
        if comanda=='4':
            apel_impartire()
        if comanda=='5':
            apel_conversie()
        if comanda=='6':
            apel_conversieInterm()    
        if comanda=='x':
            print("Multumim ca ati utilizat aplicatia!")
            break
        if comanda not in '123456xX':
            print("Comanda gresita! Alegeti din 1,2,3,4,5,6!")
            continue
    