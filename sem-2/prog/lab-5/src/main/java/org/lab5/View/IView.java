package org.lab5.View;

import java.lang.reflect.Method;

/**
 * Интерфейс отображения
 */
public interface IView {
    /**
     * Запросить ввести поле, являющееся числовым, строковым, булевым типом.
     * 
     * @param <T> Тип поля
     * @param fieldName Имя поля
     * @param checker Метод для проверки ограничений значения поля
     * @param fieldClass Класс поля
     * @param firstLastField Является ли поле первым (-1), средним (0), последним (1)
     * @return Введённое пользователем значение в указанном типе
     */
    <T> T readSimpleField(String fieldName, Method checker, Class<T> fieldClass, int firstLastField);

    /**
     * Запросить ввести поле, являющееся константой enum.
     * 
     * @param <T> Тип поля
     * @param fieldName Имя поля
     * @param checker Метод для проверки ограничений значения поля
     * @param enum Класс поля
     * @param firstLastField Является ли поле первым (-1), средним (0), последним (1)
     * @return Введённое пользователем значение в указанном типе
     */
    <T extends Enum<T>> T readEnumConstant(String fieldName, Method checker, Class<T> enumClass, int firstLastField);

    /**
     * Вывести результат некоторой операции
     * 
     * @param result Текст результата
     */
    void showResult(String result);

    /**
     * Вывести ошибку
     * 
     * @param message Текст ошибки
     */
    void showError(String message);

    /**
     * Вывести информационное сообщение
     * 
     * @param message Текст информационного сообщения
     */
    void showInfo(String message);
}