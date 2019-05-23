import random

MAX_DEPTH = 2
FUNCTION_SET = ["+", "-"]

class Chromosome:
    def __init__(self, dimTerminal):
        self.representation = []
        self.fitness = 0.0
        self.TERMINAL_SET = [i for i in range(dimTerminal)]

    def grow(self):
        # first_layer
        self.representation.append(random.choice(FUNCTION_SET))
        left = [random.choice(FUNCTION_SET),
                random.choice(self.TERMINAL_SET),
                random.choice(self.TERMINAL_SET)]
        right = [random.choice(FUNCTION_SET),
                 random.choice(self.TERMINAL_SET),
                 random.choice(self.TERMINAL_SET)]
        self.representation.append(left)
        self.representation.append(right)

    # def eval(self, inExample, pos):
    #     if (self.representation[pos] in self.TERMINAL_SET):
    #         return inExample[self.representation[pos]]
    #     else:
    #         if (self.representation[pos] == "+"):
    #             pos += 1
    #             left = self.eval(inExample, pos)
    #             pos += 1
    #             right = self.eval(inExample, pos)
    #             return left + right
    #         elif (self.representation[pos] == "-"):
    #              pos += 1
    #              left = self.eval(inExample, pos)
    #              pos += 1
    #              right = self.eval(inExample, pos)
    #              return left + right
    #         elif (self.representation[pos] == "*"):
    #              pos += 1
    #              left = self.eval(inExample, pos)
    #              pos += 1
    #              right = self.eval(inExample, pos)
    #              return left + right

    def eval(self, input: [], output: []):
        def __eval_function(reprezentare: [], input: []):
            def __eval_operand(operand, input: []):
                if operand in self.TERMINAL_SET:
                    return input[operand]
                else:
                    return __eval_function(operand, input)

            function = reprezentare[0]
            left = __eval_operand(reprezentare[1], input)
            right = __eval_operand(reprezentare[2], input)
            if function == '+':
                return left + right
            if function == '-':
                return left - right
            if function == '*':
                return left * right
            if function == '/':
                if right != 0:
                    return left / right
                if left != 0:
                    return right / left
                return 1

        for i in range(len(input)):
            evaluated = __eval_function(self.representation, input[i])
            self.fitness += (evaluated - output[i]) ** 2
            # print("Yguess:"+str(evaluated)+"yreal: "+str(output[i]))
            self.fitness = self.fitness
        return self.fitness

    def __str__(self):
        return str(self.representation) + " fit = " + str(self.fitness)

    def __repr__(self):
        return str(self.representation)
