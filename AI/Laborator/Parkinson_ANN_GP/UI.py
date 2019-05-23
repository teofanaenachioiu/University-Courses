import math

from ANN import regression
from PGGG import run


class UI:

    def __init__(self, file_train, file_test, file_result):
        self.file_train = file_train
        self.file_test = file_test
        self.file_result = file_result
        self.regressionDataTrain = self.getAllFromFileTrain()
        self.regressionDataTest = self.getAllFromFileTest()

    def getAllFromFileTrain(self):
        l = []
        with open(self.file_train, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            n = int(head[0])
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0])):
                    li.append(float(currentline[j]))
                l.append(li)
        return l

    def getAllFromFileTest(self):
        l = []
        with open(self.file_test, "r") as filestream:
            head = [next(filestream) for x in range(2)]
            for line in filestream:
                li = []
                currentline = line.split(",")
                for j in range(int(head[0])):
                    li.append(float(currentline[j]))
                l.append(li)
        return l

    def printMenu(self):
        s = "********************************\n"
        s += "*         PARKINSON           *\n"
        s += "*******************************\n"
        s += "* 0 - iesire                  *\n"
        s += "* 1 - rezolva folosind ANN    *\n"
        s += "* 2 - rezolva folosind PG     *\n"
        s += "*******************************"
        print(s)

    def solveWithANN(self):
        print('ANN')
        # print("Data train: " , self.regressionDataTrain)
        # print("Data test: " , self.regressionDataTest)
        regression(self.regressionDataTrain, self.regressionDataTest)
        print('-> Rezultatul a fost salvat in fisier')

    def solveWithPG(self):
        print('PG')
        # print(self.regressionDataTrain)
        run(self.regressionDataTrain,self.regressionDataTest)
        print('-> Rezultatul a fost salvat in fisier')

    def run(self):
        runM = True
        while runM:
            self.printMenu()
            command = input(">>")
            if command == '0':
                runM = False
            elif command == '1':
                self.solveWithANN()
            elif command == '2':
                self.solveWithPG()
            else:
                print('invalid command')
