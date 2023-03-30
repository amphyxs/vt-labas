package lab6.server.View;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;
import lab6.server.Presenter.Commands.ICommand;
import lab6.server.Presenter.Exceptions.InputEndedException;
import lab6.serializedobjects.Form.IForm;


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
     * Запросить у пользователя ввести значения в форму
     * 
     * @param <T> Тип объекта формы
     * @param form Экземпляр формы
     * @return Та же форма с введёнными значениями
     * @throws InputEndedException Если вводы был прерван
     */
    <T> IForm<T> readForm(IForm<T> form) throws InputEndedException;

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