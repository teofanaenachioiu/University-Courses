from math import sqrt
from random import randrange

import numpy
import secrets

from Cromozon import Cromozon

import matplotlib.pyplot as plt


class SolverAE:
    def __init__(self, problema):
        self.__problema = problema
        self.__populatie = []
        self.fit = []
        self.iteratii = 0

    #pozitiile libere din tabela intiala
    def getPozitiiLibere(self):
        return self.__problema.empty()

    #lista de cromozoni
    def getPopulatie(self):
        return self.__populatie

    #dimensiunea tabelei
    def getDimensiune(self):
        return self.__problema.getDimensiuneTabla()

    #returneaza tabela initiala
    def getInitialSudoku(self):
        return self.__problema.getConfiguratieInitiala()

    #returneaza dimensiunea populatiei
    def getNrPopulatie(self):
        return self.__problema.getNrPopulatie()

    #returneaza numarul de generarii (numarul de iteratii)
    def getNrGeneratii(self):
        return self.__problema.getNrGeneratii()

    #adauga un cromozon in populatie
    def adaugaCromozon(self, cr):
        self.__populatie.append(cr)

    #sorteaza cromozonii din populatie in functie de fitness
    def sorteazaPopulatie(self):
        self.__populatie = sorted(self.__populatie, key=lambda cr: cr.getFitness())

    # verifica daca cromozonul exista sau nu in populatie
    def existaCromozonInPopulatie(self, cromozon):
        if self.__populatie.count(cromozon) != 0:
            return True
        else:
            return False

    # se genereaza o populatie de dimensiune data
    def generare(self):
        gen = 0
        while gen < self.getNrPopulatie():
            values = []
            for ind in range(self.getDimensiune()):
                row, nonApp = self.__problema.row(ind)
                while row.count(0) > 0:
                    i = row.index(0)
                    row[i] = secrets.choice(nonApp)
                    nonApp.remove(row[i])
                    numpy.random.shuffle(nonApp)
                values.extend(row)

            cromozon = Cromozon(values)
            self.adaugaCromozon(cromozon)
            gen += 1

    # se evalueaza intreaga populatie
    def evaluare(self):
        for cr in self.__populatie:
            cr.eval1(self.getInitialSudoku())

    # se determina cel mai bun individ din populatie
    def best(self):
        self.sorteazaPopulatie()
        return self.__populatie[0]

    # se determina cel mai slab individ din populatie
    def worst(self):
        self.sorteazaPopulatie()
        # self.worstFit = self.__populatie[-1]
        return self.__populatie[-1]

    # cel mai bun fitness
    def bestFitness(self):
        self.sorteazaPopulatie()
        return self.__populatie[0].getFitness()

    # functia de selectie
    def selectie(self):
        numpy.random.shuffle(self.__populatie)
        pos1 = randrange(self.getNrPopulatie())
        pos2 = randrange(self.getNrPopulatie())
        if (self.__populatie[pos1].getFitness() < self.__populatie[pos2].getFitness()):
            return self.__populatie[pos1]
        return self.__populatie[pos2]

    # functia de incrucisare
    def crossover(self, m, t):
        f = []
        dim = self.getDimensiune()
        for i in range(dim):
            p = numpy.random.rand()
            if p < 0.5:
                f.extend(m.row(i)[:])
            else:
                f.extend(t.row(i)[:])
        c = Cromozon(f)
        return c

    # selectia populatiei
    def crossoverPopulatie(self):
        dim = self.getNrPopulatie()
        populatieNoua = []
        for index in range(dim):
            m = self.selectie()
            t = self.selectie()
            f = self.crossover(m, t)
            f.eval1(self.getInitialSudoku())
            populatieNoua.append(f)
        self.__populatie.extend(populatieNoua)
        self.sorteazaPopulatie()
        self.__populatie = self.__populatie[:dim]

    # functia de mutatie a unui cromozon
    def mutatie1(self, off, p):
        if numpy.random.rand() < p:
            dim = self.getDimensiune()
            while True:
                lin = numpy.random.randint(0, dim)
                emp = self.__problema.emptyPoz(lin)
                if len(emp) > 1:
                    c1 = secrets.choice(emp)
                    emp.remove(c1)
                    c2 = secrets.choice(emp)
                    poz1 = lin * dim + c1
                    poz2 = lin * dim + c2
                    off.swap(poz1, poz2)
                    break
                else:
                    continue

    # mutatie pe jumatatea slaba a populatiei
    def mutatiePopulatie(self, p):
        start = self.getNrPopulatie() // 4
        # start = self.getNrPopulatie() // 2
        end = self.getNrPopulatie()
        for index in range(start, end):
            cr = self.__populatie[index]
            self.mutatie1(cr, p)
            cr.eval1(self.getInitialSudoku())
            # self.evaluareCromozon(cr)
        # self.evaluare()

    def printPopulatie(self):
        for el in self.__populatie:
            print(el)

    #NU MAI FOLOSESC
    def reset(self, dim):
        pop = []
        gen = 0
        while gen < dim:
            values = []
            for ind in range(self.getDimensiune()):
                row, nonApp = self.__problema.row(ind)
                while row.count(0) > 0:
                    i = row.index(0)
                    row[i] = secrets.choice(nonApp)
                    nonApp.remove(row[i])
                    numpy.random.shuffle(nonApp)
                values.extend(row)

            cromozon = Cromozon(values)
            pop.append(cromozon)
            gen += 1
        return pop

    # algoritmul evolutiv
    # se genereaza o populatie
    # se evalueaza populatia
    # se face incrucisarea populatiei
    # se aleg cei mai buni indivizi din populatie
    # se face mulatie pe populatie
    def algoritmEvolutiv(self):
        self.generare()
        self.evaluare()
        self.sorteazaPopulatie()
        print('initial fitness: ' + str(self.bestFitness()))
        count = 0
        while True:
            self.crossoverPopulatie()
            count += 1
            actualBest = self.bestFitness()
            self.fit.append(actualBest)
            print('ITERATION ' + str(count) + ' -> best fitness: ' + str(self.bestFitness()))
            # print('ITERATION ' + str(count) + ' -> best fitness: ' + str(self.bestFitness())+ ' worst: '+str(self.worst().getFitness()))
            # if self.bestFitness() == self.worst().getFitness():
            #     self.iteratii += 1
            # if self.iteratii == 2000:
                # self.iteratii = 0
                # print('Reset populatie')
                # dim = self.getNrPopulatie() // 2 + 1
                # newPop = self.reset(dim)
                # self.sorteazaPopulatie()
                # self.__populatie = self.__populatie[:dim]
                # self.__populatie.extend(newPop)
                # self.evaluare()
            if actualBest == 0:
                break
            self.mutatiePopulatie(0.7)
            self.sorteazaPopulatie()
            if self.bestFitness() == 0:
                break

        print('best fitness: ' + str(self.bestFitness()))
        self.fit.append(self.bestFitness())
        plt.plot(list(range(len(self.fit))), self.fit, 'ro')
        plt.axis([0, len(self.fit), 0, 50])
        plt.show()

        c = self.best()
        self.__problema.writeInFile(c)
        return c

    # def crossoverrr(self, b, m):
    #     st = len(self.getPozitiiLibere())
    #     end = b.getFitness()
    #     cateCompletate = st - end
    #     firstPozNecomp = self.getPozitiiLibere()[cateCompletate]
    #
    #     l = firstPozNecomp // self.getDimensiune()
    #     l *= self.getDimensiune()
    #
    #     valFiu = []
    #     valFiu.extend(b.getConfiguratie()[:l])
    #     valFiu.extend(m.getConfiguratie()[l:])
    #     f = Cromozon(valFiu)
    #     return f

    # def replace(self, w, f):
    #     self.__populatie.remove(w)
    #     self.__populatie.append(f)

    # def SeadyState(self):
    #     self.generare()
    #     self.evaluare()
    #     self.sorteazaPopulatie()
    #     print('initial fitness: ' + str(self.bestFitness()))
    #     count = 0
    #     while True:
    #         m = self.selectie()
    #         t = self.selectie()
    #         if m.getFitness() < t.getFitness():
    #             f = self.crossoverrr(m, t)
    #         else:
    #             f = self.crossoverrr(m, t)
    #         self.mutatie1(f, 0.1)
    #         self.evaluareCromozon(f)
    #         w = self.worst()
    #         if f.getFitness() < w.getFitness():
    #             self.replace(w, f)
    #         self.sorteazaPopulatie()
    #         if self.bestFitness() == 0:
    #             break
    #         count += 1
    #         print("ITERATIA " + str(count) + " -> fitness " + str(self.bestFitness()))
    #     plt.plot(list(range(len(self.fit))), self.best, 'ro')
    #     plt.axis([0, len(self.fit), 0, 50])
    #     plt.show()
    #     return self.best()
    #
    # def evaluareCromozon(self, cr):
    #     zero = self.getPozitiiLibere()[:]
    #     while len(zero) > 0:
    #         ind = zero[0]
    #         r = ind // self.getDimensiune()
    #         c = ind % self.getDimensiune()
    #         dimCadran = int(sqrt(self.getDimensiune()))
    #         cadraneJos = int(r / dimCadran)
    #         cadraneDr = int(c / dimCadran)
    #         b = cadraneJos * dimCadran + cadraneDr
    #
    #         row = cr.row(r)
    #         col = cr.column(c)
    #         bl = cr.block(b)
    #         val = cr.getConfiguratie()[ind]
    #
    #         if row.count(val) == 1 and col.count(val) == 1 and bl.count(val) == 1:
    #             zero.remove(ind)
    #             continue
    #         else:
    #             break
    #     cr.setFitness(len(zero))

    # def mutatie(self, off, p):
    #     if numpy.random.rand() < p:
    #         dim = self.getDimensiune()
    #         lin = numpy.random.randint(0, dim)
    #         c1 = numpy.random.randint(0, dim)
    #         c2 = numpy.random.randint(0, dim)
    #         poz1 = lin * dim + c1
    #         poz2 = lin * dim + c2
    #         off.swap(poz1, poz2)
