# Заменить все целые числа на функцию от этого числа. Функция: 4x^2 − 7, где x − исходное число. 

import re
from checker import read_input, check_output
from task2 import get_surnames

if __name__ == '__main__':
    pattern = re.compile(r'\w+\s+\w+\s+\w+а\b\n')
    for text, inp_cnt in read_input('3'):
        matches = pattern.findall(text)
        surnames = get_surnames(matches)
        print('Фамилии:')
        print(*matches, sep='\n')
        check_output('\n'.join(matches), '2', inp_cnt)