package lab6.server.Presenter.Commands;

import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.Exceptions.*;
import lab6.serializedobjects.Form.*;
import lab6.serializedobjects.MessagesType;
import lab6.serializedobjects.ServerMessage;
import java.util.List;
import java.util.ArrayList;


import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда для обновления объекта коллекции с заданным полем id
 */
public class UpdateCommand extends AbstractFormCommand {

    private int id;
    private SpaceMarineForm form = null;

    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        boolean found = false;
        
        for (SpaceMarine element : presenter.getCollection()) {
            if (element.getId() == this.id) {
                try {
                    SpaceMarine replaceMarine;
                    if (this.form == null) {
                        SpaceMarineForm form = new SpaceMarineForm(id);
                        replaceMarine = presenter.getView().readForm(form).createObject();
                    } else {
                        if (getForm() == null)
                            throw new InputEndedException();
                            
                        getForm().setId(id);
    
                        replaceMarine = getForm().createObject();
                        if (replaceMarine == null)
                            return messages;
                    }

                    element.setName(replaceMarine.getName());
                    element.setCoordinates(replaceMarine.getCoordinates());
                    element.setHealth(replaceMarine.getHealth());
                    element.setCategory(replaceMarine.getCategory());
                    element.setWeaponType(replaceMarine.getWeaponType());
                    element.setMeleeWeapon(replaceMarine.getMeleeWeapon());
                    element.setChapter(replaceMarine.getChapter());
                    found = true;
                    break;
                } catch (ValidationFailedException e) {
                    presenter.getView().showError(e.getMessage());
                    messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
                } catch (InputEndedException e) {
                    return messages;
                }
            }
        }

        if (!found) {
            String errorMessage = String.format("Не удалось найти объект с id = %d", this.id);
            presenter.getView().showError(errorMessage);
            messages.add(new ServerMessage(MessagesType.ERROR, errorMessage));
        }

        return messages;
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

    @Override
    public SpaceMarineForm getForm() {
        if (this.form == null)
            return new SpaceMarineForm();
        else
            return this.form;
    }

    @Override
    public void setForm(IForm form) {
        this.form = (SpaceMarineForm) form;
    };

}
