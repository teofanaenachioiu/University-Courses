import random
from math import sqrt

import numpy
import secrets
import matplotlib.pyplot as plt

class SolverHC:
    def __init__(self, problema, generatii):
        self.__problema = problema
        self.__configuratieCurenta = self.__problema.getConfiguratieInitiala()[:]
        self.__configuratieInitiala = self.__problema.getConfiguratieInitiala()[:]
        self.__iteratie = 0
        self.__crt = 0
        self.__best = 1000000
        self.__popSize = 10
        self.__nrGeneratii=generatii
        self.bestFit = []

    def getDimensiune(self):
        return self.__problema.getDimensiuneTabla()

    def getSubDimensiune(self):
        return int(sqrt(self.getDimensiune()))

    def getInitialSudoku(self):
        return self.__configuratieInitiala

    def getCurentSudoku(self):
        return self.__configuratieCurenta

    def getNrGeneratii(self):
        return self.__problema.getNrGeneratii()

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

    def row(self, sudoku, r):
        row = []
        for index in range(self.getDimensiune()):
            row.append(sudoku[r * self.getDimensiune() + index])
        return row

    def column(self, sudoku, c):
        column = []
        for index in range(self.getDimensiune()):
            column.append(sudoku[index * self.getDimensiune() + c])
        return column

    def block(self, sudoku, b):
        block = []
        nr = self.getSubDimensiune()
        dim = self.getDimensiune()
        blockstart = int(b / nr) * dim * nr + (b - int(b / nr) * nr) * nr
        for indexX in range(nr):
            for indexY in range(nr):
                block.append(sudoku[blockstart + indexX * dim + indexY])
        return block

    def getUnSudokuCompletat(self):
        lista = [self.getDimensiune() for x in range(self.getDimensiune())]
        for el in self.getInitialSudoku():
            if el != 0:
                lista[el - 1] -= 1

        values = []

        nr = 1
        for frec in lista:
            while frec != 0:
                values.append(nr)
                frec -= 1
            nr += 1

        val = self.getInitialSudoku()[:]
        numere = values[:]
        random.shuffle(numere)
        empty = self.__problema.empty()
        while len(empty) != 0:
            poz = empty.pop()
            val[poz] = numere.pop()
        return val

    def nrGreseli(self, sudoku):
        err = 0
        for r in range(self.getDimensiune()):
            rr = self.row(sudoku, r)
            for el in rr:
                err = err + rr.count(el) - 1
        for r in range(self.getDimensiune()):
            rr = self.column(sudoku, r)
            for el in rr:
                err = err + rr.count(el) - 2
        for r in range(self.getDimensiune()):
            rr = self.block(sudoku, r)
            for el in rr:
                err = err + rr.count(el) - 2
        return err

    def getPozitii(self):
        emp = self.__problema.empty()
        c1 = secrets.choice(emp)
        emp.remove(c1)
        c2 = secrets.choice(emp)
        return c1, c2

    def swap(self, su, poz1, poz2):
        sudoku = su[:]
        a = sudoku[poz1]
        sudoku[poz1] = sudoku[poz2]
        sudoku[poz2] = a
        return sudoku

    def climb(self, sudoku):

        while True:
            greseliConfigCurenta = self.nrGreseli(sudoku)
            self.__crt += 1
            self.bestFit.append(greseliConfigCurenta)

            if self.__crt % 10 == 0:
                print('ITERATION ' + str(self.__crt) + ': ' + str(greseliConfigCurenta))

            a, b = self.getPozitii()
            sudokuUrm = self.swap(sudoku, a, b)[:]
            greseliConfigUrmatoare = self.nrGreseli(sudokuUrm)
            self.__iteratie += 1

            if greseliConfigUrmatoare == 0:
                return sudokuUrm
            if greseliConfigUrmatoare < greseliConfigCurenta:
                sudoku = sudokuUrm

            if self.__crt > 10000:

                return sudoku

    def hillClimbing(self):
        temp = self.climb(self.getUnSudokuCompletat())


        self.bestFit.append(0)
        plt.plot(list(range(len(self.bestFit))), self.bestFit, 'ro')
        plt.axis([0, len(self.bestFit), 0, 50])
        plt.show()

        s = ''
        for i in range(len(temp)):
            s = s + ' ' + str(temp[i])
            if i % int(sqrt(len(temp))) == int(sqrt(len(temp))) - 1:
                s += '\n'
        self.__problema.writeInFile(s)
        return s
