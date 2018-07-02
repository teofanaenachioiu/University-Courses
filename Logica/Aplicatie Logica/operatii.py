'''
Created on 13 feb. 2018

@author: USER
'''
def zerorizare(numar,diferenta):
    '''
    Adaugarea de zerouri neseminificative la inceputul numarului.
    :prarm numar: lista de cifre
    :param diferenta: numar natural (exprima numarul de cifre de zero de adaugat)
    :return nr: lista de cifre
    '''
    zero=[0]*diferenta
    nr=zero+numar
    return nr

def eliminareZeroNesemnificativ(numar):
    '''
    Eliminarea de zerouri nesemnificative de la inceputul cuvantului
    :param numar: lista de cifre
    :return numar: lista de cifre (dupa eliminare)
    '''
    while len(numar)>1 and numar[0]==0:
            numar=numar[1:]   
    return numar
        
def adunare(numar1,numar2,baza):
    '''
    Se efectuiaza adunarea a doua numere intr-o baza oarecare
    :param numar1,numar2: lista de cifre
    :param baza: numar natural
    :return suma: lista de cifre
    '''
    index=max(len(numar1),len(numar2))
    nr1=zerorizare(numar1, index-len(numar1)+1)    
    nr2=zerorizare(numar2, index-len(numar2)+1)
    t=0
    suma=[0]*(index+1)
    while index>=0:
        suma[index]=(nr1[index]+nr2[index]+t)%baza
        t=(nr1[index]+nr2[index]+t)//baza
        index=index-1
    suma=eliminareZeroNesemnificativ(suma) 
    return suma        

def scadere(numar1,numar2,baza):
    '''
    Se efectueaza scaderea a doua numere intr-o baza oarecare
    Preconditii: numar1>=numar2
    :param numar1,numar2: lista de cifre
    :return diferenta: lista de cifre
    '''
    index=len(numar1)-1 
    nr1=numar1 
    nr2=zerorizare(numar2, index-len(numar2)+1)
    t=0
    diferenta=[0]*(index+1)
    while index>=0:
        if nr1[index]+t >= nr2[index]:
            diferenta[index]=nr1[index]+t-nr2[index]
            t=0
        else:
            diferenta[index]=baza+nr1[index]+t-nr2[index]
            t=-1
        index=index-1
    diferenta=eliminareZeroNesemnificativ(diferenta) 
    return diferenta

def impartire(numar1,numar2,baza): 
    '''
    Se efectueaza impartirea unui numar la o cifra
    :param numar1: lista de cifre
    :param numar2: cifra
    :return rezultat: dictionar cu liste de cifre pentru cat, resprectiv rest
    '''
    t=0
    listaCat=[]
    while len(numar1)>0:
        cifra=numar1[0]
        cat=(t*baza+cifra)//numar2
        listaCat.append(cat)
        rest=(t*baza+cifra)%numar2
        t=rest
        numar1=numar1[1:]
    listaCat=eliminareZeroNesemnificativ(listaCat)    
    rezultat={'cat':listaCat,'rest':[rest]}  
    return rezultat

def inmultire(numar1,numar2,baza): 
    '''
    Se efectueaza inmultirea unui numar cu o cifra intr-o baza oarecare
    :param numar1: lista de cifre
    :param numar2: cifra
    :return produs: lista de cifre
    '''
    t=0
    produs=[]
    while len(numar1)>0:
        cifra=numar1[-1]
        term=(cifra*numar2+t)%baza
        t=(cifra*numar2+t)//baza  
        produs.append(term)
        numar1=numar1[:-1]
    produs.append(t)    
    produs.reverse()
    produs=eliminareZeroNesemnificativ(produs)
    return produs

def conversie(numar,bazaS,bazaD):
    '''
    Se apeleaza convenabil converisa numarului dintr-o baza in alta
    :return: lista de cifre
    '''
    if bazaS==bazaD:
        return numar
    if bazaS==2: 
        if bazaD==4:
            return conversie2to4(numar) 
        if bazaD==8:
            return conversie2to8(numar) 
        if bazaD==16:
            return conversie2to16(numar)
    if bazaD==2:
        if bazaS==4:
            return conversie4to2(numar) 
        if bazaS==8:
            return conversie8to2(numar) 
        if bazaS==16:
            return conversie16to2(numar)
    if bazaS>bazaD:
        return conversieImpartiri(numar,bazaS,bazaD)
    if bazaS<bazaD:
        return conversieSubstitutie(numar,bazaS,bazaD)
    
      
def conversieImpartiri(numar,bazaS, bazaD):
    '''
    Se efectueaza conversia prin impartiri succesive.
    Preconditii: baza sursa > baza destinatie
    :param numar: lista de cifre
    :param bazaS,bazaD: numare naturale
    :return numarNou: lista de cifre (numarul dupa conversie)
    '''
    numarNou=[]
    cat=numar
    while cat!=[0]:
        rez=impartire(cat,bazaD,bazaS)
        cat=rez['cat']
        eliminareZeroNesemnificativ(cat)
        numarNou.extend(rez['rest'])
    numarNou.reverse()
    return numarNou    

def conversieSubstitutie(numar,bazaS,bazaD):
    '''
    Se efectueaza conversia prin substitutie.
    Preconditii: baza sursa < baza destinatie
    :param numar: lista de cifre
    :param bazaS,bazaD: numare naturale
    :return numarNou: lista de cifre (numarul dupa conversie)
    '''
    numarNou=[]
    p=[1]
    for i in range(len(numar)-1,-1,-1):
        termen=inmultire(p, numar[i], bazaD) 
        p=inmultire(p, bazaS, bazaD)
        numarNou=adunare(numarNou,termen,bazaD)
    return numarNou


def conversie2to4(numar):
    '''
    Se efectueaza conversia rapida din baza 2 in baza 4
    :param numar: lista de cifre
    :return numarNou: lista de cifre(numarul dupa conversie)
    '''
    conversieRapida={'[0, 0]':[0],'[0, 1]':[1],'[1, 0]':[2],'[1, 1]':[3]}
    numarNou=[]
    diferenta=len(numar)%2
    if diferenta>0:
        numar=zerorizare(numar,2-diferenta)
    while len(numar)>0:
        cifre=numar[:2]
        numarNou.extend(conversieRapida[str(cifre)])
        numar=numar[2:]
    return numarNou

def conversie2to8(numar):
    '''
    Se efectueaza conversia rapida din baza 2 in baza 8
    :param numar: lista de cifre
    :return numarNou: lista de cifre(numarul dupa conversie)
    '''
    conversieRapida={'[0, 0, 0]':[0],'[0, 0, 1]':[1],'[0, 1, 0]':[2],'[0, 1, 1]':[3],'[1, 0, 0]':[4],'[1, 0, 1]':[5],'[1, 1, 0]':[6],'[1, 1, 1]':[7]}
    numarNou=[]
    diferenta=len(numar)%3
    if diferenta>0:
        numar=zerorizare(numar,3-diferenta)
    while len(numar)>0:
        cifre=numar[:3]
        numarNou.extend(conversieRapida[str(cifre)])
        numar=numar[3:]
    return numarNou

def conversie2to16(numar):
    '''
    Se efectueaza conversia rapida din baza 2 in baza 16
    :param numar: lista de cifre
    :return numarNou: lista de cifre(numarul dupa conversie)
    '''
    conversieRapida={'[0, 0, 0, 0]':[0],'[0, 0, 0, 1]':[1],'[0, 0, 1, 0]':[2],'[0, 0, 1, 1]':[3],'[0, 1, 0, 0]':[4],'[0, 1, 0, 1]':[5],'[0, 1, 1, 0]':[6],'[0, 1, 1, 1]':[7],'[1, 0, 0, 0]':[8],'[1, 0, 0, 1]':[9],'[1, 0, 1, 0]':[10],'[1, 0, 1, 1]':[11],'[1, 1, 0, 0]':[12],'[1, 1, 0, 1]':[13],'[1, 1, 1, 0]':[14],'[1, 1, 1, 1]':[15]}
    numarNou=[]
    diferenta=len(numar)%4
    if diferenta>0:
        numar=zerorizare(numar,4-diferenta)
    while len(numar)>0:
        cifre=numar[:4]
        numarNou.extend(conversieRapida[str(cifre)])
        numar=numar[4:]
    return numarNou

def conversie4to2(numar):
    '''
    Se efectueaza conversia rapida din baza 4 in baza 2
    :param numar: lista de cifre
    :return numarNou: lista de cifre(numarul dupa conversie)
    '''
    conversieRapida={'0':[0, 0],'1':[0, 1],'2':[1, 0],'3':[1, 1]}
    numarNou=[]
    while len(numar)>0:
        cifre=numar[0]
        numarNou.extend(conversieRapida[str(cifre)])
        numar=numar[1:]
    numarNou=eliminareZeroNesemnificativ(numarNou)
    return numarNou

def conversie8to2(numar):
    '''
    Se efectueaza conversia rapida din baza 8 in baza 2
    :param numar: lista de cifre
    :return numarNou: lista de cifre(numarul dupa conversie)
    '''
    conversieRapida={'0':[0, 0, 0],'1':[0, 0, 1],'2':[0, 1, 0],'3':[0, 1, 1],'4':[1, 0, 0],'5':[1, 0, 1],'6':[1, 1, 0],'7':[1, 1, 1]}
    numarNou=[]
    while len(numar)>0:
        cifre=numar[0]
        numarNou.extend(conversieRapida[str(cifre)])
        numar=numar[1:]
    numarNou=eliminareZeroNesemnificativ(numarNou)
    return numarNou

def conversie16to2(numar):
    '''
    Se efectueaza conversia rapida din baza 16 in baza 2
    :param numar: lista de cifre
    :return numarNou: lista de cifre(numarul dupa conversie)
    '''
    conversieRapida={'0':[0, 0, 0, 0],'1':[0, 0, 0, 1],'2':[0, 0, 1, 0],'3':[0, 0, 1, 1],'4':[0, 1, 0, 0],'5':[0, 1, 0, 1],'6':[0, 1, 1, 0],'7':[0, 1, 1, 1],'8':[1, 0, 0, 0],'9':[1, 0, 0, 1],'10':[1, 0, 1, 0],'11':[1, 0, 1, 1],'12':[1, 1, 0, 0],'13':[1, 1, 0, 1],'14':[1, 1, 1, 0],'15':[1, 1, 1, 1]}
    numarNou=[]
    while len(numar)>0:
        cifre=numar[0]
        numarNou.extend(conversieRapida[str(cifre)])
        numar=numar[1:]
    numarNou=eliminareZeroNesemnificativ(numarNou)
    return numarNou
    