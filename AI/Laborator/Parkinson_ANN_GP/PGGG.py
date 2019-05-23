import math
import random

from numpy import double

from Cromozonnn import Chromosome

FUNCTION_SET = ["+", "-"]

'''
Normalizarea datelor de train
'''
def normalizareDateTrain(regressionDataTrain):
    list_means = []
    noAttributes = len(regressionDataTrain[0])
    n = len(regressionDataTrain)
    for i in range(noAttributes):
        sum = 0
        for el in regressionDataTrain:
            sum = sum + el[i]
        list_means.append(sum / n)
    list_deviation = []
    for i in range(noAttributes):
        sum = 0
        for el in regressionDataTrain:
            sum = sum + (el[i] - list_means[i]) ** 2
        list_deviation.append(math.sqrt(sum / (n - 1)))
    normalized_dataIn = []
    for i in range(n):
        li = []
        for j in range(len(regressionDataTrain[i])):
            li.append((regressionDataTrain[i][j] - list_means[j]) / list_deviation[j])
        normalized_dataIn.append(li)
    means = list_means
    deviation = list_deviation
    return normalized_dataIn, means, deviation

'''
Normalizarea datelor de test pe baza mediei si deviatiei standard a datelor de train
'''
def normalizeInputTest(dataT, means, deviation):
    dataTest = []
    for data in dataT:
        data2 = []
        for x in data:
            data2.append(x)
        for i in range(len(data2)):
            data2[i] = (data2[i] - means[i]) / deviation[i]
        dataTest.append(data2)
    return dataTest

'''
Crearea populatiei
'''
def init(pop, noGenes, popSize, dimTerminal):
    for i in range(0, popSize):
        indiv = Chromosome(dimTerminal)
        indiv.grow(0)
        pop.append(indiv)
    for i in range(len(pop)):
        print("Pop ",i, ": ", pop[i])

'''
Calcularea fitnesului unui cromozon
'''
def computeFitness(chromo, inData, outData):
    err = 0.0
    for i in range(0, len(inData)):
        crtEval = chromo.eval(inData[i], 0)
        crtErr = abs(crtEval - outData[i]) ** 2
        err += crtErr
        # print(crtEval, ' -- ', outData[i])
    chromo.fitness = err/len(inData)

'''
Clacularea fitnesului final
'''
def computeFitnessResult(chromo, inData, outData):
    err = 0.0
    for i in range(0, len(inData)):
        crtEval = chromo.eval(inData[i], 0)
        crtErr = abs(crtEval - outData[i]) ** 2
        err += crtErr
        # print('Real: ', outData[i], 'Prezis: ', crtEval)
    chromo.fitness = err/len(inData)

'''
Evaluarea fitnesului intregii populatii
'''
def evalPop(pop, trainInput, trainOutput):
    for indiv in pop:
        computeFitness(indiv, trainInput, trainOutput)


'''
Selectia parintilor (turnir binar)
'''
def selection(pop):
    pos1 = random.randrange(len(pop))
    pos2 = random.randrange(len(pop))
    if (pop[pos1].fitness < pop[pos2].fitness):
        return pop[pos1]
    else:
        return pop[pos2]


'''
Selectia - ruleta
'''
def selectionRoulette(pop):
    sectors = [0]
    sum = 0.0
    for chromo in pop:
        sum += chromo.fitness
    for chromo in pop:
        sectors.append(chromo.fitness / sum + sectors[len(sectors) - 1])
    r = random.random()
    i = 1
    while ((i < len(sectors)) and (sectors[i] <= r)):
        i += 1
    return pop[i - 1]

'''
Traversarea arborelui
'''
def traverseazaArbore(repres, pos, TERMINAL_SET):
    if (repres[pos] in TERMINAL_SET):
        return pos + 1
    else:
        pos = traverseazaArbore(repres, pos + 1, TERMINAL_SET)
        pos = traverseazaArbore(repres, pos, TERMINAL_SET)
    return pos

'''
Crossover - taietura
Inlocuirea unui subarbore din mama cu un subarbore din tata
'''
def crossover(M, F, TERMINAL_SET):
    off = Chromosome(len(TERMINAL_SET))
    startM = random.randrange(len(M.representation))
    endM = traverseazaArbore(M.representation, startM, TERMINAL_SET)
    startF = random.randrange(len(F.representation))
    endF = traverseazaArbore(F.representation, startF, TERMINAL_SET)

    for i in range(0, startM):
        off.representation.append(M.representation[i])
    for i in range(startF, endF):
        off.representation.append(F.representation[i])
    for i in range(endM, len(M.representation)):
        off.representation.append(M.representation[i])
    return off

'''
Mutatie - schimbarea continutului unui nod (terminal sau functie)
'''
def mutation(off, TERMINAL_SET):
    pos = random.randrange(len(off.representation))
    if (off.representation[pos] in TERMINAL_SET):
        terminal = random.choice(TERMINAL_SET)
        off.representation[pos] = terminal
    else:
        function = random.choice(FUNCTION_SET)
        off.representation[pos] = function
    return off

'''
Alegerea celui mai bun individ din populatie
'''
def bestSolution(pop):
    best = pop[0]
    for indiv in pop:
        if indiv.fitness < best.fitness:
            best = indiv
    return best


def EA_generational(noGenes, popSize, noGenerations, trainIn, trainOut, TERMINAL_SET):
    pop = []
    init(pop, noGenes, popSize, len(TERMINAL_SET))
    evalPop(pop, trainIn, trainOut)
    for g in range(0, noGenerations):
        popAux = []
        for k in range(0, popSize):
            M = selectionRoulette(pop)
            F = selectionRoulette(pop)

            off = crossover(M, F, TERMINAL_SET)
            off = mutation(off, TERMINAL_SET)
            popAux.append(off)
        pop = popAux.copy()
        evalPop(pop, trainIn, trainOut)
    sol = bestSolution(pop)
    return sol

'''
Steady State
'''
def EA_steadyState(noGenes, popSize, noGenerations, trainIn, trainOut, TERMINAL_SET):
    pop = []
    init(pop, noGenes, popSize, len(TERMINAL_SET))
    evalPop(pop, trainIn, trainOut)
    for g in range(0, noGenerations):
        for k in range(0, popSize):
            M = selectionRoulette(pop)
            F = selectionRoulette(pop)
            off = crossover(M, F, TERMINAL_SET)
            off = mutation(off, TERMINAL_SET)
            computeFitness(off, trainIn, trainOut)
            crtBest = bestSolution(pop)
            print("Best: ", crtBest)
            if (off.fitness < crtBest.fitness):
                crtBest = off
    sol = bestSolution(pop)
    return sol

'''
Ruleare algoritmului de programare genetica
Stabilirea dimensiunii populatiei si a numarului de negeratii
'''
def runEA(inputTrain, outputTrain, inputTest, outputTest, TERMINAL_SET):
    print()
    learntModel = EA_steadyState(2, 20, 10, inputTrain, outputTrain, TERMINAL_SET)
    print("learnt model steady-state: " + str(learntModel))
    print("training quality: ", double(learntModel.fitness))
    computeFitnessResult(learntModel, inputTest, outputTest)
    # print("testing quality: ", double(learntModel.fitness))
    return double(learntModel.fitness)

'''
Entry point
'''
def run(data_train, data_test):
    normalizedTrain, means, deviation = normalizareDateTrain(data_train)

    inputTrain = [data_train[i][0:len(normalizedTrain[0]) - 1] for i in range(len(normalizedTrain))]
    outputTrain = [row[-1] for row in normalizedTrain]

    normalizedTest = normalizeInputTest(data_test, means, deviation)

    inputTest = [normalizedTest[i][0:len(normalizedTest[0]) - 1] for i in range(len(normalizedTest))]
    outputTest = [row[-1] for row in normalizedTest]

    TERMINAL_SET = [i for i in range(len(inputTrain[0]))]
    print('TERMINAL_SET: ', TERMINAL_SET)

    i = 0
    best = 99999999
    while i < 1:
        rez = runEA(inputTrain, outputTrain, inputTest, outputTest, TERMINAL_SET)
        if rez < best:
            best = rez
        i += 1
    print("testing quality: ", best)
