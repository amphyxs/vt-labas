import random

s = ''
for i in range(100):
    x = random.randint(1, 100)
    y = random.randint(1, 100)
    h = random.randint(1, 100)
    s += f'add\n1\n{x}\n{y}\n{h}\n1\n1\n1\n1\n1\n1\n'

fout = open('killserv', 'w')
fout.write(s)