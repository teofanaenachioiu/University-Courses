import numpy

'''
Fitness-ul unui cromozon este dat de numarul de matchuiri
'''


def getFitnessPopulation(y_hat, y):
    fitness = numpy.sum(y_hat * y, axis=1)
    return fitness

'''
Selectez cei mai buni parinti (fitnesul cel mai mare)
'''
def selectParents(pop, fitness):
    parents = numpy.empty((2, len(pop[0])))
    for parent_num in range(2):
        maxFitnessIndex = numpy.where(fitness == numpy.max(fitness))
        maxFitnessIndex = maxFitnessIndex[0][0]
        parents[parent_num, :] = pop[maxFitnessIndex, :]
        fitness[maxFitnessIndex] = -9999999999
    return parents

'''
Incrucisare intre toata populatia
'''
def crossover(parents, dimPopulatie):
    nrCoeficienti = len(parents[0])
    offspring_size = (dimPopulatie - 2, nrCoeficienti)
    fii = numpy.empty(offspring_size)
    crossoverPoint = int(nrCoeficienti / 2)
    for index in range(dimPopulatie-2):
        parentIndex1 = index % len(parents)
        parentIndex2 = (index + 1) % len(parents)
        fii[index, 0:crossoverPoint] = parents[parentIndex1, 0:crossoverPoint]
        fii[index, crossoverPoint:] = parents[parentIndex2, crossoverPoint:]
    return fii


def mutation(fii):
    for idx in range(len(fii)):
        random_value = numpy.random.uniform(-1.0, 1.0, 1)
        fii[idx, 0] = fii[idx, 0] + random_value

    return fii
