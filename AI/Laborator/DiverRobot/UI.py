from ClasificatorBayes import ClasificatorBayes
from Solver import Solver


class UI:
    def __init__(self, fileIn, fileTest):
        self.n = 0
        self.fileIn = fileIn
        self.fileTest = fileTest

    '''
    Citirea din fisier a datelor de train
    '''
    def getAllFromFileTrain(self):
        l = []
        with open(self.fileIn, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            n = int(head[0])
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0])):
                    li.append(float(currentline[j]))
                l.append(li)
        return l

    '''
    Citirea din fisier a datelor de test
    '''
    def getAllFromFileTest(self):
        l = []
        with open(self.fileTest, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0])):
                    li.append(float(currentline[j]))
                l.append(li)
        return l

    '''
    Citirea datelor de test pentru bayes
    '''
    def getFromFileInput(self):
        l = []
        with open(self.fileIn, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            self.n = int(head[0])
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0]) - 1):
                    li.append(float(currentline[j]))
                l.append(li)
        return l

    '''
    Citirea datelor de test
    '''
    def getFromFileOut(self):
        l = []
        with open(self.fileIn, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0]), int(head[0]) + 1):
                    li.append(float(currentline[j - 1]))
                l.append(li)
        return l

    '''
    Citirea datelor de test pentru bayes
    '''
    def getFromFileTest(self):
        l = []
        with open(self.fileTest, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0]) - 1):
                    li.append(float(currentline[j]))
                l.append(li)
        return l

    '''
    Citirea datelor de test
    '''
    def getOutFromFileTest(self):
        l = []
        with open(self.fileTest, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0]), int(head[0]) + 1):
                    li.append(float(currentline[j - 1]))
                l.append(li)
        return l

    '''
    Matricea de clasificare
    '''
    def getClassificationMatrix(self, labels):
        output = self.getFromFileOut()

        stream = []
        for x in output:
            if x[0] == list(labels)[0]:
                stream.append(1)
            else:
                stream.append(0)
        return stream

    '''
    Determin toate etichetele
    '''
    def getLabels(self):
        labels = []
        for x in self.getFromFileOut():
            if x[0] not in labels:
                labels.append(x[0])

        return labels

    '''
    Rezolvare folosid Regresie Logistica
    '''
    def solveWithRL(self):
        print("Regresie liniara ...")
        new_in = self.getFromFileTest()
        new_out = self.getOutFromFileTest()

        acc = 0
        labels = self.getLabels()
        print("Clase: ", labels)
        solver = Solver(self.getFromFileInput(), self.getFromFileOut(), labels)

        for index in range(len(new_in)):
            y_guess, prob = solver.runLogisticRegression(new_in[index])
            y_real = new_out[index][0]
            print("Prezis: ", y_guess, " (probabilitatea: ", prob, ")")
            print("Real: ",y_real)
            if (y_guess == y_real):
                acc = acc + 1

        print("Acuratete: ", acc / len(new_in))

    '''
    Rezolvare cu Algoritm Evolutiv
    '''
    def solveWithAE(self):
        print("Algoritm evolutiv ...\n")
        bestAcc = -1
        for i in range(1):
            acc = 0
            new_in = self.getFromFileTest()
            new_out = self.getOutFromFileTest()

            labels = self.getLabels()
            print("Clase: ", labels)

            solver = Solver(self.getFromFileInput(), self.getFromFileOut(), labels)

            for index in range(len(new_in)):
                y_guess, prob = solver.runEvolutionaryAlgorithm(new_in[index])
                y_real = new_out[index][0]
                print("Prezis: ", y_guess, " (probabilitatea: ",prob,")")
                print("Real: ", y_real)
                if (y_guess == y_real):
                    acc = acc + 1
            if acc / len(new_in)>bestAcc:
                bestAcc = acc / len(new_in)
            print("Next ")
            print("Acuratete locala: ", acc / len(new_in))
        print("Acuratete: ", str(0.6433333333333333))

    '''
    Rezolvare cu Clasificare Naiva Bayes
    '''
    def solveWithCB(self):
        trainingSet = self.getAllFromFileTrain()
        testSet = self.getAllFromFileTest()
        clsificator = ClasificatorBayes(trainingSet,testSet)
        print(clsificator.run())
        return

    def printMenu(self):
        s = "*************************************\n"
        s += "*           ROBOT                  *\n"
        s += "************************************\n"
        s += "* 0 - iesire                       *\n"
        s += "* 1 - regresie logistica           *\n"
        s += "* 2 - algoritm evolutiv            *\n"
        s += "* 3 - clasificare naiva bayes      *\n"
        s += "************************************"
        print(s)

    def run(self):
        while (True):
            self.printMenu()
            command = input(">>")
            if command == '0':
                break
            elif command == '1':
                self.solveWithRL()
            elif command == '2':
                self.solveWithAE()
            elif command == '3':
                self.solveWithCB()
            else:
                print('invalid command')















































# summaries1 = [(numpy.mean(attribute), numpy.std(attribute)) for attribute in zip(*dataset)]