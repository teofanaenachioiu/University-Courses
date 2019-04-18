import random
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

    # pozitiile libere din tabela intiala
    def getPozitiiLibere(self):
        return self.__problema.empty()

    # lista de cromozoni
    def getPopulatie(self):
        return self.__populatie

    # dimensiunea tabelei
    def getDimensiune(self):
        return self.__problema.getDimensiuneTabla()

    # returneaza tabela initiala
    def getInitialSudoku(self):
        return self.__problema.getConfiguratieInitiala()

    # returneaza dimensiunea populatiei
    def getNrPopulatie(self):
        return self.__problema.getNrPopulatie()

    # returneaza numarul de generarii (numarul de iteratii)
    def getNrGeneratii(self):
        return self.__problema.getNrGeneratii()

    # adauga un cromozon in populatie
    def adaugaCromozon(self, cr):
        self.__populatie.append(cr)

    # sorteaza cromozonii din populatie in functie de fitness
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

    def generare1(self):
        lista = [9 for x in range(9)]
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
        print(values)
        gen = 0
        while gen < self.getNrPopulatie():
            val = self.getInitialSudoku()[:]
            numere = values[:]
            random.shuffle(numere)
            empty = self.getPozitiiLibere()[:]
            while len(empty) != 0:
                poz = empty.pop()
                val[poz] = numere.pop()
            cromozon = Cromozon(val)
            self.adaugaCromozon(cromozon)
            gen += 1

    # se evalueaza intreaga populatie
    def evaluare(self):
        for cr in self.__populatie:
            cr.eval1()

    # se determina cel mai bun individ din populatie
    def best(self):
        self.sorteazaPopulatie()
        return self.__populatie[0]

    # cel mai bun fitness
    def bestFitness(self):
        self.sorteazaPopulatie()
        return self.__populatie[0].getFitness()

    # functia de selectie
    def selectie(self):
        # numpy.random.shuffle(self.__populatie)
        return self.__populatie[randrange(self.getNrPopulatie())]

    # functia de incrucisare
    def crossover(self, m, t):
        f = []
        dim = self.getDimensiune()
        for i in range(dim):
            if numpy.random.rand() < 0.5:
                f.extend(m.row(i)[:])
            else:
                f.extend(t.row(i)[:])
        c = Cromozon(f)
        return c

    def ruleta(self, dim):
        newPop = []
        for i in range(dim):
            sumaTotala = 0
            for cr in self.__populatie:
                sumaTotala += cr.getFitness()

            suma = 0
            procente = []
            for cr in self.__populatie:
                proc = float(sumaTotala) / cr.getFitness()
                suma += proc
                procente.append(proc)

            sumaaaa = 0
            for index in range(len(procente)):
                procente[index] = sumaaaa + procente[index] / suma
                sumaaaa = procente[index]

            p = numpy.random.rand()
            for index in range(len(procente)):
                if p < procente[index]:
                    newPop.append(self.__populatie.pop(index))
                    break

        self.__populatie = newPop

    # selectia populatiei
    def crossoverPopulatie(self, p):
        dim = self.getNrPopulatie()
        populatieNoua = []
        for index in range(dim):
            m = self.selectie()
            t = self.selectie()
            f = self.crossover(m, t)

            self.mutatie(f, p)
            f.eval1()
            populatieNoua.append(f)
        self.__populatie.extend(populatieNoua)

        self.ruleta(dim)

        # self.sorteazaPopulatie()
        # cat = int(0 * dim)
        # popVeche = self.__populatie[cat:]
        # random.shuffle(popVeche)
        # self.__populatie = self.__populatie[:cat]
        # self.__populatie.extend(popVeche[:int(1*dim)])

    # functia de mutatie a unui cromozon
    def mutatie1(self, off, p):
        if numpy.random.rand() <= p:
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

    def mutatie(self, off, p):
        if numpy.random.rand() <= p:
            emp = self.__problema.empty()
            c1 = secrets.choice(emp)
            emp.remove(c1)
            c2 = secrets.choice(emp)
            off.swap(c1, c2)

    def printPopulatie(self):
        for el in self.__populatie:
            print(el)

    def algoritmEvolutiv(self):
        self.generare1()
        self.evaluare()
        self.sorteazaPopulatie()
        print('initial fitness: ' + str(self.bestFitness()))
        count = 0
        while True:
            self.crossoverPopulatie(0.7)
            count += 1
            actualBest = self.bestFitness()
            self.fit.append(actualBest)
            print('ITERATION ' + str(count) + ' -> best fitness: ' + str(self.bestFitness()))

            if actualBest == 0:
                break

        print('best fitness: ' + str(self.bestFitness()))
        self.fit.append(self.bestFitness())
        plt.plot(list(range(len(self.fit))), self.fit, 'ro')
        plt.axis([0, len(self.fit), 0, 50])
        plt.show()

        c = self.best()
        self.__problema.writeInFile(c)
        return c
