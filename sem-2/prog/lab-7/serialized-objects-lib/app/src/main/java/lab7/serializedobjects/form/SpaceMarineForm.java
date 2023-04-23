package lab7.serializedobjects.form;

import lab7.serializedobjects.dataclasses.*;
import lab7.serializedobjects.exceptions.ValidationFailedException;


/**
 * Форма для создания объектов класса {@link SpaceMarine}
 */
public class SpaceMarineForm extends AbstractForm<SpaceMarine> {
    public class CoordinatesForm extends AbstractForm<Coordinates> {
        public Field<Double> x;
        public Field<Float> y;

        public CoordinatesForm() {
            super("координаты бойца");
            try {
                this.x = new TextField<Double>(Double.class, "координата X", Coordinates.class.getMethod("checkX", Double.class));
                this.y = new TextField<Float>(Float.class, "координата Y", Coordinates.class.getMethod("checkY", Float.class));
            } catch (NoSuchMethodException e) {
                System.out.println(e.getLocalizedMessage());
                this.x = null;
                this.y = null;
            }

            setElements(new FormElement[] { x, y });
        }

        public Coordinates createObject() throws ValidationFailedException {
            return new Coordinates(x.getValue(), y.getValue());
        }

        @Override
        public String getSimpleTypeName() {
            return "CoordinatesForm";
        }
    }
    
    public class ChapterForm extends AbstractForm<Chapter> {
        public Field<String> mainChapter;
        public Field<String> world;
        
        public ChapterForm() {
            super("отряд бойца");
            try {
                this.mainChapter = new TextField<String>(String.class, "название", Chapter.class.getMethod("checkName", String.class));
                this.world = new TextField<String>(String.class, "мир подразделения", Chapter.class.getDeclaredMethod("checkWorld", String.class));
            } catch (NoSuchMethodException e) {
                System.out.println(e.getLocalizedMessage());
                this.mainChapter = null;
                this.world = null;
            }

            setElements(new FormElement[] { mainChapter, world });
        }

        public Chapter createObject() throws ValidationFailedException {
            return new Chapter(mainChapter.getValue(), world.getValue());
        }

        @Override
        public String getSimpleTypeName() {
            return "Chapter";
        }
    }
    
    public Field<String> name;
    public CoordinatesForm coordinatesForm;
    public Field<Long> health;
    public Field<AstartesCategory> category;
    public Field<Weapon> weaponType;
    public Field<MeleeWeapon> meleeWeapon;
    public ChapterForm chapterForm;     
    public int id;
    
    public SpaceMarineForm() {
        super("космческий десантнтник");
        try {
            this.name = new TextField<String>(String.class, "имя бойца", SpaceMarine.class.getMethod("checkName", String.class));
            this.coordinatesForm = new CoordinatesForm();
            this.health = new TextField<Long>(Long.class, "HP бойца", SpaceMarine.class.getMethod("checkHealth", Long.class));
            this.category = new TextField<AstartesCategory>(AstartesCategory.class, "категория бойца", SpaceMarine.class.getMethod("checkCategory", AstartesCategory.class));
            this.weaponType = new TextField<Weapon>(Weapon.class, "огнестрельное оружие", SpaceMarine.class.getMethod("checkWeaponType", Weapon.class));
            this.meleeWeapon = new TextField<MeleeWeapon>(MeleeWeapon.class, "холодное оружие", SpaceMarine.class.getMethod("checkMeleeWeapon", MeleeWeapon.class));
            this.chapterForm = new ChapterForm();
        } catch (NoSuchMethodException e) {
            System.out.println(e.getLocalizedMessage());
            this.name = null;
            this.coordinatesForm = null;
            this.health = null;
            this.category = null;
            this.weaponType = null;
            this.meleeWeapon = null;
            this.chapterForm = null;
        }

        setElements(new FormElement[] { name, coordinatesForm, health, category, weaponType, meleeWeapon, chapterForm });
    }

    public SpaceMarineForm(int id) {
        this();
        setId(id);
    }
    
    public SpaceMarineForm(SpaceMarineForm formToCopy) throws ValidationFailedException {
        this();
        this.name.setValue(formToCopy.name.getValue());
        this.coordinatesForm.x.setValue(formToCopy.coordinatesForm.x.getValue());
        this.coordinatesForm.y.setValue(formToCopy.coordinatesForm.y.getValue());
        this.health.setValue(formToCopy.health.getValue());
        this.category.setValue(formToCopy.category.getValue());
        this.weaponType.setValue(formToCopy.weaponType.getValue());
        this.meleeWeapon.setValue(formToCopy.meleeWeapon.getValue());
        this.chapterForm.mainChapter.setValue(formToCopy.chapterForm.mainChapter.getValue());
        this.chapterForm.world.setValue(formToCopy.chapterForm.world.getValue());
    }

    public void setId(int id) {
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

    @Override
    public String getSimpleTypeName() {
        return "SpaceMarine";
    }

}
