package org.lab5.Presenter.Commands;

import org.lab5.Model.DataClasses.*;
import org.lab5.Model.Exceptions.*;
import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.CommandArgNotFound;
import org.lab5.View.IView;

public class AddCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        IView view = presenter.getView();
        SpaceMarine marine = null;

        try {
            int id = presenter.getCollection().generateUniqueId();
            marine = readSpaceMarine(id, view);
        } catch (NoSuchMethodException | UserInputException | BadIdException e) {
            view.showError(e.getMessage());
        }

        presenter.getCollection().push(marine);
    }

    static SpaceMarine readSpaceMarine(Integer id, IView view) throws NoSuchMethodException, UserInputException, BadIdException {
        String name = view.readSimpleField("имя бойца", SpaceMarine.class.getMethod("checkName", String.class), String.class, -1);
        Double x = view.readSimpleField("координату X", Coordinates.class.getMethod("checkX", Double.class), Double.class, 0);
        Float y = view.readSimpleField("координату Y", Coordinates.class.getMethod("checkY", Float.class), Float.class, 0);
        Coordinates coordinates = new Coordinates(x, y);
        Long health = view.readSimpleField("HP бойца", SpaceMarine.class.getMethod("checkHealth", Long.class), Long.class, 0);
        AstartesCategory category = view.readEnumConstant("категорию бойца", SpaceMarine.class.getMethod("checkCategory", AstartesCategory.class), AstartesCategory.class, 0);
        Weapon weaponType = view.readEnumConstant("огнестрельное оружие", SpaceMarine.class.getMethod("checkWeaponType", Weapon.class), Weapon.class, 0);
        MeleeWeapon meleeWeapon = view.readEnumConstant("холодное оружие", SpaceMarine.class.getMethod("checkMeleeWeapon", MeleeWeapon.class), MeleeWeapon.class, 0);
        String mainChapter = view.readSimpleField("подразделение бойца", Chapter.class.getMethod("checkName", String.class), String.class, 0);
        String world = view.readSimpleField("название мира подразделения", Chapter.class.getMethod("checkWorld", String.class), String.class, 1);
        Chapter chapter = new Chapter(mainChapter, world);

        return new SpaceMarine(id, name, coordinates, health, category, weaponType, meleeWeapon, chapter);
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
