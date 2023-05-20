package lab8.client.presenter;

import lab7.serializedobjects.auth.User;

import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab8.client.presenter.commands.Command;
import lab8.client.presenter.exceptions.*;
import lab8.client.view.View;


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
    Command getCommandByName(String[] commandWithArgs) throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException;

    /**
     * 
     * @return Отображение
     */
    View getView();

    User getUser();

    void setUser(User user);

    /**
     * Добавить отображение в стек исполнения отображений
     *
     * @param view Отображение для добавления
     */
    void addView(View view);

    /**
     * Начать работу с текущим представлением
     */
    void start();

    Client getClient();

    boolean connectToHost();

    void processCommand(View view, Command command);

    SpaceMarine[] fetchData();

}
