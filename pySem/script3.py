__author__ = 'User 1'
import Script1
import numpy as np
from scipy.sparse import csr_matrix

import numpy as np
from scipy.sparse import csr_matrix
A = csr_matrix([[1, 2, 0], [0, 0, 3], [4, 0, 5]])
v = np.array([1, 0, -1])
print A
print A.dot(v)
