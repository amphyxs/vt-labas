# Дан текст. Требуется найти в тексте все фамилии, отсортировав их по алфавиту.
# Фамилией для простоты будем считать слово с заглавной буквой, после которого идут инициалы.

from checker import read_input, check_output
import locale
import re


def get_surnames(results):
    for i in range(len(results)):
        if ' ' in results[i]:
            results[i] = results[i].split(' ')[0]
        if '\n' in results[i]:
            results[i] = results[i].split('\n')[0]
    return russian_sorted(results)


def russian_sorted(strs):
    locale.setlocale(locale.LC_ALL, ('ru_RU', 'UTF-8'))
    return sorted(strs, key=locale.strxfrm)


if __name__ == '__main__':
    exp = re.compile(r'\b[А-ЯЁ][а-яё]+\s+[А-ЯЁ]\.[А-ЯЁ]\.(?=[^\w]|\Z)')
    for text, inp_cnt in read_input('2'):
        results = exp.findall(text)
        results = get_surnames(results)
        print('Фамилии:')
        print(*results, sep='\n')
        check_output('\n'.join(results), '2', inp_cnt)
