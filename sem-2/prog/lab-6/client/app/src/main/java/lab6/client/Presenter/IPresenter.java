package lab6.client.Presenter;

import lab6.client.Presenter.Commands.ICommand;
import lab6.client.Presenter.Exceptions.*;
import lab6.client.View.IView;


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
     * Начать работу с текущим представлением
     */
    void start();

    /**
     * Остановить работу с текущим представлением
     */
    void stop();

    IClient getClient();

    boolean connectToHost();

}
