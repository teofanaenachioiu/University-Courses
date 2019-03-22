from Problema import Problema
class Solver:
    def __init__(self, problema):
        self.__problema = problema
        self.root = self.__problema.readFromFile()

    def DFS(self):
        s = [self.root]
        viz = {}
        viz[str(self.root)] = True
        while len(s) > 0:
            currendConfig = s.pop()
            if currendConfig.isFinal():
                self.__problema.writeInFile(currendConfig)
            list = self.__problema.expand(currendConfig, viz)
            s.extend(list)

    def GDFS(self):
        s = [self.root]
        viz = {}
        viz[str(self.root)] = True
        while len(s) > 0:
            currendConfig = s.pop()
            if currendConfig.isFinal():
                self.__problema.writeInFile(currendConfig)
            list = self.__problema.expandEuristic(currendConfig, viz)  # reversed
            s.extend(list)