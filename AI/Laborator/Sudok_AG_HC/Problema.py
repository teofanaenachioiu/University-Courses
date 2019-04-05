class Problema:
    def __init__(self, input, nrPopulatie, nrGeneratii):
        self.__input = input
        self.__nrPop = nrPopulatie
        self.__nrGeneratii = nrGeneratii

        self.__dimensiune = 0
        self.__configuratieInitiala = []
        self.__zero = []
        self.readFromFile()
        self.__output = input.split(".")[0] + "REZULTAT.txt"

    #scrierea in fisier
    def writeInFile(self, conf):
        f = open(self.__output, "w")
        f.write(str(self.__dimensiune)+'\n')
        f.write(str(conf))

    #citirea din fisier
    def readFromFile(self):
        fin = open(self.__input, 'r')
        self.__dimensiune = int(fin.readline())

        linii = []
        for line in fin.readlines():
            linii.extend([int(x) for x in line.split(',')])
        self.__configuratieInitiala = linii

    def getDimensiuneTabla(self):
        return self.__dimensiune

    def getNrPopulatie(self):
        return self.__nrPop

    def getConfiguratieInitiala(self):
        return self.__configuratieInitiala

    def getNrGeneratii(self):
        return self.__nrGeneratii

    #determina lista valorilor de pe un rand dat
    #in plus, da si numerele care nu se gasesc pe rand
    def row(self, rand):
        dim = self.getDimensiuneTabla()
        row = []
        nonApp = list(range(1, dim + 1))
        for index in range(dim):
            el = self.__configuratieInitiala[rand * dim + index]
            row.append(el)
            if el != 0:
                nonApp.remove(el)
        return row, nonApp

    #pozitiile libere pe pe un rand
    def emptyPoz(self, rand):
        dim = self.getDimensiuneTabla()
        emp = []
        for index in range(dim):
            el = self.__configuratieInitiala[rand * dim + index]
            if el == 0:
                emp.append(index)
        return emp

    #determina pozitiile libere de pe o configuratia initiala
    def empty(self):
        dim = len(self.__configuratieInitiala)
        emp = []
        for index in range(dim):
            el = self.__configuratieInitiala[index]
            if el == 0:
                emp.append(index)
        return emp
