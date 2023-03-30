package lab6.serializedobjects.Form;

import lab6.serializedobjects.DataClasses.*;
import lab6.serializedobjects.Exceptions.ValidationFailedException;


/**
 * Форма для создания объектов класса {@link SpaceMarine}
 */
public class SpaceMarineForm extends AbstractForm<SpaceMarine> {
    public class CoordinatesForm extends AbstractForm<Coordinates> {
        public IField<Double> x;
        public IField<Float> y;

        public CoordinatesForm() {
            super("координаты бойца");
            try {
                this.x = new Field<Double>(Double.class, "координата X", Coordinates.class.getMethod("checkX", Double.class));
                this.y = new Field<Float>(Float.class, "координата Y", Coordinates.class.getMethod("checkY", Float.class));
            } catch (NoSuchMethodException e) {
                System.out.println(e.getLocalizedMessage());
                this.x = null;
                this.y = null;
            }

            setElements(new IFormElement[] { x, y });
        }

        public Coordinates createObject() throws ValidationFailedException {
            return new Coordinates(x.getValue(), y.getValue());
        }
    }
    
    public class ChapterForm extends AbstractForm<Chapter> {
        public IField<String> mainChapter;
        public IField<String> world;
        
        public ChapterForm() {
            super("отряд бойца");
            try {
                this.mainChapter = new Field<String>(String.class, "название", Chapter.class.getMethod("checkName", String.class));
                this.world = new Field<String>(String.class, "мир подразделения", Chapter.class.getDeclaredMethod("checkWorld", String.class));
            } catch (NoSuchMethodException e) {
                System.out.println(e.getLocalizedMessage());
                this.mainChapter = null;
                this.world = null;
            }

            setElements(new IFormElement[] { mainChapter, world });
        }

        public Chapter createObject() throws ValidationFailedException {
            return new Chapter(mainChapter.getValue(), world.getValue());
        }
    }
    
    public IField<String> name;
    public IForm<Coordinates> coordinatesForm;
    public IField<Long> health;
    public IField<AstartesCategory> category;
    public IField<Weapon> weaponType;
    public IField<MeleeWeapon> meleeWeapon;
    public IForm<Chapter> chapterForm;     
    public int id;
    
    public SpaceMarineForm() {
        super("космческий десантнтник");
        try {
            this.name = new Field<String>(String.class, "имя бойца", SpaceMarine.class.getMethod("checkName", String.class));
            this.coordinatesForm = new CoordinatesForm();
            this.health = new Field<Long>(Long.class, "HP бойца", SpaceMarine.class.getMethod("checkHealth", Long.class));
            this.category = new Field<AstartesCategory>(AstartesCategory.class, "категория бойца", SpaceMarine.class.getMethod("checkCategory", AstartesCategory.class));
            this.weaponType = new Field<Weapon>(Weapon.class, "огнестрельное оружие", SpaceMarine.class.getMethod("checkWeaponType", Weapon.class));
            this.meleeWeapon = new Field<MeleeWeapon>(MeleeWeapon.class, "холодное оружие", SpaceMarine.class.getMethod("checkMeleeWeapon", MeleeWeapon.class));
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

        setElements(new IFormElement[] { name, coordinatesForm, health, category, weaponType, meleeWeapon, chapterForm });
    }

    public SpaceMarineForm(int id) {
        this();
        setId(id);
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
}
