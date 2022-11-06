# Программа должна считать количество смайликов вида: 8<{P

from checker import read_input, check_output
import re


def regexpify(s):
    to_shield = ')(.[]'
    for c in to_shield:
        s = s.replace(c, f'\\{c}')
    return s


emoji = regexpify(input('Введите смайлик: '))
exp = re.compile(emoji)
for text, inp_cnt in read_input('1'):
    results = len(exp.findall(text))
    print(f'Количество совпадений с \"{exp.pattern}\": {results}')
    check_output(results, '1', inp_cnt)
