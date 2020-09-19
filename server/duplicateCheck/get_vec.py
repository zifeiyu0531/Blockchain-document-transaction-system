import check
import sys

if __name__ == '__main__':
    a = []
    for i in range(1, len(sys.argv)):
        a.append(sys.argv[i])

    print(check.get_vec(a[0]))