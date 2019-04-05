from Cromozon import Cromozon


def run():
    testEval1()


def testEval1():
    initial = [0, 0, 1, 0, 0, 0, 0, 3, 2, 0, 0, 0, 0, 4, 0, 2]
    val1 = [3, 4, 1, 2, 4, 2, 1, 3, 2, 3, 1, 4, 3, 4, 1, 2]
    val2 = [3, 2, 1, 4, 4, 1, 2, 3, 2, 3, 4, 1, 1, 4, 3, 2]
    cr1 = Cromozon(val1)
    assert cr1.eval1(initial) == 26
    cr2 = Cromozon(val2)
    assert cr2.eval1(initial) == 0


run()
