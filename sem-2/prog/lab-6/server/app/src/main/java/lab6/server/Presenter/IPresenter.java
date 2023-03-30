package lab6.server.Presenter;

import java.util.List;
import lab6.serializedobjects.SerializedCommand;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.Form.IForm;

import lab6.server.Presenter.Commands.ICommand;
import lab6.server.Presenter.Exceptions.*;
import lab6.server.View.IView;
import lab6.server.Model.IModel;

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
    ICommand getCommandByName(String[] commandWithArgs) 
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException;

    ICommand getCommandByName(SerializedCommand clientsCommand) 
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException, CommandArgNotFound;

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

    Object processClientsObject(Object obj) throws UnsupportedObjectReceivedException, InputEndedException;

}
