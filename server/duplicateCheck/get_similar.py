import check
import sys

if __name__ == '__main__':
    a = []
    for i in range(1, len(sys.argv)):
        a.append(sys.argv[i])

    print(check.check_dup(a[0],a[1]))