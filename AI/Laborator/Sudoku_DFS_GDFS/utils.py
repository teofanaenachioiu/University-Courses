
#Sterge toate zerourile din lista
def removeZero(lista):
    return [x for x in lista if x != 0]

#Verifica daca elementele din lista (exceptand zerourile) sunt distincte
def distinctItems(lista):
    newList = removeZero(lista)
    if len(newList) == 0:
        return True
    if len(newList) != len(set(newList)):
        return False
    return True

#Cancatenare de liste
def concatenateLines(list_of_lists):
    return sum(list_of_lists, [])

#Divizeaza lista in listute
def divideList(listaValori, dim):
    start = 0
    end = dim
    first = []
    while end <= len(listaValori):
        first.append(listaValori[start:end])
        start = end
        end = end + dim
    return first

#Fragmenteaza lista in mai multe listute
def fragmList(list_of_list, dimLista, dimCadran):
    second = []
    for lista in list_of_list:
        aux = []
        for j in range(int(dimLista / dimCadran)):
            for mul in range(dimCadran):
                aux.append(lista[j + mul * dimLista // dimCadran])
            if j % dimCadran == dimCadran - 1:
                second.append(aux)
                aux = []
    return second

def findBestPlace(conf):
    return conf.findBestEmptyPlace
