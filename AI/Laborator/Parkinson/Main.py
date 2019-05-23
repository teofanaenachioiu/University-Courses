from Problema import Problema
from UI import UI

def main():
    # problema = Problema("example_parkinson_01_train", "example_parkinson_01_test", "rezultat.txt")
    # problema = Problema("easy_parkinson_dragos_01_train", "easy_parkinson_dragos_01_test", "rezultat.txt")
    # problema = Problema("medium_parkinson_dragos_01_train", "medium_parkinson_dragos_01_test", "rezultat.txt")
    # problema = Problema("hard_parkinson_dragos_01_train", "hard_parkinson_dragos_01_test", "rezultat.txt")

    # problema = Problema("easy_parkinson_01_train", "easy_parkinson_01_test", "rezultat.txt")
    problema = Problema("medium_parkinson_02_train", "medium_parkinson_02_test", "rezultat.txt")
    # problema = Problema("hard_parkinson_02_train", "hard_parkinson_02_test", "rezultat.txt")

    ui = UI(problema)
    ui.run()

main()

