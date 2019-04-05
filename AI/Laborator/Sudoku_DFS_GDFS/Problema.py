from math import sqrt
from Configuration import Configuration
from utils import concatenateLines, fragmList, divideList


class Problema:
    def __init__(self, input, output):
        self.__dimensiune = 0
        self.__configuratie = []
        self.__input = input
        self.__output = output

    def getOutput(self):
        return self.__output

    #determina lista de coloane
    def getColoane(self, values):
        coloane = []
        for c in range(len(values)):
            col = []
            for lista in values:
                col.append(lista[c])
            coloane.append(col[:])
        return coloane[:]

    #determina lista cu cadrane
    def getCadrane(self, values):
        dimCadran = int(sqrt(len(values)))
        listaValori = concatenateLines(values)
        dimNewLists = int(len(listaValori) / dimCadran)
        first = divideList(listaValori, dimNewLists)
        second = fragmList(first, dimNewLists, dimCadran)

        return second

    def findZero(self, lista_liste):
        rez = []
        for i in range(len(lista_liste)):
            for j in range(len(lista_liste)):
                el = lista_liste[i][j]
                if el == 0:
                    rez.append([i, j])
        return rez

    def readFromFile(self):
        fin = open(self.__input, 'r')
        self.__dimensiune = fin.readline()

        linii = []
        for line in fin.readlines():
            linii.append([int(x) for x in line.split(',')])
        coloane = self.getColoane(linii)
        cadrane = self.getCadrane(linii)
        zero = self.findZero(linii)

        return Configuration(linii, coloane, cadrane, zero)

    #De la configuratia curenta cauta urmatoarele configuratii
    #returneaza lista de configuratii
    def nextConfigurationsSimple(self, conf, poz, viz):
        nextC = []
        a = poz[0]
        b = poz[1]

        for numar in range(1, conf.getSize() + 1):
            confff = conf.adaug([a, b, numar])
            # key = str(confff)
            if confff.isAlmostFinal(a, b, conf.getMyCadran(a, b)):  # and key not in viz:
                p = conf.getZero().index(poz)
                newConfig = conf.add(p)
                nextC.append(newConfig)
                # viz[key] = True
        conf.adaug([a, b, 0])
        return nextC

    #expandeaza configuratia curenta cu configuratiile urmatoare
    #returneaza lista de configuratii
    def expand(self, currentConf, viz):
        poz = currentConf.getPozitieLibera()
        if poz != []:
            return self.nextConfigurationsSimple(currentConf, poz, viz)
        return []

    #expandeaza configuratia
    def expandEuristic(self, currentConf, viz):
        poz = currentConf.getCeaMaiBunaPozitieLibera()
        if poz != []:
            return self.nextConfigurationsSimple(currentConf, poz, viz)
        return []

    def writeInFile(self, conf):
        f = open(self.__output, "w")
        f.write(str(self.__dimensiune))
        f.write(str(conf))







# for x in self.nextConfigurationsSimple(currentConf, poz, viz):
 #     myList.append(x)
