package lab6.client.Presenter.Commands;

import lab6.client.Presenter.Exceptions.*;


/**
 * Команда вывода значений всех полей chapter объектов коллекции, отсортировав по убыванию
 */
public class PrintFieldDescendingChapterCommand extends AbstractNetworkCommand {
    
    @Override
    public String getName() {
        return "print_field_descending_chapter";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля chapter всех элементов в порядке убывания";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
