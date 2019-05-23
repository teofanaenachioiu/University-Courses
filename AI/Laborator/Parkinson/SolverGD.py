import statistics

import numpy as np



def normalizareDate(x):
    inputNormalizat = []
    ma_list = []
    devStandardList = []
    for index in range(len(x)):
        inputNormalizat.append([])
    for data in range(len(x[0])):
        ma = 0
        for index in range(len(x)):
            ma += x[index][data]
        ma = ma / len(x)
        sumPatratDif = 0
        for index in range(len(x)):
            sumPatratDif += ((x[index][data] - ma) ** 2)
        sumPatratDif = sumPatratDif / (len(x) - 1)
        devStandard = np.sqrt(sumPatratDif)
        # col = x[:,data]
        # devStandard = statistics.stdev(col)
        for index in range(len(x)):
            valNorm = (x[index][data] - ma) / devStandard
            inputNormalizat[index].append(valNorm)
        ma_list.append(ma)
        devStandardList.append(devStandard)

    return inputNormalizat, ma_list, devStandardList


def normaliseTest(x, ma, devStandard):
    xNorm = []
    for index in range(len(x)):
        xNorm.append([])
    for data in range(len(x[0])):
        for index in range(len(x)):
            valNorm = (x[index][data] - ma[data]) / devStandard[data]
            xNorm[index].append(valNorm)

    return xNorm


class Gradient:
    def __init__(self, problema):
        self.__problema = problema
        self.maList = []
        self.devList = []

    # Normalizarea datelor folosind deviatia standard
    def normalizare(self, matrix):
        nrFeatures = len(matrix[0])
        nrExemple = len(matrix)
        for j in range(nrFeatures):
            col = matrix[:, j]
            media = statistics.mean(col)
            dvStd = statistics.stdev(col)
            for i in range(nrExemple):
                matrix[i][j] = (matrix[i][j] - media) / dvStd

    # Se construieste Matricea de input
    def constructInputMatrix(self):
        input = self.__problema.getXTrain()
        input, self.maList, self.devList = normalizareDate(input)
        input = np.array(input)
        X0 = np.ones((self.__problema.getNrExempleTrain(), 1))
        Matrix = np.hstack((X0, input))
        return Matrix

    # Se construieste matricea de output
    def constructOutMatrix(self):
        out = self.__problema.getYTrain()
        Matrix = np.array(out)
        return Matrix

    # Se construeste matricea coeficientilor
    def constructCoeffMatrix(self):
        m = len(self.__problema.getXTrain()[0]) + 1
        l = np.zeros((m, 1))
        l = np.array(l)
        return l

    # Gradient function
    # Se calculeaza matricea coeficientilor cu formula beta = beta - X_transp * learning_rate * error
    def gradientDescent(self, x, y, coef, lr, num_iters):
        for i in range(num_iters):
            for j in range(len(x)):
                y_hat = x[j].dot(coef)
                error = y_hat - y[j]
                transp = []
                for el in x[j]:
                    transp.append([el])
                transp = np.array(transp)
                coef = coef - transp * lr * error
        return coef

    # Se calculeaza matricea coeficientilor
    def run(self, maxiter, learning_rate):
        # maxiter = 10000
        input = self.constructInputMatrix()
        out = self.constructOutMatrix()
        m = self.constructCoeffMatrix()
        # learning_rate = 0.005
        return self.gradientDescent(input, out, m, learning_rate, maxiter)

    # Functia de predictie
    # Se inmulteste matricea variabileleor cu matricea coeficientilor
    def model(self, maxiter, learning_rate):
        m = self.run(maxiter, learning_rate)

        input = self.__problema.getXTest()
        input = normaliseTest(input, self.maList, self.devList)
        new = []
        for el in input:
            new.append([1] + el)
        Matrix = np.array(new)
        res = Matrix.dot(m)
        return res

    # Functia calculeaza deviatia standard cu formula sum_1_n (dif partrat din y_real y_guess)/n
    def devStd(self, maxiter, learning_rate):
        y_guess = self.model(maxiter, learning_rate)
        y_real = self.__problema.getYTest()
        eroare = 0

        for i in range(len(y_real)):
            print("Real: " + str(y_real[i]) + " -- Guess: " + str(y_guess[i]))
            val = y_real[i] - y_guess[i]
            eroare = eroare + val * val
        self.__problema.writeInFile(eroare[0] / len(y_real))
        return eroare[0] / len(y_real)
