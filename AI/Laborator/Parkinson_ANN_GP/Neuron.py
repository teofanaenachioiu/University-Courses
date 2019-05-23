class Neuron:
    def __init__(self, w = [], out = None, delta = 0.0):
        self.weights = w
        self.output = out
        self.delta = delta
    def __str__(self):
        return "weights: " + str(self.weights) + ", output: " + str(self.output) + ", delta: " + str(self.delta)
    def __repr__(self):
        return "weights: " + str(self.weights) + ", output: " + str(self.output) + ", delta: " + str(self.delta)