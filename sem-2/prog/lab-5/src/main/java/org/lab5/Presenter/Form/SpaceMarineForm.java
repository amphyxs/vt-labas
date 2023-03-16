package org.lab5.Presenter.Form;

import org.lab5.Model.DataClasses.AstartesCategory;
import org.lab5.Model.DataClasses.Chapter;
import org.lab5.Model.DataClasses.Coordinates;
import org.lab5.Model.DataClasses.MeleeWeapon;
import org.lab5.Model.DataClasses.SpaceMarine;
import org.lab5.Model.DataClasses.Weapon;
import org.lab5.Model.Exceptions.ValidationFailedException;


/**
 * Форма для создания объектов класса {@link SpaceMarine}
 */
public class SpaceMarineForm extends AbstractForm<SpaceMarine> {
    private static class CoordinatesForm extends AbstractForm<Coordinates> {
        private static IField<Double> x;
        private static IField<Float> y;

        static {
            try {
                x = new Field<Double>(Double.class, "координата X", Coordinates.class.getMethod("checkX", Double.class));
                y = new Field<Float>(Float.class, "координата Y", Coordinates.class.getMethod("checkY", Float.class));
            } catch (NoSuchMethodException e) {
                System.out.println(e.getLocalizedMessage());
                x = null;
                y = null;
            }
        }
        
        public CoordinatesForm() {
            super(
                "координаты бойца",
                x,
                y
            );
        }

        public Coordinates createObject() throws ValidationFailedException {
            return new Coordinates(x.getValue(), y.getValue());
        }
    }
    
    private static class ChapterForm extends AbstractForm<Chapter> {
        private static IField<String> mainChapter;
        private static IField<String> world;
        
        static {
            try {
                mainChapter = new Field<String>(String.class, "название", Chapter.class.getMethod("checkName", String.class));
                world = new Field<String>(String.class, "мир подразделения", Chapter.class.getDeclaredMethod("checkWorld", String.class));
            } catch (NoSuchMethodException e) {
                System.out.println(e.getLocalizedMessage());
                mainChapter = null;
                world = null;
            }
        }

        public ChapterForm() {
            super(
                "отряд бойца",
                mainChapter,
                world
            );
        }

        public Chapter createObject() throws ValidationFailedException {
            return new Chapter(mainChapter.getValue(), world.getValue());
        }
    }
    
    private static IField<String> name;
    private static IForm<Coordinates> coordinatesForm;
    private static IField<Long> health;
    private static IField<AstartesCategory> category;
    private static IField<Weapon> weaponType;
    private static IField<MeleeWeapon> meleeWeapon;
    private static IForm<Chapter> chapterForm;     
    
    private int id;

    static {
        try {
            name = new Field<String>(String.class, "имя бойца", SpaceMarine.class.getMethod("checkName", String.class));
            coordinatesForm = new CoordinatesForm();
            health = new Field<Long>(Long.class, "HP бойца", SpaceMarine.class.getMethod("checkHealth", Long.class));
            category = new Field<AstartesCategory>(AstartesCategory.class, "категория бойца", SpaceMarine.class.getMethod("checkCategory", AstartesCategory.class));
            weaponType = new Field<Weapon>(Weapon.class, "огнестрельное оружие", SpaceMarine.class.getMethod("checkWeaponType", Weapon.class));
            meleeWeapon = new Field<MeleeWeapon>(MeleeWeapon.class, "холодное оружие", SpaceMarine.class.getMethod("checkMeleeWeapon", MeleeWeapon.class));
            chapterForm = new ChapterForm();
        } catch (NoSuchMethodException e) {
            System.out.println(e.getLocalizedMessage());
            name = null;
            coordinatesForm = null;
            health = null;
            category = null;
            weaponType = null;
            meleeWeapon = null;
            chapterForm = null;
        }
    }
    
    public SpaceMarineForm(int id) {
        super(
            "космический десантник", 
            name,
            coordinatesForm,
            health,
            category,
            weaponType,
            meleeWeapon,
            chapterForm
        );
        this.id = id;
    }

    public SpaceMarine createObject() throws ValidationFailedException {
        return new SpaceMarine(
            this.id,
            name.getValue(),
            coordinatesForm.createObject(),
            health.getValue(),
            category.getValue(),
            weaponType.getValue(),
            meleeWeapon.getValue(),
            chapterForm.createObject()
        );
    }
}
