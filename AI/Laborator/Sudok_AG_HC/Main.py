from time import time

from Problema import Problema
from Solver import Solver
from Solver1 import Solver1
from SolverAE import SolverAE
from SolverHC import SolverHC


def main():
    # inputFile = "input_usor.txt"
    # inputFile = "easy_sudoku.txt"
    # inputFile = "medium_sudoku.txt"
    inputFile = "hard_sudoku.txt"
    # inputFile = "inputAlex.txt"
    # inputFile = "in.txt"
    # inputFile = "medium_s.txt"
    # inputFile = "exemplu.txt"
    # inputFile = "inH.txt"
    # inputFile = "inMM.txt"
    # inputFile = "medium_02_sudoku.txt"
    # inputFile = "hard_01_sudoku.txt"
    # inputFile = "medium_01_sudoku.txt"
    # inputFile = "inputTest.txt"

    problema = Problema(inputFile, 100, 100)

    solver = SolverAE(problema)
    start = time()
    cr = solver.algoritmEvolutiv()
    end = time()
    print(cr)
    print(cr.getFitness())
    print('execution: ' + str(end - start) + ' seconds')

    # solver = Solver(problema, 50000)
    # start = time()
    # rez = solver.hillClimbing()
    # end = time()
    # print(rez)
    # print('execution: ' + str(end - start) + ' seconds')

main()
