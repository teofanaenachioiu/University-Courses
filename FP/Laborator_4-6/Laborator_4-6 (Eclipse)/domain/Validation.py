from domain.Domain import *

def Test_verificare_actualizareCheltuiala():
    '''
    Se verifica daca functia <<verificare_actualizareCheltuiala>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_actualizareCheltuiala(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert verificare_actualizareCheltuiala(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},False) == True


def verificare_actualizareCheltuiala(cheltuieli,cheltuialaDeActualizat,gasit):
    '''
    Functia verifica daca exista cheltuiala ce se doreste a fi actualizata.
    :param cheltuieli: lista de dictionare
    :param cheltuialaDeActualizat: dictionar
    :param gasit: True/False
    '''
    for i in range(0,len(cheltuieli)):
        if cheltuialaDeActualizat==getCheltuialaCurenta(cheltuieli,i):
            gasit=True
    return gasit


Test_verificare_actualizareCheltuiala()


def Test_verificare_stergeZi():
    '''
    Se verifica daca functia <<verificare_stergeZi>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_stergeZi(cheltuieli, 1, False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},undo)
    assert verificare_stergeZi(cheltuieli, 1,False) == False
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':100, 'tipul':'intretinere'},undo)
    assert verificare_stergeZi(cheltuieli, 12,False) == True


def verificare_stergeZi(cheltuieli,zi,gasit):
    '''
    Functia vreifica daca exista in lista ziua de sters.
    :param cheltuieli: lista de dictionare
    :param zi: numar natural >0 si <32
    :param gasit: True/False
    '''
    i=0
    while i<len(cheltuieli):
        if zi == getZiua(cheltuieli,i) and getTip(cheltuieli,i)!='fara':
            gasit = True
            break
        else:
            i=i+1
    return gasit


Test_verificare_stergeZi()


def Test_verificare_stergeInterval():
    '''
    Se verifica daca functia <<stergeInterval>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_stergeInterval(cheltuieli, 1, 12,False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    assert verificare_stergeInterval(cheltuieli, 1, 3,False) == False
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':100, 'tipul':'intretinere'},undo)
    assert verificare_stergeInterval(cheltuieli, 10, 12,False) == True


def verificare_stergeInterval(cheltuieli,ziInceput,ziSfarsit,gasit):
    '''
    Functia verifica daca exista cheltuieli de sters intr-un interval de timp.
    :param cheltuieli: lista de dictionare
    :param ziInceput: numar natural >0 si <32
    :param ziSfarsit: numar natural >0 si <32
    :param gasit: True/False
    '''
    i = 0
    while i < len(cheltuieli):
        if ziInceput <= getZiua(cheltuieli,i) and ziSfarsit >= getZiua(cheltuieli,i) and getTip(cheltuieli,i)!='fara':
            gasit = True
            break
        else:
            i = i + 1
    return gasit


Test_verificare_stergeInterval()


def Test_verificare_stergeCheltuieli():
    '''
    Se verifica daca functia <<verificare_stergeCheltuieli>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_stergeCheltuieli(cheltuieli, 'mancare',False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13,'suma':10, 'tipul':'altele'},undo)
    assert verificare_stergeCheltuieli(cheltuieli, 'telefon',False) == False
    assert verificare_stergeCheltuieli(cheltuieli, 'mancare',False) == True
    assert verificare_stergeCheltuieli(cheltuieli, 'altele',False) == True


def verificare_stergeCheltuieli(cheltuieli,tipDat,gasit):
    '''
    Functia verifica daca exista tipul de sters in lista de cheltuieli
    :param cheltuieli: lista de dictionare
    :param tipSters: string
    :param gasit: True/False
    '''
    i = 0
    while i < len(cheltuieli):
        if tipDat ==getTip(cheltuieli,i):
            gasit = True
            break
        else:
            i = i + 1
    return gasit


Test_verificare_stergeCheltuieli()


def Test_verificare_tiparireSuma():
    '''
    Se verifica daca functia <<verificare_tiparireSuma>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_tiparireSuma(cheltuieli, 100,False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13,'suma':10, 'tipul':'altele'},undo)
    assert verificare_tiparireSuma(cheltuieli, 100,False) == True


def verificare_tiparireSuma(cheltuieli,sumaCitita,gasit):
    '''
    Se verifica daca estisa suma mai mica decat cea data in lista cheltuielilor.
    :param sumaCitita: numar real >0
    :param gasit: True/False
    '''
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli,i) > sumaCitita:
            gasit=True
            break
        i = i + 1
    return gasit


def Test_verificare_tiparireZiSuma():
    '''
    Functia testeaza daca <<verificare_tiparireZiSuma>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_tiparireZiSuma(cheltuieli,14, 100, False) == False
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert verificare_tiparireZiSuma(cheltuieli,13, 150, False) == True


def verificare_tiparireZiSuma(cheltuieli,ziCitita,sumaCitita,gasit):
    '''
    Functia verifica daca exista cheltuilei cu suma mai mica decat cea data si efectualte intr-o zi anterioara celei date.
    :param cheltuieli: lista de dictionare
    :param ziCitita: 
    :param gasit: True/False
    :return gasit
    '''
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli, i) < sumaCitita and getZiua(cheltuieli, i) < ziCitita and getTip(cheltuieli,i) != 'fara':
            gasit=True
        i = i + 1
    return gasit


Test_verificare_tiparireZiSuma()


def Test_verificare_tiparireTip():
    '''
    Functia testeaza daca <<verificare_tiparireTipa>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_tiparireTip(cheltuieli, 'mancare',False) == False
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert verificare_tiparireTip(cheltuieli, 'telefon',False) == False
    assert verificare_tiparireTip(cheltuieli, 'mancare',False) == True
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'mancare'},undo)
    assert verificare_tiparireTip(cheltuieli, 'mancare',False) == True


def verificare_tiparireTip(cheltuieli,tipCitit,gasit):
    '''
    Se verifica daca tipul data se gaseste in lista de cheltuieli.
    :param cheltuieli: lista de dictionare
    :param tipCitit:string
    :param gasit: True/False
    :return gasit
    '''
    i = 0
    while i < len(cheltuieli):
        if getTip(cheltuieli, i) == tipCitit and getTip(cheltuieli, i) != 'fara':
            gasit=True
            break
        i = i + 1
    return gasit


Test_verificare_tiparireTip()


def Test_verificare_tiparireSumaTip():
    '''
    Se verifica daca functia <<verificare_tiparireSumaTip>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_tiparireSumaTip(cheltuieli, 'mancare',False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13,'suma':10, 'tipul':'altele'},undo)
    assert verificare_tiparireSumaTip(cheltuieli, 'mancare',False) == True
    cheltuialaNoua(cheltuieli, {'ziua':14,'suma':30, 'tipul':'mancare'},undo)
    assert verificare_tiparireSumaTip(cheltuieli, 'mancare',False) == True


def verificare_tiparireSumaTip(cheltuieli,tipCitit,gasit):
    '''
    Se verifica daca tipul data se gaseste in lista de cheltuieli.
    :param cheltuieli: lista de dictionare
    :param tipCitit:string
    :param gasit: True/False
    :return gasit
    '''
    i = 0
    while i < len(cheltuieli):
        if getTip(cheltuieli,i) == tipCitit:
            gasit = True
            break
        i = i + 1
    return gasit


Test_verificare_tiparireSumaTip()


def Test_verificare_listaNevida():
    '''
    Functia verifica daca <<verificare_listaNevida>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_listaNevida(cheltuieli,True) == False
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert verificare_listaNevida(cheltuieli, True) == True
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 150, 'tipul': 'mancare'},undo)
    assert verificare_listaNevida(cheltuieli, True) == True


def verificare_listaNevida(cheltuieli,gasit):
    '''
    Se verifica daca lista este nevida.
    :param cheltuieli: lista de dictionare
    :param gasit: True/False
    :return gasit
    '''
    if getTip(cheltuieli,0)=='fara':
            gasit=False
    return gasit


Test_verificare_listaNevida()


def Test_verificare_tiparireSumaExacta():
    '''
    Functia verifica daca <<tiparireSumaExacta>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_tiparireSumaExacta(cheltuieli, 100,False) == False
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert verificare_tiparireSumaExacta(cheltuieli, 100,False) == False
    assert verificare_tiparireSumaExacta(cheltuieli, 120,False) == True
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'mancare'},undo)
    assert verificare_tiparireSumaExacta(cheltuieli, 10,False) == True


def verificare_tiparireSumaExacta(cheltuieli,sumaCitita,gasit):
    '''
    Se verifica daca exista suma data in lista de cheltuieli.
    :param cheltuieli: lista de dictionare
    :param sumaCitita:numar real>0
    :param gasit: True/False
    :return gasit
    '''
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli,i) == sumaCitita and getTip(cheltuieli,i) != 'fara':
            gasit=True
        i = i + 1
    return gasit


Test_verificare_tiparireSumaExacta()


def Test_verificare_eliminaTip():
    '''
    Se verifica daca functia <<verificare_eliminaTip>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_eliminaTip(cheltuieli, 'mancare', False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12,'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13,'suma':10, 'tipul':'altele'},undo)
    assert verificare_eliminaTip(cheltuieli, 'mancare',False) == True
    assert verificare_eliminaTip(cheltuieli, 'telefon',False) == False


def verificare_eliminaTip(cheltuieli,tipCitit,gasit):
    '''
    Se verifica daca tipul dat se gaseste in lista de cheltuieli.
    :param cheltuieli: lista de dictionare
    :param tipCitit:string
    :param gasit: True/False
    :return gasit
    '''
    i = 0
    while i < len(cheltuieli):
        if tipCitit == getTip(cheltuieli,i):
            gasit = True
            break
        else:
            i = i + 1
    return gasit


Test_verificare_eliminaTip()


def Test_verificare_eliminaSuma():
    '''
    Functia testeaza daca functia <<eliminaTip>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert verificare_eliminaSuma(cheltuieli,100,False) == False
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert verificare_eliminaSuma(cheltuieli,100,False) == True
    assert verificare_eliminaSuma(cheltuieli, 5,False) == False


def verificare_eliminaSuma(cheltuieli,sumaCitita,gasit):
    '''
    Se verifica daca exista cheltuieli cu suma mai mica decat cea data in lista.
    :param cheltuieli: lista de dictionare
    :param sumaCitita: numar real>0
    :param gasit: True/False
    :return gasit
    '''
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli,i)<sumaCitita and getTip(cheltuieli,i)!='fara':
            gasit=True
            break
        else:
            i = i + 1
    return gasit


Test_verificare_eliminaSuma()


def Test_validareDate():
    assert validareDate(ziValid=0)==False
    assert validareDate (sumaValid=-5)==False
    assert validareDate(tipValid='internet')==False


def validareDate(ziValid=1,sumaValid=0,tipValid='fara',corect=True):
    '''
    Functia valideaza datele de intrare.
    :param ziValid: numar natural >0 si <32
    :param sumaValid: numar real >0
    :param tipValid: string (respecta sabloanele)
    :return corect: True/False
    '''
    if sumaValid < 0:
        corect=False
    if tipValid != 'mancare' and tipValid != 'intretinere' and tipValid != 'imbracaminte' and tipValid != 'telefon' and tipValid != 'altele' and tipValid != 'fara':
        corect=False
    if ziValid < 1 or ziValid > 31:
        corect=False
    return corect  


Test_validareDate()