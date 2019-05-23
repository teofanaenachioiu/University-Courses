import numpy as np

from Problema import Problema
from SolverLSM import LeastSquareMethod

problema = Problema("example_parkinson_01_train", "example_parkinson_01_test", "rezultatTest.txt")
lsm = LeastSquareMethod(problema)


def testConstructInputMatrix():

    inputM = lsm.constructInputMatrix()
    matrice = np.array([[1, 0.00662, 3.38e-05], [1, 0.0081, 6.375e-05], [1, 0.00273, 1.57e-05],
                        [1, 0.00382, 3.005e-05], [1, 0.00501, 3.648e-05], [1, 0.00453, 3.481e-05],
                        [1, 0.00526, 6.016e-05], [1, 0.0087, 4.743e-05], [1, 0.0124, 0.00011434],
                        [1, 0.00476, 4.216e-05]])
    assert inputM.all() == matrice.all()


def testConstructOutputMatrix():

    outputM = lsm.constructOutMatrix()
    matrice = np.array([[28.199], [11.218], [23.856], [11.27], [31.0], [27.729], [16.816], [18.169], [17.0], [12.0]])
    assert outputM.all() == matrice.all()


def testGetWeights():
    coef = lsm.getWeights()
    matrice = np.array([[2.21960022e+01], [1.20091608e+03], [-2.06977010e+05]])
    assert coef.all() == matrice.all()


def testModel():

    rezultat = lsm.model()
    matrice = np.array([[20.88329366], [21.06093691], [22.9164371]])
    assert rezultat.all() == matrice.all()


def testDevStd():

    dev = lsm.devStd()
    assert dev == 18.42576501433854


def teste():
    testConstructInputMatrix()
    testConstructOutputMatrix()
    testGetWeights()
    testModel()
    testDevStd()

teste()
