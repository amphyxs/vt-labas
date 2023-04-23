package lab7.server.view;

import lab7.serializedobjects.form.Form;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.commands.Command;
import lab7.server.presenter.exceptions.*;


/**
 * Интерфейс отображения
 */
public interface View {

    /**
     *
     * @return Привязанное представление
     */
    Presenter getPresenter();

    /**
     *
     * @param presenter Представление для привязки
     */
    void setPresenter(Presenter presenter);

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
    Command readCommand() throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException, NullCommandException, InputEndedException;

    /**
     * Запросить у пользователя ввести значения в форму
     * 
     * @param <T> Тип объекта формы
     * @param form Экземпляр формы
     * @return Та же форма с введёнными значениями
     * @throws InputEndedException Если вводы был прерван
     */
    <T> Form<T> readForm(Form<T> form) throws InputEndedException;

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