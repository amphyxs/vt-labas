# Программирование 

## Лабораторная работа № 1

Написать программу на языке Java, выполняющую соответствующие варианту действия. Программа должна соответствовать следующим требованиям:

Она должна быть упакована в исполняемый jar-архив.
Выражение должно вычисляться в соответствии с правилами вычисления математических выражений (должен соблюдаться порядок выполнения действий и т.д.).
Программа должна использовать математические функции из стандартной библиотеки Java.
Результат вычисления выражения должен быть выведен в стандартный поток вывода в заданном формате.
Выполнение программы необходимо продемонстрировать на сервере helios.

Создать одномерный массив a типа int. Заполнить его числами от 3 до 20 включительно в порядке возрастания.
Создать одномерный массив x типа float. Заполнить его 19-ю случайными числами в диапазоне от -7.0 до 3.0.
Создать двумерный массив a размером 18x19. Вычислить его элементы по следующей формуле (где x = x[j]):

если a[i] = 8, то a[i][j]=e^e^ln(|x|);

если a[i] ∈ {4, 6, 12, 13, 14, 15, 16, 18, 19}, то a[i][j] = arcsin(e^(cbrt(-arccos((x-2)/1E+1))))

для остальных значений a[i]: a[i][j] = ((arctan(3/4*(x-2)/1E+1))^(2*(2/tan(x))^(tan(x))))^(2*cbrt(cbrt(cos(x))))

Напечатать полученный в результате массив в формате с пятью знаками после запятой.
