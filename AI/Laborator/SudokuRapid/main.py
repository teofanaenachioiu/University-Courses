from Problema import Problema
from UI import UI

def main():
    inputFile = "medium.txt"
    outputFile = inputFile.split(".")[0] + "REZULTAT.txt"
    problema = Problema(inputFile, outputFile)
    ui = UI(problema)
    ui.run()

main()
