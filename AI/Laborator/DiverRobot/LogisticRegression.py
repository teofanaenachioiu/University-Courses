import numpy as np
import math


class RegresieLogistica:
    def __init__(self, dataIn, dataOut):
        self.dataIn = dataIn
        self.dataOut = dataOut
        self.means = []
        self.deviation = []
        self.normalized_dataIn = self.normalizareDateTrain()

    '''
    Normalizarea datelor de train
    '''
    def normalizareDateTrain(self):
        list_means = []
        noAttributes = len(self.dataIn[0])
        n = len(self.dataIn)
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
        self.means = list_means
        self.deviation = list_deviation
        return normalized_dataIn

    '''
    Normalizarea unui rand din inputul de test
    '''
    def normalizeOneRowInputTest(self, data):
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
        return np.array(input)

    '''
    Construirea matricei de output
    '''
    def  constructOutMatrix(self):
        l = []
        for el in self.dataOut:
            l.append([el])
        return np.array(l)

    '''
    Construirea matricei coeficientilor
    '''
    def constructCoeffMatrix( self):
        m = len(self.dataIn[0])
        coef = np.zeros((m, 1))
        return np.array(coef)

    '''
    Functia sigmoid
    '''
    def sigmoidFunction(self, z):
        try:
            return 1.0 / (1.0 + math.exp(0.0 - z))
        except OverflowError:
            return 0

    '''
    Aplicare gradient si determinarea coeficientilor
    '''
    def gradientDescent(self, x, y, theta, alpha, num_iters):
        applySigmoid = np.vectorize(self.sigmoidFunction)
        for i in range(num_iters):
            y_hat = applySigmoid(x.dot(theta))
            theta = theta - alpha * x.T.dot(y_hat - y)
        return theta

    '''
    Functia de predictie
    '''
    def prediction(self, example, coef):
        s = 0.0
        for i in range(0, len(example)):
            s += coef[i] * example[i]
        return s

    '''
    Antrenarea algoritmului
    '''
    def train(self):
        maxiter = 100
        input = self.constructInputMatrix()
        out = self.constructOutMatrix()
        coef = self.constructCoeffMatrix()
        learning_rate = 0.001
        return self.gradientDescent(input, out, coef, learning_rate, maxiter)

    '''
    Entry point Regresie Logistica
    '''
    def clasificare(self, input):
        coef = self.train()
        # print ("Coeficienti: " ,coef)
        input = self.normalizeOneRowInputTest(input)
        return self.sigmoidFunction(self.prediction(input, coef))
