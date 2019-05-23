class Problema:
    def __init__(self, inputTrain,inputTest, output):
        self.__inputTrain = inputTrain
        self.__nrVarinateIndepTrain = 0
        self.__nrExempleTrain = 0
        self.__xTrain = []
        self.__yTrain = []

        self.__nrVarinateIndepTest = 0
        self.__nrExempleTest = 0
        self.__xTest = []
        self.__yTest = []
        self.__inputTest = inputTest

        self.__output = output
        self.readFromFileTrain()
        self.readFromFileTest()

    def getOutput(self):
        return self.__output

    def getNrVarianteIndepTest(self):
        return self.__nrVarinateIndepTest

    def getNrVarianteIndepTrain(self):
        return self.__nrVarinateIndepTrain

    def getNrExempleTest(self):
        return self.__nrExempleTest

    def getNrExempleTrain(self):
        return self.__nrExempleTrain

    def getXTest(self):
        return self.__xTest

    def getXTrain(self):
        return self.__xTrain

    def getYTest(self):
        return self.__yTest

    def getYTrain(self):
        return self.__yTrain

    def readFromFileTest(self):
        fin = open(self.__inputTest, 'r')
        self.__nrVarinateIndepTest = int(fin.readline())
        self.__nrVarinateIndepTest -=1
        self.__nrExempleTest = int(fin.readline())
        for line in fin.readlines():
            linie = [float(x) for x in line.split(',')]
            self.__yTest.append([linie[-1]])
            self.__xTest.append(linie[0:len(linie)-1])

    def readFromFileTrain(self):
        fin = open(self.__inputTrain, 'r')
        self.__nrVarinateIndepTrain = int(fin.readline())
        self.__nrVarinateIndepTrain -= 1
        self.__nrExempleTrain = int(fin.readline())
        for line in fin.readlines():
            linie = [float(x) for x in line.split(',')]
            self.__yTrain.append([linie[-1]])
            self.__xTrain.append(linie[0:len(linie) - 1])

    def writeInFile(self, err):
        f = open(self.__output, "a")
        f.write(str(err)+"\n")
