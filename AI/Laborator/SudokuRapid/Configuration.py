from math import sqrt
from utils import distinctItems


class Configuration:
    def __init__(self, linii, coloane, cadrane, zero):
        self.__linii = linii
        self.__coloane = coloane
        self.__cadrane = cadrane
        self.__zero = zero
        #self.__complete = []

    def getLinii(self):
        return self.__linii

    def getColoane(self):
        return self.__coloane

    def getCadrane(self):
        return self.__cadrane

    def getZero(self):
        return self.__zero

    def getPozitieLibera(self):
        if len(self.__zero) == 0:
            return []
        return self.__zero[0]

    def getSize(self):
        return len(self.__linii)

    def __str__(self):
        s = ''
        for i in range(self.getSize()):
            for j in range(self.getSize() - 1):
                s = s + str(self.__linii[i][j]) + ","
            s = s + str(self.__linii[i][self.getSize() - 1]) + "\n"
        return s

    def getMyCadran(self, x, y):
        dimCadran = int(sqrt(self.getSize()))
        cadraneJos = int(x / dimCadran)
        cadraneDr = int(y / dimCadran)
        return cadraneJos * dimCadran + cadraneDr

    def add(self, poz):
        dimCadran = int(sqrt(self.getSize()))

        i = self.__zero[poz][0]
        j = self.__zero[poz][1]

        linii = [[self.__linii[x][y] for y in range(len(self.__linii[0]))] for x in range(len(self.__linii))]
        coloane = [[self.__coloane[x][y] for y in range(len(self.__coloane[0]))] for x in range(len(self.__coloane))]
        cadrane = [[self.__cadrane[x][y] for y in range(len(self.__cadrane[0]))] for x in range(len(self.__cadrane))]

        zero = [[self.__zero[x][y] for y in range(len(self.__zero[0]))] for x in range(len(self.__zero))]
        zero.pop(poz)
        newConf = Configuration(linii, coloane, cadrane, zero)
        self.__linii[i][j] = 0
        self.__coloane[j][i] = 0
        self.__cadrane[self.getMyCadran(i, j)][(j % dimCadran) * dimCadran + i % dimCadran] = 0
        #self.__complete.append([i, j])
        return newConf

    def adaug(self, nod):
        dimCadran = int(sqrt(self.getSize()))
        i = nod[0]
        j = nod[1]
        val = nod[2]
        self.__linii[i][j] = val
        self.__coloane[j][i] = val
        self.__cadrane[self.getMyCadran(i, j)][(j % dimCadran) * dimCadran + i % dimCadran] = val
        return self

    def isFinal(self):
        if self.getPozitieLibera() == []:
            return True
        return False

    def isAlmostFinal(self, i, j, k):
        if not distinctItems(self.getLinii()[i]):
            return False
        if not distinctItems(self.getColoane()[j]):
            return False
        if not distinctItems(self.getCadrane()[k]):
            return False
        return True

    def getCeaMaiBunaPozitieLibera(self):
        if len(self.__zero) == 0:
            return []
        min = self.getSize() * self.getSize() + 1
        x = -1
        y = -1
        for poz in self.__zero:
            i = poz[0]
            j = poz[1]

            nr = self.__linii[i].count(0)
            nr = nr + self.__coloane[j].count(0)
            nr = nr + self.__cadrane[self.getMyCadran(i, j)].count(0)
            if nr < min:
                min = nr
                x = i
                y = j
        return [x, y]
