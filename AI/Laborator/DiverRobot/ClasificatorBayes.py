import math
import numpy


class ClasificatorBayes:
    def __init__(self, inputTrain, inputTest):
        self.inputTrain = inputTrain
        self.inputTest = inputTest

    '''
    Creez un dictionar din datele de train cu cheia valoarea de OUTPUT
    '''
    def getClassesFromTrain(self):
        dictionar = {}
        for i in range(len(self.inputTrain)):
            inputLine = self.inputTrain[i]
            output = self.inputTrain[i][-1]
            if (output not in dictionar):
                dictionar[output] = [inputLine]
            else:
                dictionar[output].append(inputLine)
        return dictionar

    '''
    Calculez probabilitatea ca o valoare sa apara in lista de outputuri
    '''
    def getProbabilitate(self, value):
        probabilitate = 0
        for lista in self.inputTrain:
            if value == lista[-1]:
                probabilitate = probabilitate + 1
        return (probabilitate / len(self.inputTrain))

    '''
    Creez o lista cu liste ce contin valorile pt fiecare features 
    '''
    def groupByFeature(self, listOfLists):
        linesInput = []
        nrFatures = len(listOfLists[0]) - 1
        for index in range(nrFatures):
            linieFeatures = []
            for lista in listOfLists:
                linieFeatures.append(lista[index])
            linesInput.append(linieFeatures[:])
        return linesInput

    '''
    Determin deviatia media si deviatia standard pentru fiecare clasa
    Grupez dupa features datele corespunzatoare fiecarei clase
    Cu datele grupate, calculez valoarea medie si deviatia standard
    '''
    def getMeanStddevForEachClass(self):
        dictionar = self.getClassesFromTrain()
        meanAndStddev = {}
        for item in dictionar.items():
            clasa, listOfLists = item
            lista = self.groupByFeature(listOfLists)
            meanAndStddev[clasa] = [[numpy.mean(val), numpy.std(val)] for val in lista]
        return meanAndStddev

    '''
    Functia de densitate normala
    '''
    def getDensitateaNormala(self, x, mean, stdev):
        if stdev == 0:
            return 0
        exp = math.exp(-(math.pow(x - mean, 2) / (2 * math.pow(stdev, 2))))
        return (1 / (math.sqrt(2 * math.pi) * stdev)) * exp

    '''
       Functia sigmoid
       '''

    def sigmoidFunction(self, z):
        try:
            return 1.0 / (1.0 + math.exp(0.0 - z))
        except OverflowError:
            return 0

    '''
    Calculez probabilitatea pentru fiecare clasa (BAYES)
    '''
    def getClassProbabilities(self, dictMeanStddev, inputLine):
        probabilitati = {}
        for item in dictMeanStddev.items():
            catg, meanStddev =item
            probabilitati[catg] = self.getProbabilitate(catg)
            for i in range(len(meanStddev)):
                mean, stdev = meanStddev[i]
                value = inputLine[i]
                probabilitati[catg] = probabilitati[catg] * self.getDensitateaNormala(value, mean, stdev)
        return probabilitati

    '''
    Determin categoria in care se afla fiecare linie de input din test
    '''
    def clasificationOne(self, dictMeanStddev, input):
        probabilities = self.getClassProbabilities(dictMeanStddev, input)
        bestClass, bestProb = None, -1
        for classValue, prob in probabilities.items():
            if bestClass is None or prob > bestProb:
                bestProb = prob
                bestClass = classValue
        return bestClass, bestProb

    '''
    Clasificare date
    '''
    def clasificare(self):
        dictMeanStddev = self.getMeanStddevForEachClass()
        predictions = []
        for i in range(len(self.inputTest)):
            result, prob = self.clasificationOne(dictMeanStddev, self.inputTest[i])
            predictions.append([result,prob])
        return predictions

    '''
    Acurateatea rezultatului
    '''
    def getAcuratete(self, predictions):
        nr = 0
        for i in range(len(self.inputTest)):
            print("Prezis", predictions[i][0])
            print("Real:", self.inputTest[i][-1])
            if self.inputTest[i][-1] == predictions[i][0]:
                nr += 1
        return (nr / float(len(self.inputTest)))

    '''
    Start clasificator
    '''
    def run(self):
        predictions = self.clasificare()
        return self.getAcuratete(predictions)
