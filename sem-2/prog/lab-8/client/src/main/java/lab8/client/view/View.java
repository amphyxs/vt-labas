package lab8.client.view;

import javafx.stage.Stage;
import lab8.client.presenter.AppLanguage;
import lab8.client.presenter.Presenter;
import lab8.client.presenter.commands.Command;
import lab8.client.presenter.exceptions.*;
import lab7.serializedobjects.form.Form;


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

    void start(Stage stage);

    void stop();

    boolean checkIfStopped();

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

    void setAppLanguage(AppLanguage language);

    AppLanguage getAppLanguage();
}