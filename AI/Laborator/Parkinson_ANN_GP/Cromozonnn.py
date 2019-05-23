import random

MAX_DEPTH = 7
FUNCTION_SET = ["-", "+"]

class Chromosome:
    def __init__(self, dimTerminal):
        self.representation = []
        self.fitness = 0.0
        self.TERMINAL_SET = [i for i in range(dimTerminal)]

    '''
    Crearea cromozon
    '''
    def grow(self, crtDepth):
        if (crtDepth == MAX_DEPTH):
            terminal = random.choice(self.TERMINAL_SET)
            self.representation.append(terminal)
        else:
            if (random.random() < 0.5):
                terminal = random.choice(self.TERMINAL_SET)
                self.representation.append(terminal)
            else:
                function = random.choice(FUNCTION_SET)
                self.representation.append(function)
                self.grow(crtDepth + 1)
                self.grow(crtDepth + 1)

    '''
    Evaluare cromozon
    '''
    def eval(self, inExample, pos):
        if (self.representation[pos] in self.TERMINAL_SET):
            return inExample[self.representation[pos]]
        else:
            if (self.representation[pos] == "+"):
                pos += 1
                left = self.eval(inExample, pos)
                pos += 1
                right = self.eval(inExample, pos)
                return left + right
            elif (self.representation[pos] == "-"):
                 pos += 1
                 left = self.eval(inExample, pos)
                 pos += 1
                 right = self.eval(inExample, pos)
                 return left + right
            elif (self.representation[pos] == "*"):
                 pos += 1
                 left = self.eval(inExample, pos)
                 pos += 1
                 right = self.eval(inExample, pos)
                 return left + right

    def __str__(self):
        return str(self.representation) + " fit = " + str(self.fitness)

    def __repr__(self):
        return str(self.representation)