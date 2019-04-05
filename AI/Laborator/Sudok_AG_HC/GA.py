from random import shuffle, randint, randrange
import copy
import numpy as np

#citirea din fisier
def read(nume):
    initial = []
    n = 0
    filename = open(nume, 'r')
    ok = 0
    initial.clear()
    for linie in filename:
        if ok == 0:
            n = int(linie)
            ok = 1
        else:
            rand = linie.strip().split(",")
            rand = [int(x) for x in rand]
            initial.append(rand)
    n = len(initial)
    filename.close()
    return n,initial

# scrie in fisier
def scriere(st, f):
    filename = open(f,'w')
    filename.write(str(len(st)) + "\n")
    for linie in st:
        ok = 0
        for x in linie:
            if ok==0:
                filename.write(str(x))
                ok=1
            else:
                filename.write(","+str(x))
        filename.write("\n")
    filename.close()


# Returneaza un sudoku gol de n*n
def empty_grid(size):

    return [
        [0 for j in range(size)] for i in range(size)
    ]

# creeaza populatia initiala
def getPopulatieInitiala(population, problem_grid):
    candidates = []
    for k in range(population):
        candidate = empty_grid(len(problem_grid))
        for i in range(len(problem_grid)):
            # pt fiecare linie luam un set de n inturi si le punem
            shuffled_sub_grid = [n for n in range(1, len(problem_grid) + 1)]
            shuffle(shuffled_sub_grid)
            # populam elementele ce sunt deja in gridul initial
            for j in range(len(problem_grid)):
                if problem_grid[i][j] != 0:
                    candidate[i][j] = problem_grid[i][j]
                    shuffled_sub_grid.remove(problem_grid[i][j])
            # punem elementele ramase in lista celor n int-uri
            for j in range(len(problem_grid)):
                if candidate[i][j] == 0:
                    candidate[i][j] = shuffled_sub_grid.pop()
        candidates.append(candidate)
    return candidates

def selectie(populatie, fitnessAll):
    elem1 = randrange(len(populatie))
    elem2 = randrange(len(populatie))
    if fitnessAll[elem1] > fitnessAll[elem2]:
        return populatie[elem2]
    else:
        return populatie[elem1]

# returneaza pozitia pe care avem cel mai bun fitness
def getBest(fitness):
    best = 0
    for i in range(len(fitness)):
        if fitness[i] < fitness[best]:
            best = i
    return best

# returneaza pozitia pe care avem cel mai slab fitness
def getWorst(fitness):
    worst = 0
    for i in range(len(fitness)):
        if fitness[i] > fitness[worst]:
            worst = i
    return worst

# returneaza numarul de duplicate pe coloana col
def getDuplicatesColumn(sudoku, col):
    lista = [sudoku[i][col] for i in range(len(sudoku))]
    dup = [0]*(len(sudoku) + 1)
    for x in lista:
        dup[x] += 1
    sum = 0
    for x in dup:
        if x >= 1:
           sum += 1
    return len(sudoku)-sum

# calculeaza nr de duplicate pe un subgrid
def getDuplicatesSquare(sudoku, square):

    n = len(sudoku)
    size = int(np.sqrt(n))
    subrow = (square // size) * size
    subcol = (square % size) * size
    v = [0] * n
    for j in range(n):
        subrj = j // size
        subcj = j % size
        v[j] = sudoku[subrow + subrj][subcol + subcj]

    dup = [0] * (n + 1)
    for x in v:
        dup[x] += 1

    sum = 0
    for x in dup:
        if x >= 1:
            sum += 1
    return n-sum

#nr de duplicate pe care le avem pe linie coloana si patratel
def fitness(sudoku):

    fit = 0
    for i in range(len(sudoku)):
        fit += getDuplicatesColumn(sudoku, i)
        fit += getDuplicatesSquare(sudoku, i)
    return fit

#evaluam fitnessul pentru toata populatia
def evalPopulation(populatie):
    n = len(populatie)
    fit = [0]*n
    for i in range(n):
        fit[i] = fitness(populatie[i])
    return fit

#luam prima jumatate de la mama si cealalata de la tata
def crossOver(mama, tata):

    n = len(mama)
    m = n//2
    copil = empty_grid(n)
    for i in range(m+1):
        for j in range(n):
            copil[i][j] = mama[i][j]
    for i in range(m+1, n):
        for j in range(n):
            copil[i][j] = tata[i][j]
    return copil

#luam un numar random din range ul 0 - size
def getRandInt(size):
    return randrange(size)

# daca o pozitie nu e si in matricea initiala
def mutatie(copil, initial):
    size = len(copil)
    row = getRandInt(size)
    c1 = getRandInt(size)
    c2 = getRandInt(size)
    nr = 0
    while initial[row][c1] != 0 or initial[row][c2] != 0 or getDuplicatesColumn(copil, c1) == 0 or getDuplicatesColumn(copil, c2) == 0:
        row = getRandInt(size)
        c1 = getRandInt(size)
        c2 = getRandInt(size)
        if nr == 1000:
            return None
        nr += 1
    copil[row][c1], copil[row][c2] = copil[row][c2], copil[row][c1]


def solve(sudoku, size, maxPopulation, nrGen):
    copySudoku = copy.deepcopy(sudoku)
    populatie = getPopulatieInitiala(maxPopulation,copySudoku)
    #toate fitnesurile
    fitnessAll = evalPopulation(populatie)
    #int cu pozitia celui mai bun
    maxGlobal = getBest(fitnessAll)
    for i in range(nrGen):
        #selectie
        mama = selectie(populatie, fitnessAll)
        tata = selectie(populatie, fitnessAll)
        #imperechere
        copil = crossOver(mama, tata)
        #modificam copilul
        mutatie(copil, copySudoku)
        #ii calculam fitnessul si il inlocuim cu cel mai rau din toate
        fit = fitness(copil)

        poz = getWorst(fitnessAll)
        fitnessAll[poz] = fit
        populatie[poz] = copil
        #refacem fitnessul global
        maxGlobal = getBest(fitnessAll)
        if i % 1000 == 0:
            print('Iteratia '+ str(i)+ ' -> fitness '+ str(fitnessAll[maxGlobal]))
        if fitnessAll[maxGlobal] == 0:
            break
    return populatie[maxGlobal], fitnessAll[maxGlobal]

def main():
    n, initial = read("medium_s.txt")
    populatie_maxima = 3000
    generatii = 300000

    solutie, fitness = solve(initial, n, populatie_maxima, generatii)
    print(fitness)
    scriere(solutie, "output.txt")


main()