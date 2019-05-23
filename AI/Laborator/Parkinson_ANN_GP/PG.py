import math
import random

from numpy import double

from Cromozon import Chromosome

FUNCTION_SET = ["+", "-"]


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
Normalizarea unui rand din inputul de test
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


def init(pop, noGenes, popSize, dimTerminal):
    for i in range(0, popSize):
        indiv = Chromosome(dimTerminal)
        indiv.grow()
        pop.append(indiv)


def computeFitness(chromo, inData, outData):
    err = 0.0
    for i in range(0, len(inData)):
        crtEval = chromo.eval(inData[i], 0)
        crtErr = abs(crtEval - outData[i]) ** 2
        err += crtErr
        # print(crtEval, ' -- ', outData[i])
    chromo.fitness = err/len(inData)


def computeFitnessResult(chromo, inData, outData):
    err = 0.0
    for i in range(0, len(inData)):
        crtEval = chromo.eval(inData[i], 0)
        crtErr = abs(crtEval - outData[i]) ** 2
        err += crtErr
        print('Real: ', outData[i], 'Prezis: ', crtEval)
    chromo.fitness = err/len(inData)


def evalPop(pop, trainInput, trainOutput):
    for indiv in pop:
        indiv.eval(trainInput, trainOutput)


# binary tournament selection
def selection(pop):
    pos1 = random.randrange(len(pop))
    pos2 = random.randrange(len(pop))
    if (pop[pos1].fitness < pop[pos2].fitness):
        return pop[pos1]
    else:
        return pop[pos2]


# roulette selection
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


def traverse(repres, pos, TERMINAL_SET):
    if (repres[pos] in TERMINAL_SET):
        return pos + 1
    else:
        pos = traverse(repres, pos + 1, TERMINAL_SET)
        pos = traverse(repres, pos, TERMINAL_SET)
    return pos


# cutting-point XO
# replace a sub-tree from M with a sub-tree from F
def crossover(M, F, TERMINAL_SET):
    child = Chromosome(len(TERMINAL_SET))
    child.representation.append(M.representation[0])
    child.representation.append(M.representation[1])
    child.representation.append(F.representation[2])
    return child


# change the content of a note (function -> function, terminal -> terminal
def mutation(off):
    pos = random.randrange(len(off.representation))
    if pos == 0:
        off.representation[0] = random.choice(FUNCTION_SET)
    if pos == 1:
        off.representation[1][0] = random.choice(FUNCTION_SET)
    if pos == 2:
        off.representation[2][0] = random.choice(FUNCTION_SET)

    return off


def bestSolution(pop):
    best = pop[0]
    for indiv in pop:
        if indiv.fitness < best.fitness:
            best = indiv
    return best


def EA_steadyState(noGenes, popSize, noGenerations, trainIn, trainOut, TERMINAL_SET):
    pop = []
    init(pop, noGenes, popSize, len(TERMINAL_SET))
    evalPop(pop, trainIn, trainOut)
    for g in range(0, noGenerations):
        for k in range(0, popSize):
            # M = selection(pop)
            # F = selection(pop)
            M = selectionRoulette(pop)
            F = selectionRoulette(pop)
            off = crossover(M, F, TERMINAL_SET)
            off = mutation(off)
            off.eval(trainIn, trainOut)
            crtBest = bestSolution(pop)
            if (off.fitness < crtBest.fitness):
                crtBest = off
    sol = bestSolution(pop)
    return sol


def runEA(inputTrain, outputTrain, inputTest, outputTest, TERMINAL_SET):
    print()
    learntModel = EA_steadyState(len(inputTrain[0]), 10, 100, inputTrain, outputTrain, TERMINAL_SET)
    print("learnt model: " + str(learntModel))
    print("training quality: ", double(learntModel.fitness))
    learntModel.eval(inputTest, outputTest)
    print("testing quality (partial): ", double(learntModel.fitness))
    return double(learntModel.fitness)


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
