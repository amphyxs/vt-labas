package org.lab5.Presenter.Commands;

import org.lab5.Model.DataClasses.SpaceMarine;
import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;
import org.lab5.Model.Exceptions.*;


public class UpdateCommand implements ICommand {

    private int id;

    @Override
    public void execute(IPresenter presenter) {
        boolean found = false;
        
        for (SpaceMarine element : presenter.getCollection()) {
            if (element.getId() == this.id) {
                try {
                    SpaceMarine replaceMarine = AddCommand.readSpaceMarine(this.id, presenter.getView());
                    element.setName(replaceMarine.getName());
                    element.setCoordinates(replaceMarine.getCoordinates());
                    element.setHealth(replaceMarine.getHealth());
                    element.setCategory(replaceMarine.getCategory());
                    element.setWeaponType(replaceMarine.getWeaponType());
                    element.setMeleeWeapon(replaceMarine.getMeleeWeapon());
                    element.setChapter(replaceMarine.getChapter());
                    found = true;
                    break;
                } catch (NoSuchMethodException | UserInputException | BadIdException e) {
                    presenter.getView().showError(e.getMessage());
                }
            }
        }

        if (!found)
            presenter.getView().showError(String.format("Не удалось найти объект с id = %d", this.id));
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"id"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        try {
            switch (argName) {
                case "id":
                    this.id = Integer.parseInt(valueString);
                    break;
            }
        } catch (NumberFormatException e) {
            throw new BadCommandArgException(getName(), argName, Integer.class.getSimpleName());
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "id":
                return this.id;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}
