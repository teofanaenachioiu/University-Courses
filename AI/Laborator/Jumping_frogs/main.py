from time import time

class Configuration:

    def __init__(self,positions):
        self.__values=positions[:]
        self.__size=len(positions)

    def getSize(self):
        return self.__size

    def getValues(self):
        return self.__values[:]

    def findPoz(self):
        for i in range(len(self.__values)):
            if self.__values[i] == '0':
                return i
        return -1;

    def nextConfig(self):
        nextC = []
        poz = self.findPoz()

        #left
        if poz>0:
            aux = self.__values[:]
            aux[poz], aux[poz - 1] = aux[poz - 1], aux[poz]
            nextC.append(Configuration(aux))

        #left left
        if poz>1:
            aux = self.__values[:]
            aux[poz], aux[poz - 2] = aux[poz - 2], aux[poz]
            nextC.append(Configuration(aux))

        #right
        if poz < self.getSize()-1:
            aux = self.__values[:]
            aux[poz], aux[poz + 1] = aux[poz + 1], aux[poz]
            nextC.append(Configuration(aux))

        #right right
        if poz < self.getSize()-2:
            aux = self.__values[:]
            aux[poz], aux[poz + 2] = aux[poz + 2], aux[poz]
            nextC.append(Configuration(aux))

        return nextC;

    def __eq__(self, other):
        if not isinstance(other, Configuration):
            return False
        if self.__size != other.getSize():
            return False
        for i in range(self.__size):
            if self.__values[i] != other.getValues()[i]:
                return False
        return True

    def __str__(self):
        s = ''
        for i in range(len(self.__values)):
            s = s + str(self.__values[i]) + " "
        return s

class State:

    def __init__(self):
        self.__values = []

    def setValues(self, values):
        self.__values = values[:]

    def getValues(self):
        return self.__values[:]

    def __str__(self):
        s = ''
        for x in self.__values:
            s += str(x) + "\n"
        return s

    def __add__(self, something):
        aux = State()
        if isinstance(something, State):
            aux.setValues(self.__values + something.getValues())
        elif isinstance(something, Configuration):
            aux.setValues(self.__values + [something])
        else:
            aux.setValues(self.__values)
        return aux

class Problem:

    def __init__(self):
        self.__initialConfig = Configuration(['R','R','R','0','V','V','V'])
        self.__finalConfig = Configuration(['V','V','V','0','R','R','R'])

        self.__initialState = State()
        self.__initialState.setValues([self.__initialConfig])

    def expand(self, state):
        myList = []
        currentConf = state.getValues()[-1]

        for x in currentConf.nextConfig():
            myList.append(state + x)

        return myList

    def getFinal(self):
        return self.__finalConfig

    def getRoot(self):
        return self.__initialState

    def heuristics(self, configuration):
        count = 0

        for i in range(len(self.__finalConfig.getValues())):
            if configuration.getValues()[-1].getValues()[i] == self.__finalConfig.getValues()[i]:
                count = count + 1
        return -1*count

class Controller:

    def __init__(self, problem):
        self.__problem = problem

    def BFS(self, root):
        q = [root]  # initial state added in Q
        viz = {}

        while len(q) > 0:
            currentState = q.pop(0)

            viz[str(currentState.getValues()[-1])] = True

            if currentState.getValues()[-1] == self.__problem.getFinal():
                return currentState

            for x in self.__problem.expand(currentState):
                if str(x.getValues()[-1]) not in viz:
                    q = q + [x]

    def GBFS(self, root):
        q = [root]  # initial state added in Q
        viz = {}

        while len(q) > 0:
            currentState = q.pop(0)

            viz[str(currentState.getValues()[-1])] = True

            if currentState.getValues()[-1] == self.__problem.getFinal():
                return currentState

            aux = []
            for x in self.__problem.expand(currentState):
                if str(x.getValues()[-1]) not in viz:
                    aux = aux + [x]

            aux = [[x, self.__problem.heuristics(x)] for x in aux]
            aux.sort(key=lambda x: x[1])
            aux = [x[0] for x in aux]

            q =   aux[:] + q

class UI:

    def __init__(self):
        self.__p = Problem()
        self.__contr = Controller(self.__p)

    def printMainMenu(self):
        s = ''
        s += "0 - exit \n"
        s += "1 - find a path with BFS \n"
        s += "2 - find a path with GBFS\n"
        print(s)

    def findPathBFS(self):
        startClock = time()
        print(str(self.__contr.BFS(self.__p.getRoot())))
        print('execution time = ', time() - startClock, " seconds")

    def findPathGBFS(self):
        startClock = time()
        print(str(self.__contr.GBFS(self.__p.getRoot())))
        print('execution time = ', time() - startClock, " seconds")

    def run(self):
        runM = True
        self.printMainMenu()
        while runM:
            try:
                command = int(input(">>"))

                if command == 0:
                    runM = False
                elif command == 1:
                    self.findPathBFS()
                elif command == 2:
                    self.findPathGBFS()
            except:
                print('invalid command')

def main():
    #tests();
    ui = UI();
    ui.run();

main()