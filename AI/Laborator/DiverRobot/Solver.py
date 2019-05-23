from EvolutionaryAlgorithm import AlgoritmEvolutiv
from LogisticRegression import *


class Solver:
    def __init__(self, dataIn, dataOut, tags):
        self.dataIn = dataIn
        self.tags = tags
        self.classifiedDataOut = self.clasificaDataOut(dataOut)

    '''
    Crearea matricei tagurilor
    '''
    def clasificaDataOut(self, dataOut):
        classifiedDataOut = []
        for tag in self.tags:
            outdata = []
            for data in dataOut:
                if data[0] == tag:
                    outdata.append(1)
                else:
                    outdata.append(0)
            classifiedDataOut.append(outdata)
        return classifiedDataOut

    '''
    Rularea regresiei logistice
    '''
    def runLogisticRegression(self, input):
        max = -1
        fitTag = 0
        for index in range(len(self.classifiedDataOut)):
            solver = RegresieLogistica(self.dataIn, self.classifiedDataOut[index])
            prob = solver.clasificare(input)
            if (prob > max):
                max = prob
                fitTag = self.tags[index]
        return [fitTag, max]

    '''
    Rularea algoritmului evolutiv
    '''
    def runEvolutionaryAlgorithm(self, input):
        max = -1
        fitTag = 0
        for index in range(len(self.classifiedDataOut)):
            solver = AlgoritmEvolutiv(self.dataIn, self.classifiedDataOut[index])
            prob = solver.clasificare(input)
            if (prob > max):
                max = prob
                fitTag = self.tags[index]
        return [fitTag, max]
