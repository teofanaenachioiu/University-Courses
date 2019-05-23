import numpy as np
from EAUtils import *
import math



class AlgoritmEvolutiv:
    def __init__(self, dataIn, dataOut):
        self.dataIn = dataIn
        self.dataOut = dataOut
        self.means =[]
        self.deviation =[]
        self.normalized_dataIn = self.normalizeDataIn()

    '''
    Normalizarea datelor de train
    '''
    def normalizeDataIn(self):
        list_means = []
        noAttributes = len(self.dataIn[0])
        n = len(self.dataOut)
        for i in range(noAttributes):
            sum = 0
            for el in self.dataIn:
                sum = sum + el[i]
            list_means.append(sum / n)
        list_deviation = []
        for i in range(noAttributes):
            sum = 0
            for el in self.dataIn:
                sum = sum + (el[i] - list_means[i]) ** 2
            list_deviation.append(math.sqrt(sum / (n - 1)))
        normalized_dataIn = []
        for i in range(n):
            li = []
            for j in range(len(self.dataIn[i])):
                li.append((self.dataIn[i][j] - list_means[j]) / list_deviation[j])
            normalized_dataIn.append(li)
        self.means=list_means
        self.deviation =list_deviation
        return normalized_dataIn

    '''
    Normalizare data de test
    '''
    def normalize_oneData(self, data):
        data2 = []
        for x in data:
            data2.append(x)
        for i in range(len(data2)):
            data2[i] = (data2[i] - self.means[i]) / self.deviation[i]
        return data2

    '''
    Construirea matricei de input 
    '''
    def constructInputMatrix(self):
        input = self.normalized_dataIn
        Matrix = np.array(input)
        return Matrix

    '''
    Construirea matricei de output
    '''
    def constructOutMatrix(self):
        l = []
        for el in self.dataOut:
            l.append([el])
        l = np.array(l)
        return l

    '''
    Functia sigmoid
    '''
    def sigmoidFunction(self, z):
        try:
            return 1.0 / (1.0 + math.exp(0.0 - z))
        except OverflowError:
            return 0

    '''
    Translatare matrice linie - matrice coloana
    '''
    def toColumn(self, coef):
        l = []
        for x in coef:
            l.append([x])
        return np.array(l)

    '''
    Rularea algoritmului evolutiv
    '''
    def run(self):
        nrCoeficienti = len(self.dataIn[0])


        pop_size = (dimPopulatie, nrCoeficienti)
        populatieCromozomi = np.random.uniform(0, 1, pop_size)

        applySigmoid = np.vectorize(self.sigmoidFunction)

        y_reals = self.constructOutMatrix()

        for generation in range(nrGeneratii):
            y_guesses_pop = []
            for cromozon in list(populatieCromozomi):
                y_guesses = applySigmoid(self.constructInputMatrix().dot(self.toColumn(cromozon)))
                y_guesses_pop.append(y_guesses)

            fitnessPopulatie = getFitnessPopulation(y_guesses_pop, y_reals)
            parinti = selectParents(populatieCromozomi, fitnessPopulatie)
            fii = crossover(parinti, dimPopulatie)

            fii = mutation(fii)

            populatieCromozomi[0:len(parinti), :] = parinti
            populatieCromozomi[len(parinti):, :] = fii

        y_guesses_pop = []
        for cromozon in list(populatieCromozomi):
            y_guesses = applySigmoid(self.constructInputMatrix().dot(self.toColumn(cromozon)))
            y_guesses_pop.append(y_guesses)
        fitnessPopulatie = getFitnessPopulation(y_guesses_pop, y_reals)

        best_fitness_index = np.where(fitnessPopulatie == np.min(fitnessPopulatie))
        return populatieCromozomi[best_fitness_index, :][0][0]

    '''
    Functia de predictie
    '''
    def prediction(self, example, coef):
        s = 0.0
        for i in range(0, len(example)):
            s += coef[i] * example[i]
        return s

    '''
    Entry point Algoritm Evolutiv
    '''
    def clasificare(self, input):
        coef = self.run()
        input = self.normalize_oneData(input)
        return self.sigmoidFunction(self.prediction(input, coef))










dimPopulatie = 20
nrGeneratii = 20