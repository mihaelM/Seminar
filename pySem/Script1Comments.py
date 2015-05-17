__author__ = 'User 1'


#test_convertKmers
"""
print convertKmers ("GGGGGGGGGG")
"""
#test splitanje
"""
niz = [i for i in xrange(20)]
splits = split (niz, 3)
for arr in splits:
    print arr
"""
#test links
"""
links = create_links()
for link in links:
    print link
"""

"""test read little data
links = create_links()
matrix = read_little_data(links)
print matrix
"""

"""
def read_little_data(links): #not so little
    wholeMatrix = []
    i = 0
    for link in links:

        if i == 1:
            break;
        data = urllib2.urlopen(link).read(500) # read only 20 000 chars
        data = data [(data.find("\n") + 1) :]
        print data
        i = i + 1

        kmers = slide(data, 10)          #velicina 10

        arr = [0 for i in xrange(pow(4, 10))] #niz s 4^10 nula
        for kmer in kmers :
            tmp = convertKmers(kmer)  #niz trazenih brojeva
            arr[tmp]+=1
        wholeMatrix.append(arr)
    return wholeMatrix
"""