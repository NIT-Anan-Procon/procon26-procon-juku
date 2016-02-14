# -*- coding: utf-8
import matplotlib.pyplot as plt
import numpy as np

with open('output.txt') as fp:
    csv = np.array(map(lambda l: (int(l.split(',')[0]), float(l.split(',')[1])),
                   fp.read().splitlines()))

tabu_search = sum([csv[300*(2*i):300*(2*i+1)] for i in range(100)]) / 100
greedy = sum([csv[300*(2*i+1):300*(2*i+2)] for i in range(100)]) / 100
tabu_search = tabu_search[:, 1]
greedy = greedy[:, 1]
t = range(1, 301)

plt.plot(t, tabu_search, 'r-')
plt.plot(t, greedy, 'b-')
plt.legend([u'Tabu Search', u'Greedy Algorithm'], loc='lower right')
plt.xlabel('Number of Steps')
plt.ylabel('$R$')
plt.show()


