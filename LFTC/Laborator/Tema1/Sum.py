def number_sum(n):
    nr_sum = 0

    for i in range(n):
        nr = int(input('Number ' + str(i) + ': '))
        nr_sum += nr

    return nr_sum
