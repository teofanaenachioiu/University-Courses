from time import time

from Solver import Solver


class UI:

    def __init__(self, problema):
        self.__problema = problema
        self.__solver = Solver(self.__problema)

    def printMenu(self):
        s = "*******************************\n"
        s += "*           SUDOKU            *\n"
        s += "*******************************\n"
        s += "* 0 - iesire                  *\n"
        s += "* 1 - rezolva folosind DFS    *\n"
        s += "* 2 - rezolva folosind Greedy *\n"
        s += "*******************************"
        print(s)

    def findPathDFS(self):
        print('Asteptati...')
        start = time()
        self.__solver.DFS()
        end = time()
        print('-> Rezultatul a fost salvat in fisierul ' + self.__problema.getOutput())
        print('-> Timp de executie: ', end - start, " secunde\n")

    def findPathGDFS(self):
        print('Asteptati...')
        start = time()
        self.__solver.GDFS()
        end = time()
        print('-> Rezultatul a fost salvat in fisierul ' + self.__problema.getOutput())
        print('-> Timp de executie: ', end - start, " secunde\n")

    def run(self):
        runM = True
        while runM:
            self.printMenu()
            command = input(">>")
            if command == '0':
                runM = False
            elif command == '1':
                self.findPathDFS()
            elif command == '2':
                self.findPathGDFS()
            else:
                print('invalid command')
