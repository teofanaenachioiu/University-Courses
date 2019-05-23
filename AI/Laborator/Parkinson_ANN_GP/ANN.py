import math

from numpy.ma import exp
from numpy.random import random

from Neuron import Neuron

ACTIVATION = "Linear"
# ACTIVATION = "Sigmoid"

'''
Normalizarea datelor de train
'''
def normalizareDateTrain(regressionDataTrain):
    list_means = []
    noAttributes = len(regressionDataTrain[0])
    n = len(regressionDataTrain)
    for i in range(noAttributes):
        sum = 0
        for el in regressionDataTrain:
            sum = sum + el[i]
        list_means.append(sum / n)
    list_deviation = []
    for i in range(noAttributes):
        sum = 0
        for el in regressionDataTrain:
            sum = sum + (el[i] - list_means[i]) ** 2
        list_deviation.append(math.sqrt(sum / (n - 1)))
    normalized_dataIn = []
    for i in range(n):
        li = []
        for j in range(len(regressionDataTrain[i])):
            li.append((regressionDataTrain[i][j] - list_means[j]) / list_deviation[j])
        normalized_dataIn.append(li)
    means = list_means
    deviation = list_deviation
    return normalized_dataIn, means, deviation


'''
Normalizarea datelor de test pe baza mediei si deviatiei standard a datelor de train
'''
def normalizeOneRowInputTest(dataT, means, deviation):
    dataTest = []
    for data in dataT:
        data2 = []
        for x in data:
            data2.append(x)
        for i in range(len(data2)):
            data2[i] = (data2[i] - means[i]) / deviation[i]
        dataTest.append(data2)
    return dataTest

'''
Initierea retelei neuronale
'''
def netInitialisation(noInputs, noOutputs, noHiddenNeurons):
    net = []
    hiddenLayer = []
    for h in range(noHiddenNeurons):  # create hidden layers
        weights = [random() for i in range(noInputs + 1)]  # noInputs and the bias
        neuron = Neuron(weights)
        hiddenLayer.append(neuron)
    net.append(hiddenLayer)
    outputLayer = [Neuron([random() for i in range(noHiddenNeurons + 1)]) for o in range(noOutputs)]
    net.append(outputLayer)
    return net

'''
Evaluare 
'''
def activate(input, weights):
    result = 0.0
    for i in range(0, len(input)):
        result += input[i] * weights[i]
        result += weights[len(input)]
    return result


'''
Transferul neurnoului
'''
def transfer(value):
    if ACTIVATION == "Linear":
        return value
    elif ACTIVATION == "Sigmoid":
        return 1.0 / (1.0 + exp(-value))


'''
Propagarea datelor in reteaua neuronala
'''
def forwardPropagation(net, inputs):
    for layer in net:
        newInputs = []
        for neuron in layer:
            activation = activate(inputs, neuron.weights)
            neuron.output = transfer(activation)
            newInputs.append(neuron.output)
        inputs = newInputs
    return inputs


'''
Transferul invers al neuronului
'''
def transferInverse(val):
    if ACTIVATION == "Linear":
        return val
    elif ACTIVATION == "Sigmoid":
        return val * (1 - val)


'''
Propagarea erorii
'''
def backwardPropagation(net, expected):
    for i in range(len(net) - 1, 0, -1):
        crtLayer = net[i]
        errors = []
        if i == len(net) - 1:
            #sunt pe ultimul strat
            for j in range(0, len(crtLayer)):
                crtNeuron = crtLayer[j]
                errors.append(expected[j] - crtNeuron.output)
        else:
            # sunt pe un strat ascuns
            for j in range(0, len(crtLayer)):
                crtError = 0.0
                nextLayer = net[i + 1]
                for neuron in nextLayer:
                    crtError += neuron.weights[j] * neuron.delta
                errors.append(crtError)
            for j in range(0, len(crtLayer)):
                crtLayer[j].delta = errors[j] * transferInverse(crtLayer[j].output)


'''
Actualizarea costurilor
'''
def updateWeights(net, example, learningRate):
    for i in range(0, len(net)):  # pentru fiecare strat
        inputs = example[:-1]
        if i > 0:  # strat ascuns sau de output
            inputs = [neuron.output for neuron in net[i - 1]]  # calcularea val pt stratul prec
        for neuron in net[i]:  # updatare costuri
            for j in range(len(inputs)):
                neuron.weights[j] += learningRate * neuron.delta * inputs[j]
            neuron.weights[-1] += learningRate * neuron.delta

'''
Train-urirea retelei
'''
def trainingMLP(net, data, learningRate, noEpochs):
    for epoch in range(0, noEpochs):
        sumError = 0.0
        for example in data:
            inputs = example[:- 1]
            computedOutputs = forwardPropagation(net, inputs)
            expected = [example[-1]]
            crtErr = sum([(expected[i] - computedOutputs[i]) ** 2 for i in range(0, len(expected))])

            print("computed: ",computedOutputs, " expected: ", expected)
            sumError += crtErr
            backwardPropagation(net, expected)
            updateWeights(net, example, learningRate)
        # print("Epoch: ", epoch, "sumError: ",sumError)

'''
Evaluarea retelei
'''
def evaluatingMLP(net, data):
    computedOutputs = []
    for inputs in data:
        computedOutput = forwardPropagation(net, inputs[:-1])
    computedOutputs.append(computedOutput[0])
    return computedOutputs

'''
Calcularea erorii
'''
def computeError(computedOutputs, realOutputs):
    error = sum([(computedOutputs[i] - realOutputs[i]) ** 2 for i in range(len(computedOutputs))])
    return error/len(realOutputs)


'''
Rularea retelei neuronale
'''
def runMLP(trainData, testData, learningRate, noEpochs):
    xTrain = [trainData[i][0:len(trainData[0]) - 1] for i in range(len(trainData))]
    yTrain = [row[-1] for row in trainData]

    xTrain, means, deviation = normalizareDateTrain(xTrain)

    xTest = [testData[i][0:len(testData[0]) - 1] for i in range(len(testData))]
    yTest = [row[-1] for row in testData]

    xTest = normalizeOneRowInputTest(xTest, means, deviation)

    xTrainInainte = xTrain
    xTestInainte = xTest
    for i in range(len(xTrain)):
        xTrainInainte[i].append(yTrain[i])
    for i in range(len(xTest)):
        xTestInainte[i].append(yTest[i])

    '''running algorithm'''
    noInputs = len(trainData[0]) - 1
    noOutputs = 1
    net = netInitialisation(noInputs, noOutputs, 10)
    trainingMLP(net, xTrainInainte, learningRate, noEpochs)

    # computedOutputs = evaluatingMLP(net, xTrainInainte)
    # print("error train: ", computePerformanceRegression(computedOutputs, yTrain))

    computedOutputs = evaluatingMLP(net, xTestInainte)
    print("error test: ", computeError(computedOutputs, yTest))

'''
Entry point
Setarea learning rate-ului si a numarului de epoci 
'''
def regression(regressionDataTrain, regressionDataTest):
    learningRate = 0.01
    noEpochs = 100
    runMLP(regressionDataTrain, regressionDataTest, learningRate, noEpochs)
