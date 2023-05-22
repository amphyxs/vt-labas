
from math import ceil
from typing import Callable


def integral(f: Callable, nth_derivative: Callable, a: float, b: float, h: float, integral_method: Callable, integral_error: Callable) -> tuple[float, float]:
    '''
    Функция для численного вычисления интеграла функции f. 

    - f - функция, для которой ищется интеграл;
    - a - левая граница интегрирования;
    - b - правая граница интегрирования;
    - h - шаг разбиения на трапеции;
    - integral_method - функция, вычисляющая интеграл на отрезке разбиения;
    - integral_error - функция, вычисляющая погрешность значения на отрезке разбиения.

    Возвращает кортеж из двух значений:
    - Значение интеграла;
    - Погрешность вычисления заданным методом при данном разбиении.
    '''

    x = [min(a + h*k, b) for k in range(ceil((b - a) / h) + 1)] # Точки разбиения
    result = 0 # Искомое значение интеграла
    error = 0 # Погрешность вычисления

    for k in range(1, len(x)):
        result += integral_method(f, x[k - 1], x[k])
        error += integral_error(nth_derivative, x[k - 1], x[k])
    
    return (result, error)


def trapezoid_integral(f: Callable, a: float, b: float) -> float:
    '''
    Интеграл трапеции.

    - f - функция для вычисления длин оснований трапеции в точках a и b;
    - a - левая граница отрезка основания трапеции;
    - b - правая граница отрезка основания трапеции;
    '''

    return (f(a) + f(b))/2 * (b - a)

def trapezoid_error(f_derivative_2: Callable, a: float, b: float) -> float:
    '''
    Погрешность интеграла трапеции.

    - f_derivative_2 - вторая производная интегрируемой функции;
    - a - левая граница отрезка основания трапеции;
    - b - правая граница отрезка основания трапеции;
    '''

    return  (b - a)**3/12 * max_value(f_derivative_2, a, b)


def parabola_integral(f: Callable, a: float, b: float) -> float:
    '''
    Интеграл параболы.

    - f - функция для вычисления длин оснований трапеции в точках a и b;
    - a - левая граница отрезка;
    - b - правая граница отрезка;
    '''

    return (b - a)/6 * (f(a) + 4*f((a + b)/2) + f(b))


def parabola_error(f_derivative_4: Callable, a: float, b: float) -> float:
    '''
    Погрешность интеграла параболы.

    - f_derivative_4 - четвёртая производная интегрируемой функции;
    - a - левая граница отрезка;
    - b - правая граница отрезка;
    '''

    return (b - a)**5/2880 * max_value(f_derivative_4, a, b)


def max_value(f: Callable, a: float, b: float, step=0.01) -> float:
    '''
    Функция для нахождения максимального значения функции f на отрезке [a; b]
    путём перебора всех значений на нём с шагом step.
    '''
    result = f(a) # Наибольшее значение на отрезке
    x = a
    while x <= b:
        result = max(result, f(x))
        x += step
    
    return result


def print_result(integral_result: tuple[float, float], method_name: str):
    '''
    Вывести результат вычисления интеграла
    '''
    integral_value, error = integral_result
    print(f'Интеграл методом {method_name}: {integral_value} ± {error}')


if __name__ == '__main__':
    # Внесите сюда функцию, её вторую и четвёртую производные
    f = lambda x: 1 + x**2
    f_derivative_2 = lambda x: 2
    f_derivative_4 = lambda x: 0

    print_result(integral(f, f_derivative_2, 0, 2, 1, trapezoid_integral, trapezoid_error), 'трапеций')
    print_result(integral(f, f_derivative_2, 0, 2, 1, parabola_integral, parabola_error), 'парабол')
