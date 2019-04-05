import random
from math import sqrt

import numpy
import secrets


random.seed()
class Solver1:
    def __init__(self, problema):
        self.__problema = problema
        self.__configuratieCurenta = self.__problema.getConfiguratieInitiala()[:]
        self.__configuratieInitiala = self.__problema.getConfiguratieInitiala()[:]
        self.__iteratie = 0
        self.__crt = 0
        self.__best = 1000000

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

    def swap(self, sudoku, poz1, poz2):
        a = sudoku[poz1]
        sudoku[poz1] = sudoku[poz2]
        sudoku[poz2] = a

    def getVecin(self,sudoku):
        a, b = self.getPozitii()
        s = sudoku[:]
        self.swap(s,a,b)
        return s



    def climb(self, sudoku):
        greseliConfigCurenta = self.nrGreseli(sudoku)
        self.__crt += 1

        if greseliConfigCurenta<self.__best:
            self.__best = greseliConfigCurenta
            print('ITERATION ' + str(self.__crt) + ' -> best fitness: ' + str(greseliConfigCurenta))

        if self.__crt%1000 == 0:
            print('ITERATION ' + str(self.__crt) + ': ' + str(greseliConfigCurenta))

        sudokuV = self.getVecin(sudoku)
        greseliConfigUrmatoare = self.nrGreseli(sudokuV)

        self.__iteratie += 1

        if greseliConfigUrmatoare == 0:
            return sudokuV

        if greseliConfigUrmatoare < greseliConfigCurenta:
            if self.__iteratie == 50:
                self.__iteratie = 0
                if numpy.random.rand() <0.05:
                    return sudoku
                return sudokuV
            return self.climb(sudokuV)
        else:
            if self.__iteratie == 50:
                self.__iteratie = 0
                if numpy.random.rand() <0.05:
                    return sudokuV
                return sudoku
            return self.climb(sudoku)

    def hillClimbing(self):
        temp = self.getUnSudokuCompletat()
        while self.nrGreseli(temp) > 0:
            temp = self.climb(temp)

        s = ''
        for i in range(len(temp)):
            s = s + ' ' + str(temp[i])
            if i % int(sqrt(len(temp))) == int(sqrt(len(temp))) - 1:
                s += '\n'
        self.__problema.writeInFile(s)
        return s
