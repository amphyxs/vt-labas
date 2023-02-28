# Заменить все целые числа на функцию от этого числа. Функция: 4x^2 − 7, где x − исходное число. 

import re
from checker import read_input, check_output

if __name__ == '__main__':
    exp = re.compile('\d+')
    f = lambda x: 4*x**2 - 7
    for text, inp_cnt in read_input('3'):
        text = exp.sub(lambda m: str(f(int(m.group()))), text)
        print(f'Текст после обработки: {text}')
        check_output(text, '3', inp_cnt)
