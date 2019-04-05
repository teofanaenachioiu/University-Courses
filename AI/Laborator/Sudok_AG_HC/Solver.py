from math import sqrt

import numpy
import secrets

import matplotlib.pyplot as plt

class Solver:
    def __init__(self, problema, nrGeneratii):
        self.__problema = problema
        self.__configuratieCurenta = self.__problema.getConfiguratieInitiala()[:]
        self.__configuratieInitiala = self.__problema.getConfiguratieInitiala()[:]
        self.__iteratie = 0
        self.__nrGeneratii = nrGeneratii
        self.__crt = 0
        self.__best = 1000000
        self.best=[]

    # dimensiunea tabelei de joc
    def getDimensiune(self):
        return self.__problema.getDimensiuneTabla()

    #dimensiunea unei tabele mici
    def getSubDimensiune(self):
        return int(sqrt(self.getDimensiune()))

    #configuratia initiala a jocului
    def getInitialSudoku(self):
        return self.__configuratieInitiala

    #configuratia curenta a jocului
    def getCurentSudoku(self):
        return self.__configuratieCurenta

    #numarul de generatii
    def getNrGeneratii(self):
        return self.__problema.getNrGeneratii()

    #determina numerele de pe un rand + numerele care nu exista
    #functia e utila atunci cand cream un sudoku completat
    def rowApp(self, rand):
        dim = self.getDimensiune()
        row = []
        nonApp = list(range(1, dim + 1))
        for index in range(dim):
            el = self.__configuratieInitiala[rand * dim + index]
            row.append(el)
            if el != 0:
                nonApp.remove(el)
        return row, nonApp

    #lista valorilor de pe randul r
    def row(self, sudoku, r):
        row = []
        for index in range(self.getDimensiune()):
            row.append(sudoku[r * self.getDimensiune() + index])
        return row

    #lista valorilor de pe coloana c
    def column(self, sudoku, c):
        column = []
        for index in range(self.getDimensiune()):
            column.append(sudoku[index * self.getDimensiune() + c])
        return column

    #lista valorilor de pe cadranul b
    def block(self, sudoku, b):
        block = []
        nr = self.getSubDimensiune()
        dim = self.getDimensiune()
        blockstart = int(b / nr) * dim * nr + (b - int(b / nr) * nr) * nr
        for indexX in range(nr):
            for indexY in range(nr):
                block.append(sudoku[blockstart + indexX * dim + indexY])
        return block

    #determina un sudoku completat
    #returneaza o lista de valori
    def getUnSudokuCompletat(self):
        values = []
        for ind in range(self.getDimensiune()):
            row, nonApp = self.rowApp(ind)
            while row.count(0) > 0:
                i = row.index(0)
                row[i] = secrets.choice(nonApp)
                nonApp.remove(row[i])
                numpy.random.shuffle(nonApp)
            values.extend(row)
        return values

    #determina fitnessul unei tabele
    #sudoku = configuratia initiala a tabelei
    def nrGreseli(self, sudoku):
        err = 0
        for r in range(self.getDimensiune()):
            rr = self.row(sudoku, r)
            for el in rr:
                err = err + rr.count(el) - 1
        for r in range(self.getDimensiune()):
            rr = self.column(sudoku, r)
            for el in rr:
                err = err + rr.count(el) - 1
        for r in range(self.getDimensiune()):
            rr = self.block(sudoku, r)
            for el in rr:
                err = err + rr.count(el) - 1
        for i in range(len(sudoku)):
            if sudoku[i] != self.__configuratieInitiala[i] and self.__configuratieInitiala[i] != 0:
                err += 1
            if sudoku[i] == 0:
                err += 1
        return err

    #determina 2 pozitii de interschimbat
    #functia e utila la crearea unui vecin
    def getPozitii(self):
        dim = self.getDimensiune()
        while True:
            lin = numpy.random.randint(0, dim)
            c1 = numpy.random.randint(0, dim)
            c2 = numpy.random.randint(0, dim)
            poz1 = lin * dim + c1
            poz2 = lin * dim + c2
            rand, app = self.rowApp(lin)
            if rand[c1] == 0 and rand[c2] == 0:
                break
            else:
                continue
        return poz1, poz2

    #interschimba doua valori pe pe tabla (poz1, poz2 sunt alese a.i. sa fie de pe aceeasi linie)
    #functia e utila la crearea unui vecin
    def swap(self, sudoku, poz1, poz2):
        a = sudoku[poz1]
        sudoku[poz1] = sudoku[poz2]
        sudoku[poz2] = a

    #functia de deterninare a celui mai bun vecin
    def climb(self, sudoku):
        greseliConfigCurenta = self.nrGreseli(sudoku)
        self.best.append(greseliConfigCurenta)
        self.__nrGeneratii-=1
        self.__crt += 1

        if greseliConfigCurenta<self.__best:
            self.__best = greseliConfigCurenta
            print('ITERATION ' + str(self.__crt) + ' -> best fitness: ' + str(greseliConfigCurenta))

        if self.__crt%1000 == 0:
            print('ITERATION ' + str(self.__crt) + ': ' + str(greseliConfigCurenta))

        a, b = self.getPozitii()
        self.swap(sudoku, a, b)
        greseliConfigUrmatoare = self.nrGreseli(sudoku)

        self.__iteratie += 1

        if greseliConfigUrmatoare == 0:
            print('ITERATION ' + str(self.__crt) + ' -> best fitness: ' + str(greseliConfigUrmatoare))
            return sudoku

        if greseliConfigUrmatoare < greseliConfigCurenta:
            if self.__iteratie == 50:
                self.__iteratie = 0
                if numpy.random.rand() <0.05:
                    self.swap(sudoku, a, b)
                return sudoku
            return self.climb(sudoku)
        else:
            self.swap(sudoku, a, b)
            if self.__iteratie == 50:
                self.__iteratie = 0
                if numpy.random.rand() <0.05:
                    self.swap(sudoku, a, b)
                return sudoku
            return self.climb(sudoku)

    #functia principala
    #se apeleaza functia de determinare a celui mai bun vecin
    def hillClimbing(self):
        temp = self.getUnSudokuCompletat()
        while self.nrGreseli(temp) > 0:
        # while self.__nrGeneratii>0 and self.nrGreseli(temp) > 0:
            temp = self.climb(temp)

        self.best.append(0)
        plt.plot(list(range(len(self.best))),self.best, 'ro')
        plt.axis([0, len(self.best) , 0, 50])
        plt.show()

        s = ''
        for i in range(len(temp)):
            if i % temp == 0:
                s = s + str(temp[i])
            else:
                s = s + ',' + str(temp[i])
            if i % temp == temp - 1:
                s += '\n'
        self.__problema.writeInFile(s)

        return s
