import numpy as np
from numpy.linalg import inv


class LeastSquareMethod:
    def __init__(self, problema):
        self.__problema = problema

    # Se construieste Matricea de input X=(1 x1 x2 x3 ...)
    def constructInputMatrix(self):
        X = self.__problema.getXTrain()
        X = np.array(X)
        X0 = np.ones((self.__problema.getNrExempleTrain(), 1))
        Matrix = np.hstack((X0, X))
        return Matrix

    # Se construieste matricea de output
    def constructOutMatrix(self):
        out = self.__problema.getYTrain()
        out = np.array(out)
        Matrix = np.array(out)
        return Matrix

    # Se calculeaza matricea coeficientilor cu formula beta = (x.trans * x).inv * x.trans *y
    def getWeights(self):
        X = self.constructInputMatrix()
        X_trans = X.T
        res = X_trans.dot(X)
        res_inv = inv(res)
        result = res_inv.dot(X_trans)
        b = result.dot(self.constructOutMatrix())
        return b

    # Functia de predictie
    # Se inmulteste matricea variabileleor cu matricea coeficientilor
    def model(self):
        input = self.__problema.getXTest()
        new = []
        for el in input:
            new.append([1] + el)
        Matrix = np.array(new)
        res = Matrix.dot(self.getWeights())
        return res

    # Functia calculeaza deviatia standard cu formula sum_1_n (dif partrat din y_real y_guess)/n
    def devStd(self):
        y_guess = self.model()
        y_real = self.__problema.getYTest()
        eroare = 0
        dim = len(y_real)
        for i in range(dim):
            val = y_real[i] - y_guess[i]
            print("Real: " + str(y_real[i]) + " -- Guess: " + str(y_guess[i]))
            eroare = eroare + val * val
        self.__problema.writeInFile(eroare[0]/dim)
        return eroare[0]/dim














 # def dataset_minmax(self, dataset):
    #     minmax = list()
    #     for i in range(len(dataset[0])):
    #         col_values = [row[i] for row in dataset]
    #         value_min = min(col_values)
    #         value_max = max(col_values)
    #         minmax.append([value_min, value_max])
    #     return minmax
    #
    # def normalize_dataset(self, dataset, minmax):
    #     for row in dataset:
    #         for i in range(len(row)):
    #             row[i] = (row[i] - minmax[i][0]) / (minmax[i][1] - minmax[i][0])
    #
    # def denormalize_dataset(self, dataset, minmax):
    #     for row in dataset:
    #         for i in range(len(row)):
    #             row[i] = minmax[i][0] + (minmax[i][1] - minmax[i][0]) * row[i]
    #
    # def normalizare(self, matrix):
    #     nrFeatures = len(matrix[0])
    #     nrExemple = len(matrix)
    #     for j in range(nrFeatures):
    #         col = matrix[:, j]
    #         media = statistics.mean(col)
    #         dvStd = statistics.stdev(col)
    #         for i in range(nrExemple):
    #             matrix[i][j] = (matrix[i][j] - media) / dvStd
    #
    # def denormalizare(self, matrix):
    #     nrFeatures = len(matrix[0])
    #     nrExemple = len(matrix)
    #     for j in range(nrFeatures):
    #         col = matrix[:, j]
    #         media = statistics.mean(col)
    #         dvStd = statistics.stdev(col)
    #         for i in range(nrExemple):
    #             matrix[i][j] = (matrix[i][j] - media) / dvStd
