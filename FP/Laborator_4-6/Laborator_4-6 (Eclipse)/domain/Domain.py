import copy

def initCheltuiala():
    '''
    Functia initializeaza o noua lista de cheltuieli.
    Functia returneaza lista de cheltuieli cu valoarea initiala {'ziua':1,'suma':0,'tipul':'fara'}, ce are semnificatia de "Nu exista cheltuieli".
    :return dictionar
    '''
    return [{'ziua':1,'suma':0,'tipul':'fara'}]


def initUndo():
    '''
    Functia initializeaza lista undo cu lista vida.
    :return lista
    '''
    undo=[]
    return undo


def getZiua(cheltuieli,i):
    '''
    Functia extrage ziua in care s-a facut o anumita cheltuiala
    :param cheltuieli: o lista de dictionare
    :param i: numar natural ce reprezinta ordinea in lista
    :return: un numar natural, >0 si <32
    '''
    return cheltuieli[i]["ziua"]


def getSuma(cheltuieli,i):
    '''
    Functia extrage suma corespunzatoare unei anumite cheltuieli
    :param cheltuieli: o lista de dictionare
    :param i: numar natural ce reprezinta ordinea in lista
    :return: un numar real pozitiv
    '''
    return cheltuieli[i]["suma"]


def getTip(cheltuieli,i):
    '''
    Functia extrage tipul corespunzator unei anumite cheltuieli
    :param cheltuieli: o lista de dictionare
    :param i: numar natural ce reprezinta ordinea in lista
    :return: un sir de caractere ce respecta sablonul dat
    '''
    return cheltuieli[i]["tipul"]


def getCheltuialaCurenta(cheltuieli,i):
    '''
    Functia extrage o anumita cheltuiala din lista de cheltuieli
    :param cheltuieli: o lista de dictionare
    :param i: numar natural ce reprezinta ordinea in lista
    :return: un dictionar
    '''
    return cheltuieli[i]


def Test_cheltuialaNoua():
    '''
    Functia testeaza daca functia <<cheltuialaNoua>> a fost implementata corect.
    '''
    cheltuieli=initCheltuiala()
    undo=initUndo()
    assert cheltuialaNoua(cheltuieli,{'ziua':12,'suma':120,'tipul':'mancare'},undo)==[{'ziua':12,'suma':120,'tipul':'mancare'}]
    assert cheltuialaNoua(cheltuieli,{'ziua':13,'suma':10,'tipul':'altele'},undo)==[{'ziua':12,'suma':120,'tipul':'mancare'},{'ziua':13,'suma':10,'tipul':'altele'}]


def cheltuialaNoua(cheltuieli,cheltuialaNouaCitita,undo):
    '''
    Functia adauga o noua cheltuiala in lista
    :param cheltuieli: lista de dictionare
    :param cheltuialaNouaCitita: dictionar
    :param undo: lista de cheltuieli
    :return: cheltuieli: lista de dictionare
    '''
    if cheltuieli==initCheltuiala():
        cheltuieli.remove(getCheltuialaCurenta(cheltuieli,0))
        cheltuieli.insert(0, cheltuialaNouaCitita)
    else:
        cheltuieli.append(cheltuialaNouaCitita)
    undo.append(cheltuieli)
    return cheltuieli


Test_cheltuialaNoua()


def Test_actualizareCheltuiala():
    '''
    Functia testeaza daca functia <<actualizareCheltuiala>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert actualizareCheltuiala(cheltuieli,{'ziua':12,'suma':120,'tipul':'mancare'},{'ziua':12,'suma':120,'tipul':'intretinere'},undo)==[{'ziua':1,'suma':0,'tipul':'fara'}]
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert actualizareCheltuiala(cheltuieli,{'ziua':12,'suma':120,'tipul':'mancare'},{'ziua':12,'suma':120,'tipul':'altele'},undo)==[{'ziua':12,'suma':120,'tipul':'altele'},{'ziua':13, 'suma':10, 'tipul':'altele'}]


def actualizareCheltuiala(cheltuieli,cheltuialaDeActualizat,cheltuialaNouaCitita,undo):
    '''
    Functia actualizeaza lista de cheltuieli inlocuind dictionarul <<cheltuialaDeActualizat>> cu <<cheltuialaNouaCitita>>.
    :param cheltuieli: lista de dictionare
    :param cheltuialaDeActualizat: dictionar
    :param cheltuialaNouaCitita: dictionar
    :param undo: lista de cheltuieli
    :return: cheltuieli: lista de dictionare
    '''
    for i in range(0,len(cheltuieli)):
        if cheltuialaDeActualizat==getCheltuialaCurenta(cheltuieli,i):
            cheltuieli.remove(cheltuialaDeActualizat)
            cheltuieli.insert(i, cheltuialaNouaCitita)
    undo.append(cheltuieli) #pun in undo
    return cheltuieli


Test_actualizareCheltuiala()


def Test_stergeZi():
    '''
    Functia testeaza daca functia <<stergeZi>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert stergeZi(cheltuieli,1,undo)==[{'ziua':1,'suma':0,'tipul':'fara'}]
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    assert stergeZi(cheltuieli,1,undo)==[{'ziua':12,'suma':120,'tipul':'mancare'}]
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':12,'suma':100,'tipul':'intretinere'},undo)
    assert stergeZi(cheltuieli,12,undo)==[{'ziua':13,'suma':10,'tipul':'altele'}]


def stergeZi(cheltuieli,zi,undo):
    '''
    Functia sterge toate cheltuielile dintr-o zi.
    In cazul in care in respectiva zi nu exista cheltuieli, lista nu se modifica.
    :param cheltuieli: lista de dictionare
    :param zi: un numar natural cuprins intre 1 si 31
    :param undo: lista de cheltuieli
    :return: cheltuieli: lista de dictionare
    '''
    i=0
    while i<len(cheltuieli):
        if zi == getZiua(cheltuieli,i) :
            cheltuieli.pop(i)
        else:
            i=i+1
    if cheltuieli==[]:
        cheltuieli=initCheltuiala()
    undo.append(cheltuieli)
    return cheltuieli


Test_stergeZi()


def Test_stergeInterval():
    '''
    Functia testeaza daca functia <<stergeInterval>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert stergeInterval(cheltuieli, 1,12,undo) == [{'ziua':1,'suma':0,'tipul':'fara'}]
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    assert stergeInterval(cheltuieli, 1,3,undo) == [{'ziua':12, 'suma':120, 'tipul':'mancare'}]
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':100, 'tipul':'intretinere'},undo)
    assert stergeInterval(cheltuieli, 10,12,undo) == [{'ziua':13, 'suma':10, 'tipul':'altele'}]


def stergeInterval(cheltuieli,ziInceput,ziSfarsit,undo):
    '''
    Functia sterge toate cheltuielile dintr-un interval de timp.
    :param cheltuieli: lista de dictionare
    :param ziInceput: un numar natural cuprins intre 1 si 31
    :param ziSfarsit: un numar natural cuprins intre 1 si 31, ziSfarsit<ziInceput
    :param undo: lista de cheltuieli
    :return: cheltuieli: lista de dictionare
    '''
    i = 0
    while i < len(cheltuieli):
        if ziInceput <= getZiua(cheltuieli,i) and ziSfarsit>=getZiua(cheltuieli,i):
            cheltuieli.pop(i)
        else:
            i = i + 1
    if cheltuieli==[]:
        cheltuieli=initCheltuiala()
    undo.append(cheltuieli)
    return cheltuieli


Test_stergeInterval()


def Test_stergeCheltuieli():
    '''
    Functia testeaza daca functia <<stergeCheltuieli>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert stergeCheltuieli(cheltuieli, 'mancare',undo)==[{'ziua':1,'suma':0,'tipul':'fara'}]
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert stergeCheltuieli(cheltuieli,'telefon',undo)==[{'ziua':12, 'suma':120, 'tipul':'mancare'},{'ziua':13, 'suma':10, 'tipul':'altele'}]
    assert stergeCheltuieli(cheltuieli,'mancare',undo)==[{'ziua':13, 'suma':10, 'tipul':'altele'}]
    assert stergeCheltuieli(cheltuieli, 'altele',undo)==[{'ziua':1,'suma':0,'tipul':'fara'}]


def stergeCheltuieli(cheltuieli,tipSters,undo):
    '''
    Functia sterge toate cheltuielile de un anumit tip.
    :param cheltuieli: lista de dictionare
    :param tipSters: un sir de caractere ce respecta sabloanele date
    :param undo: lista de cheltuieli
    :return: cheltuieli: lista de dictionare
    '''
    i = 0
    while i < len(cheltuieli):
        if tipSters==getTip(cheltuieli,i):
            cheltuieli.pop(i)
        else:
            i = i + 1
    if cheltuieli==[]:
        cheltuieli=initCheltuiala()
    undo.append(cheltuieli)
    return cheltuieli


Test_stergeCheltuieli()


def Test_tiparireSuma():
    '''
    Functia testeaza daca functia <<tiparireSuma>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert tiparireSuma(cheltuieli,100,undo) == []
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert tiparireSuma(cheltuieli,100,undo) == [{'ziua':12, 'suma':120, 'tipul':'mancare'}]


def tiparireSuma(cheltuieli,sumaCitita,undo):
    '''
    Functia tipareste cheltuielile ce au suma mai mare strict decat o suma data.
    :param cheltuieli: lista de dictionare
    :param sumaCitita: un numar real strict pozitiv
    :param undo: lista de cheltuieli
    :return: listaSuma: lista de dictionare
    '''
    listaSuma=[]
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli,i)> sumaCitita:
            listaSuma.append(getCheltuialaCurenta(cheltuieli,i))
        i = i + 1
    return listaSuma

Test_tiparireSuma()

def Test_tiparireZiSuma():
    '''
    Functia testeaza daca <<tiparireZiSuma>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert tiparireZiSuma(cheltuieli,14, 100,undo) == []
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert tiparireZiSuma(cheltuieli,13, 150,undo) == [{'ziua': 12, 'suma': 120, 'tipul': 'mancare'}]


def tiparireZiSuma(cheltuieli,ziCitita, sumaCitita,undo):
    '''
    Functia tipareste toate cheltuielile ce efectuate inainte de o zi data si mai mici decat o suma.
    :param cheltuieli: lista de dictionare
    :param ziCitita: un numar natural, >0 si <32
    :param sumaCitita: un numar real pozitiv
    :param undo: lista de cheltuieli
    :return: lista: lista de dictionare
    '''
    lista = []
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli, i) < sumaCitita and getZiua(cheltuieli,i)<ziCitita and getTip(cheltuieli,i)!='fara':
            lista.append(getCheltuialaCurenta(cheltuieli, i))
        i = i + 1
    return lista


Test_tiparireZiSuma()


def Test_tiparireTip():
    '''
    Functia verifica daca <<tiparireTip>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert tiparireTip(cheltuieli, 'mancare',undo) == []
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert tiparireTip(cheltuieli, 'telefon',undo) == []
    assert tiparireTip(cheltuieli, 'mancare',undo) == [{'ziua': 12, 'suma': 120, 'tipul': 'mancare'}]
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'mancare'},undo)
    assert tiparireTip(cheltuieli, 'mancare',undo) == [{'ziua': 12, 'suma': 120, 'tipul': 'mancare'},{'ziua': 13, 'suma': 10, 'tipul': 'mancare'}]


def tiparireTip(cheltuieli,tipCitit,undo):
    '''
    Functia tipareste toate cheltuielile de un anumit tip.
    :param cheltuieli: o lista de dictionare
    :param tipCitit: un sir de caractere ce respecta sabloanele date
    :param undo: lista de cheltuieli
    :return: listaTip: lista de dictionare
    '''
    listaTip = []
    i = 0
    while i < len(cheltuieli):
        if getTip(cheltuieli,i) == tipCitit and getTip(cheltuieli,i) != 'fara':
            listaTip.append(getCheltuialaCurenta(cheltuieli, i))
        i = i + 1
    return listaTip


Test_tiparireTip()


def Test_tiparireSumaTip():
    '''
    Functia testeaza daca functia <<tiparireSumaTip>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert tiparireSumaTip(cheltuieli, 'mancare',undo) == 0
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert tiparireSumaTip(cheltuieli, 'mancare',undo) == 120
    cheltuialaNoua(cheltuieli, {'ziua':14, 'suma':30, 'tipul':'mancare'},undo)
    assert tiparireSumaTip(cheltuieli, 'mancare',undo) == 150


def tiparireSumaTip(cheltuieli,tipCitit,undo):
    '''
    Functia tipareste suma totala pentru un anumit tip de cheltuiala.
    :param cheltuieli: lista de dictionare
    :param tipCitit: un sir de caractere ce respecta sabloanele date
    :param undo: lista de cheltuieli
    :return: S :numar real pozitiv
    '''
    i = 0
    S=0
    while i < len(cheltuieli):
        if getTip(cheltuieli,i) == tipCitit:
            S=S+getSuma(cheltuieli,i)
        i = i + 1
    return S


Test_tiparireSumaTip()


def Test_cheltuialaMaxima():
    '''
    Functia verifica daca <<cheltuialaMaxima>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert cheltuialaMaxima(cheltuieli,0,undo) == 0
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    assert cheltuialaMaxima(cheltuieli, 0,undo)==12
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert cheltuialaMaxima(cheltuieli,0,undo) == 12
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 150, 'tipul': 'mancare'},undo)
    assert cheltuialaMaxima(cheltuieli,0,undo) == 13


def cheltuialaMaxima(cheltuieli,Zimax,undo):
    '''
    Functia gaseste ziua in care cheltuiala e maxima.
    :param cheltuieli: lista de dictionare
    :param max: un numar natural, >0 si <32 ce reprezinta o zi din luna
    :param undo: lista de cheltuieli
    :return: max
    '''
    Smax=0
    for i in range(0,len(cheltuieli)-1):
        S = getSuma(cheltuieli, i)
        for j in range(i+1,len(cheltuieli)):
            if getZiua(cheltuieli,i)==getZiua(cheltuieli,j):
                S=S+getSuma(cheltuieli,j)
        if S>Smax:
            Smax=S
            Zimax=getZiua(cheltuieli,i)
    if len(cheltuieli)==1 and getTip(cheltuieli, 0)!='fara':
        Zimax=getZiua(cheltuieli,0)
    return Zimax


Test_cheltuialaMaxima()


def Test_tiparireSumaExacta():
    '''
    Functia verifica daca <<tiparireSumaExacta>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert tiparireSumaExacta(cheltuieli, 100,undo) == []
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert tiparireSumaExacta(cheltuieli, 100,undo) == []
    assert tiparireSumaExacta(cheltuieli, 120,undo) == [{'ziua': 12, 'suma': 120, 'tipul': 'mancare'}]
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'mancare'},undo)
    assert tiparireSumaExacta(cheltuieli, 10,undo) == [{'ziua': 13, 'suma': 10, 'tipul': 'altele'},{'ziua': 13, 'suma': 10, 'tipul': 'mancare'}]


def tiparireSumaExacta(cheltuieli,sumaCitita,undo):
    '''
    Functia tipareste toate cheltuielile ce au o anumita suma.
    :param cheltuieli: lista de dictionare
    :param sumaCitita: numar real pozitiv
    :param undo: lista de cheltuieli
    :return: listaSuma: lista de dictionare
    '''
    listaSuma = []
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli,i) == sumaCitita and getTip(cheltuieli,i) != 'fara':
            listaSuma.append(getCheltuialaCurenta(cheltuieli, i))
        i = i + 1
    return listaSuma


Test_tiparireSumaExacta()


def Test_sortareTip():
    '''
    Se verifica daca functia <<sortareTip>> a fost implementata corect.
    '''
    cheltuieli=initCheltuiala()
    undo=initUndo()
    assert sortareTip(cheltuieli,undo) == []
    cheltuialaNoua(cheltuieli, {'ziua': 12, 'suma': 120, 'tipul': 'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 10, 'tipul': 'altele'},undo)
    assert sortareTip(cheltuieli,undo) == [{'ziua': 12, 'suma': 120, 'tipul': 'mancare'},{'ziua': 13, 'suma': 10, 'tipul': 'altele'}]
    cheltuialaNoua(cheltuieli, {'ziua': 13, 'suma': 150, 'tipul': 'mancare'},undo)
    assert sortareTip(cheltuieli,undo) == [{'ziua': 13, 'suma': 150, 'tipul': 'mancare'},{'ziua': 12, 'suma': 120, 'tipul': 'mancare'},{'ziua': 13, 'suma': 10, 'tipul': 'altele'}]


def sortareTip(cheltuieli,undo):
    '''
    Functia tipareste cheltuielile sortate dupa tip.
    :param cheltuieli: lista de dictionare
    :param undo: lista de cheltuieli
    :return: lista: lista de dictionare
    '''
    lista= sorted(cheltuieli, key=lambda k: (k['tipul'], k['ziua'],k['suma']), reverse=True)
    if getTip(cheltuieli,0)=='fara':
        lista=[]
    undo.append(cheltuieli)
    return lista


Test_sortareTip()


def Test_eliminaTip():
    '''
    Functia testeaza daca functia <<eliminaTip>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert eliminaTip(cheltuieli, 'mancare',undo) == [{'ziua':1,'suma':0,'tipul':'fara'}]
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert eliminaTip(cheltuieli,'mancare',undo) == [{'ziua':12, 'suma':120, 'tipul':'mancare'}]
    assert eliminaTip(cheltuieli, 'telefon',undo) == [{'ziua':1,'suma':0,'tipul':'fara'}]


def eliminaTip(cheltuieli,tipCitit,undo):
    '''
    Functia elimina toate cheltuielile ce au alt tip decat cel dat.
    :param cheltuieli: lista de dictionare
    :param tipCitit: un sir de caractere ce respecta sabloanele date
    :param undo: lista de cheltuieli
    :return: cheltuieli: lista de dictionare
    '''
    i = 0
    while i < len(cheltuieli):
        if tipCitit != getTip(cheltuieli,i):
            cheltuieli.pop(i)
        else:
            i = i + 1
    if cheltuieli == []:
        cheltuieli = initCheltuiala()
    undo.append(cheltuieli)
    return cheltuieli


Test_eliminaTip()


def Test_eliminaSuma():
    '''
    Functia testeaza daca functia <<eliminaSuma>> a fost implementata corect.
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert eliminaSuma(cheltuieli,100,undo) == [{'ziua':1,'suma':0,'tipul':'fara'}]
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    cheltuialaNoua(cheltuieli, {'ziua':13, 'suma':10, 'tipul':'altele'},undo)
    assert eliminaSuma(cheltuieli,100,undo) == [{'ziua':13, 'suma':10, 'tipul':'altele'}]
    assert eliminaSuma(cheltuieli,5,undo) == [{'ziua':1,'suma':0,'tipul':'fara'}]


def eliminaSuma(cheltuieli,sumaCitita,undo):
    '''
    Functia elimina toate cheltuielile mai mari sau egale ca suma data.
    :param cheltuieli: lista de dictionare
    :param tipCitit: un sir de caractere ce respecta sabloanele date
    :param undo: lista de cheltuieli
    :return: cheltuieli: lista de dictionare
    '''
    i = 0
    while i < len(cheltuieli):
        if getSuma(cheltuieli,i)>=sumaCitita:
            cheltuieli.pop(i)
        else:
            i = i + 1
    if cheltuieli == []:
        cheltuieli = initCheltuiala()
    undo.append(cheltuieli)
    return cheltuieli


Test_eliminaSuma()


def Test_apel_undo():
    '''
    Se verifica daca functia <<apel_undo>> a fost implementata corect>>
    '''
    cheltuieli = initCheltuiala()
    undo=initUndo()
    assert apel_undo(cheltuieli,undo)==[]
    cheltuialaNoua(cheltuieli, {'ziua':12, 'suma':120, 'tipul':'mancare'},undo)
    assert apel_undo(cheltuieli,undo)==[]
    
    
def apel_undo(cheltuieli,undo):
    '''
    Functia implementeaza comanda <<undo>>
    Lista <<cheltuieli>> va fi reactualizata cu lista <<cheltuieli>> anterioara 
    :param cheltuieli: lista de dictionare
    :param undo: lista de cheltuieli
    :return cheltuieli/lista vida
    '''
    if len(undo)==1:
        undo.pop()
        return undo
    if undo==[]:
        return undo
    else:
        undo.pop() #elimin ultima lista de cheltuieli
        cheltuieli=copy.deepcopy(undo[-1]) #actualizez lista de cheltuieli cu lista anterioara
        return cheltuieli
    
    
Test_apel_undo()