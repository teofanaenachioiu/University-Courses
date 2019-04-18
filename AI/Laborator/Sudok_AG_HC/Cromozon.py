from math import sqrt


class Cromozon:
    def __init__(self, values):
        self.__configuratie = values[:]
        self.__dimensiune = int(sqrt(len(values)))
        self.__subDimensiune = int(sqrt(self.__dimensiune))
        self.__fitness = 0

    def __str__(self):
        s = ''
        for i in range(len(self.__configuratie)):
            if i % self.__dimensiune == 0:
                s = s + str(self.__configuratie[i])
            else:
                s = s + ',' + str(self.__configuratie[i])
            if i % self.__dimensiune == self.__dimensiune - 1:
                s += '\n'
        return s

    def __eq__(self, other):
        if self.__configuratie == other.getConfiguratie():
            return True
        else:
            return False

    def getConfiguratie(self):
        return self.__configuratie

    def getDimensiune(self):
        return self.__dimensiune

    def getFitness(self):
        return self.__fitness

    def getSubDimensiune(self):
        return self.__subDimensiune

    #determina lista elementelor de pe randul r
    def row(self, r):
        row = []
        for index in range(self.getDimensiune()):
            row.append(self.__configuratie[r * self.getDimensiune() + index])
        return row

    #determina lista elementelor de pe coloana c
    def column(self, c):
        column = []
        for index in range(self.getDimensiune()):
            column.append(self.__configuratie[index * self.getDimensiune() + c])
        return column

    #determina lista elementelor de pe cadranul b
    def block(self, b):
        block = []
        nr = self.getSubDimensiune()
        dim = self.getDimensiune()
        blockstart = int(b / nr) * dim * nr + (b - int(b / nr) * nr) * nr
        for indexX in range(nr):
            for indexY in range(nr):
                block.append(self.getConfiguratie()[blockstart + indexX * dim + indexY])
        return block

   #interschimba doua valori din configuratia cromozonului
    def swap(self, poz1, poz2):
        a = self.__configuratie[poz1]
        self.__configuratie[poz1] = self.__configuratie[poz2]
        self.__configuratie[poz2] = a

    #verifica daca elementele dintr-o lista sunt distincte
    def distincte(self, lista):
        lista.sort()
        for index in range(len(lista) - 1):
            if lista[index] == lista[index + 1]:
                return True
        return False

    # functia de FITNESS varianta 1
    # determin cate duplicate am pe linie/coloana/cadran + cate nepotriviri fata de tabela initiala
    # numar de cate ori apare i pe linia r, de cate ori apare i pe coloana c etc ...
    # nu iau in considerare elementul cand calculez eroarea. Vad doar daca are dublura
    def eval(self, sudoku):
        err = 0
        for index in range(self.getDimensiune()):
            for element in [self.row(index), self.column(index), self.block(index)]:
                for getal in list(range(1, self.getDimensiune() + 1)):
                    if (element.count(getal)) > 1:
                        err += element.count(getal) - 1

        for i, getal in enumerate(self.getConfiguratie()):
            if getal != sudoku[i] and sudoku[i] != 0:
                err += 1
            if getal == 0:
                err += 1
        return err

    # functia de FITNESS varianta 2
    # determin cate duplicate am pe linie/coloana/cadran + cate nepotriviri fata de tabela initiala
    # pentru fiecare element din lista de linii, coloane, cadrane verific daca mai exista un alt element egal cu el
    # nu iau in considerare elementul cand calculez eroarea. Vad doar daca are dublura
    def eval1(self):
        err = 0
        for r in range(self.getDimensiune()):
            rr = self.row(r)
            for el in rr:
                err = err + rr.count(el) - 1
        for r in range(self.getDimensiune()):
            rr = self.column(r)
            for el in rr:
                err = err + rr.count(el) - 1
        for r in range(self.getDimensiune()):
            rr = self.block(r)
            for el in rr:
                err = err + rr.count(el) - 1
        # for i in range(len(self.getConfiguratie())):
        #     if self.getConfiguratie()[i] != sudoku[i] and sudoku[i] != 0:
        #         err += 1
        #     if self.getConfiguratie()[i] == 0:
        #         err += 1
        self.__fitness = err
        return err

    #functia
    def eval2(self):
        dim = self.getDimensiune()
        sum = dim * (dim + 1) // 2
        eroare = 0

        for index in range(dim):
            s = 0
            col = self.column(index)
            for r in col:
                s += r
            err = abs(s - sum)
            eroare += err

        for index in range(dim):
            s = 0
            col = self.block(index)
            for r in col:
                s += r
            err = abs(s - sum)
            eroare += err

        for index in range(self.getDimensiune()):
            for element in [self.column(index), self.block(index)]:
                for getal in list(range(1, self.getDimensiune() + 1)):
                    if (element.count(getal)) > 1:
                        eroare += element.count(getal) - 1

        self.__fitness = eroare
        return eroare

    def setFitness(self, fit):
        self.__fitness = fit
