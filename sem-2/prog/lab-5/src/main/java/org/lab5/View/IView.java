package org.lab5.View;

import java.lang.reflect.Method;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;
import org.lab5.Presenter.Commands.ICommand;
import org.lab5.Presenter.Exceptions.InputEndedException;

/**
 * Интерфейс отображения
 */
public interface IView {

    /**
     *
     * @return Привязанное представление
     */
    IPresenter getPresenter();

    /**
     *
     * @param presenter Представление для привязки
     */
    void setPresenter(IPresenter presenter);

    /**
     *
     * @return Выполняется ли в данном отображении скрипт
     */
    boolean getIsScriptMode();

    /**
     * Запросить у пользователя ввод команды
     *
     * @return Команда, указанная пользователем
     * @throws CommandNotFoundException Если такой команды не существует
     * @throws BadCommandArgException Если введённый аргумент не подходит
     * @throws BadCommandArgsNumberException Если не соответствует число введённых аргументов
     * @throws NullCommandException Если команда пустая
     * @throws InputEndedException Если ввод был прерван
     */
    ICommand readCommand() throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException, NullCommandException, InputEndedException;

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
    <T> T readSimpleField(String fieldName, Method checker, Class<T> fieldClass, int firstLastField) throws InputEndedException;

    /**
     * Запросить ввести поле, являющееся константой enum.
     * 
     * @param <T> Тип поля
     * @param fieldName Имя поля
     * @param checker Метод для проверки ограничений значения поля
     * @param enumClass Класс поля
     * @param firstLastField Является ли поле первым (-1), средним (0), последним (1)
     * @return Введённое пользователем значение в указанном типе
     */
    <T extends Enum<T>> T readEnumConstant(String fieldName, Method checker, Class<T> enumClass, int firstLastField) throws InputEndedException;

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