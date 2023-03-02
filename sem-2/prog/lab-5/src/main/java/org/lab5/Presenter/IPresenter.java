package org.lab5.Presenter;

import org.lab5.Model.DataClasses.SpaceMarine;
import org.lab5.Presenter.Commands.ICommand;
import org.lab5.Presenter.Exceptions.*;
import org.lab5.View.IView;
import org.lab5.Model.IModel;

/**
 * Интерфейс представления
 */
public interface IPresenter {
    
    /**
     * @return Все доступные команды
     */
    ICommand[] getCommands();

    /**
     * 
     * @param commandWithArgs Имя команды и аргументы в виде отдельных строк
     * @return Команда с этим именем и присвоенными аргументами
     * @throws CommandNotFoundException Если нет команды с таким именем
     * @throws BadCommandArgException Если аргумент не удалось присовить
     * @throws BadCommandArgsNumberException При некорректном числе переданных аргументов
     */
    ICommand getCommandByName(String[] commandWithArgs) throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException;

    /**
     * 
     * @return Отображение
     */
    IView getView();

    /**
     * Добавить отображение в стек исполнения отображений
     *
     * @param view Отображение для добавления
     */
    void addView(IView view);

    /**
     * 
     * @return Модель данных
     */
    IModel getModel();

    /**
     *
     * @param model Модель для присоединения
     */
    void setModel(IModel model);

    /**
     * 
     * @return Коллекция данных
     */
    DataStack<SpaceMarine> getCollection();

    /**
     * Начать работу с текущим представлением
     */
    void start();

    /**
     * Остановить работу с текущим представлением
     */
    void stop();

}
