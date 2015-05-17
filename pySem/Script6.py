__author__ = 'Mihael Marovic'
import Script1
import numpy as np
from scipy.sparse import csr_matrix
from scipy.sparse import csc_matrix

#napunit cemo je
listaTestova = []

def dot_product (original, test): # uzimamo da su iste velicine 4^10, ovo bi trebalo nesto sparse, hah
    rez = 0
    for i in xrange (pow(4,10)):
        rez+= original[i] * test[i]
    return rez

def calculate (original, our_matrix):
    fv = []
    for test in our_matrix:
        fv.append(dot_product(original, test))
    return fv

def print_statistic (fv):
    i = 0
    most_similar= -1
    mx = -1
    for arr in fv:
        if arr > mx:
            mx = arr
            most_similar = i
        i = i+1
    print most_similar
"""
def read_from_file(original): # za sad samo prvi usporedujem, ako maknem break bit ce vise
    test = []
    arr = [0 for i in xrange(pow(4, 10))]
    lines = [line.strip() for line in open('Testing1.fq')]
    i = 0
    for line in lines:
        if (Script1.convertKmers(line[0:10]) == -1):
            continue; #inace pretpostavi da je cijela linija dobra i sliceaj ju
        # print line
        create_one(line, original)
        break;
    return;
"""
def read_from_file(): # za sad samo prvi usporedujem, ako maknem break bit ce vise
    test = []
    arr = [0 for i in xrange(pow(4, 10))]
    i = 0
    with open('Testing1.fq') as f:
        for line in f:
            if (Script1.convertKmers(line[0:10]) == -1):
                continue; #inace pretpostavi da je cijela linija dobra i sliceaj ju
            # print line
            listaTestova.append(create_one(line))
            i = i+1
            if (i == 10000):
                break;
    return;

def create_one(line):
    #jos bi tu trebalo sve originale stavit na nulu i jesam
    original = [0 for i in xrange(pow(4, 10))]
    kmers = Script1.slide(line, 10)
    for kmer in kmers:
        tmp = Script1.convertKmers(kmer)  #niz trazenih brojeva
       # print tmp
        original[tmp]+=1
    return original

def solve():

    read_from_file ()
   # print original[0:1000]
    matrix = Script1.give_matrix_from_txt()
    konMatrix = csr_matrix(matrix)

    finStats = [0 for i in xrange(0, 12)]

    for i in xrange(0, 10000):
         test = csr_matrix(listaTestova[i]).transpose()
         kon = konMatrix._mul_sparse_matrix(test)
         mx = kon.max()
         for j in xrange(0, 12):
             if (kon.getrow(j).toarray().max() == mx):
                finStats[j]+=1

    for i in xrange(0, 12):
        print str(i) + "-toga ima " + str(finStats[i])

    return


solve()

"""
 TEST = csr_matrix(original).transpose()
    MATRIX = csr_matrix(matrix)
    KON = MATRIX.dot(TEST)
"""