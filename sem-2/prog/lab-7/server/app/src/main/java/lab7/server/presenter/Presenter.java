package lab7.server.presenter;

import lab7.serializedobjects.SerializedCommand;
import lab7.serializedobjects.dataclasses.SpaceMarine;

import lab7.server.model.Model;
import lab7.server.presenter.collection.DataStack;
import lab7.server.presenter.commands.Command;
import lab7.server.presenter.exceptions.*;
import lab7.server.view.View;


/**
 * Интерфейс представления
 */
public interface Presenter {
    
    /**
     * @return Все доступные команды
     */
    Command[] getCommands();

    /**
     * 
     * @param commandWithArgs Имя команды и аргументы в виде отдельных строк
     * @return Команда с этим именем и присвоенными аргументами
     * @throws CommandNotFoundException Если нет команды с таким именем
     * @throws BadCommandArgException Если аргумент не удалось присовить
     * @throws BadCommandArgsNumberException При некорректном числе переданных аргументов
     */
    Command getCommandByName(String[] commandWithArgs) 
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException;

    Command getCommandByName(SerializedCommand clientsCommand) 
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException, CommandArgNotFound;

    /**
     * 
     * @return Отображение
     */
    View getView();

    /**
     * Добавить отображение в стек исполнения отображений
     *
     * @param view Отображение для добавления
     */
    void addView(View view);

    /**
     * 
     * @return Модель данных
     */
    Model getModel();

    /**
     *
     * @param model Модель для присоединения
     */
    void setModel(Model model);

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

    void fullStop();

    Object processClientsObject(Object obj) throws UnsupportedObjectReceivedException, InputEndedException;

}
