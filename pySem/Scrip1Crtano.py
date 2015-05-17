__author__ = 'Mihael Marovic'
import urllib2

#bioPython

#create list of links needed for this
def create_links():
    links = [line.strip('\n') for line in open('links.txt')]
    return links

#read data from online links
def read_data_and_create_matrix(links):
    wholeMatrix = []
    for link in links:
        data = urllib2.urlopen(link).read() # it's a file like object and works just like a file

        data = data[(data.find("\n") + 1) :]

        kmers = slide(data, 8)          #kmers size 10

        arr = [0 for i in xrange(pow(4, 8))] #array with 4^10 zeroes
        for kmer in kmers :
            if (tmp == -1):
                continue;
            tmp = convertKmers(kmer)  #array of wanted numbers
            arr[tmp]+=1
        wholeMatrix.append(arr)
    return wholeMatrix

#spliting into sublists, I should use yield..
def slide(arr, size):
     arrs = []

     for i in xrange(0, len(arr) - size):
         j = i + size # najvise
         s = arr[i : j]
         arrs.append(s)
     return arrs

# convert k-mers into numbers
def convertKmers(s):
    i = 0  # brojac
    ret = 0
    for x in s:
        br = -1
        if x == 'A':
            br = 0
        elif x == 'C':
            br = 1
        elif x == 'T':
            br = 2
        elif x == 'G':
            br = 3
        else:
            return -1 #conversion fail
        ret +=  (pow (4, i)) * br
        i+=1
    return ret

# we get our matrix (numbers etc.)
def give_matrix():
    links = create_links()
    matrix = read_data_and_create_matrix(links)
    return matrix

# creates textual representation of matrix
def create_txt_matrix():
    matrix = give_matrix()
    out = ""
    for row in matrix:
        for el in row :
            out+= str(el)
            out+=" "
        out += "\n"
    with open("matrica.txt","wt") as file:
         file.write(out)

# provides matrix (list of lists) from textual representation of matrix
def give_matrix_from_txt():
    matrix = []
    lines = [line.strip() for line in open('matrica.txt')]
    for line in lines:
        arr = []
        svi = line.split()
        for s in svi:
           arr.append(int(s))
        matrix.append(arr)
    return matrix

create_txt_matrix()
