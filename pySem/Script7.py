__author__ = 'Mihael Marovic'
import Script1
import numpy as np
from scipy.sparse import csr_matrix
from scipy.sparse import csc_matrix


# Creates sparse vector from given string.
def create_one(line):
    #jos bi tu trebalo sve originale stavit na nulu i jesam
    original = [0 for i in xrange(pow(4, 10))]
    kmers = Script1.slide(line, 10)
    for kmer in kmers:
        tmp = Script1.convertKmers(kmer)  #niz trazenih brojeva
       # print tmp
        original[tmp]+=1
    return original


# Reads input (testing) and performs the algorithm.
def solve():

    matrix = Script1.give_matrix_from_txt()
    konMatrix = csr_matrix(matrix)

    finStats = [0 for i in xrange(0, 12)]

    test = []
    arr = [0 for i in xrange(pow(4, 10))]
    i = 0
    with open('Testing1.fq') as f:
        for line in f:
            if (Script1.convertKmers(line[0:10]) == -1):
                continue; #inace pretpostavi da je cijela linija dobra i sliceaj ju
            # print line
            test = csr_matrix(create_one(line)).transpose()
            kon = konMatrix._mul_sparse_matrix(test)
            mx = kon.max()
            for j in xrange(0, 12):
                if (kon.getrow(j).max() == mx):
                 finStats[j]+=1

            i += 1
            if i == 100:
                break;
   # print original[0:1000]

    sum = 0
    for i in xrange(0, 12):
        print str(i) + "-toga ima " + str(finStats[i])
        sum += finStats[i]
    for i in xrange(0, 12):
        print str(i) + "-toga ima " + str(100*float(finStats[i])/float(sum)) + "%"



    return

#Main program starts.

solve()
