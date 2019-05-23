from SolverLSM import LeastSquareMethod
from SolverGD import Gradient


class UI:

    def __init__(self, problema):
        self.__problema = problema

    def printMenu(self):
        s = "********************************\n"
        s += "*         PARKINSON           *\n"
        s += "*******************************\n"
        s += "* 0 - iesire                  *\n"
        s += "* 1 - rezolva folosind LSM    *\n"
        s += "* 2 - rezolva folosind GD     *\n"
        s += "*******************************"
        print(s)

    def solveWithLSM(self):
        print('LSM')
        lsm = LeastSquareMethod(self.__problema)
        print(lsm.devStd())
        print('-> Rezultatul a fost salvat in fisierul ' + self.__problema.getOutput())

    def solveWithGD(self):
        print('GD')
        dg = Gradient(self.__problema)
        maxiter = int(input("Numar iteratii: "))
        learning_rate = float(input("Learning rate: "))
        print(dg.devStd(maxiter, learning_rate))
        print('-> Rezultatul a fost salvat in fisierul ' + self.__problema.getOutput())

    def run(self):
        runM = True
        while runM:
            self.printMenu()
            command = input(">>")
            if command == '0':
                runM = False
            elif command == '1':
                self.solveWithLSM()
            elif command == '2':
                self.solveWithGD()
            else:
                print('invalid command')
