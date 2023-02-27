package org.lab5.Model.DataClasses;

import org.lab5.Model.Exceptions.UserInputException;

import org.lab5.Model.IModel;
import org.lab5.Model.Exceptions.BadIdException;


/**
 * Космический десантник, основной объект данных
 * 
 */
public class SpaceMarine extends EntityWithId implements Comparable<SpaceMarine> {

    // id: Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long health; //Поле не может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле не может быть null
    private Weapon weaponType; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле может быть null
    private Chapter chapter; //Поле не может быть null

    /**
     * 
     * @param id id
     * @param name Имя
     * @param coordinates Координаты
     * @param health Здоровье
     * @param category Категория бойца
     * @param weaponType Тип огнестрельного оружия
     * @param meleeWeapon Тип холодного оружия
     * @param chapter Подразделение
     * @throws UserInputException Если параметры не соответствуют огрничениям
     * @throws BadIdException Если id не соответствует огрничениям
     */
    public SpaceMarine(
            int id, String name, Coordinates coordinates, Long health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter
    ) throws UserInputException, BadIdException {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setHealth(health);
        setCategory(category);
        setWeaponType(weaponType);
        setMeleeWeapon(meleeWeapon);
        setChapter(chapter);
        generateCreationDate();
    }

    public static void checkId(int id) throws BadIdException {
        if (id <= 0)
            throw new BadIdException("id меньше или равно 0");
    }

    public static void checkName(String name) throws UserInputException {
        if (name == null || name.isEmpty())
            throw new UserInputException("Имя не должно быть пустым");
    }

    public static void checkCoordinates(Coordinates coordinates) throws UserInputException {
        if (coordinates == null)
            throw new UserInputException("Значение координат не должно быть пустым");
    }

    public static void checkHealth(Long health) throws UserInputException {
        if (health == null)
            throw new UserInputException("Значение здоровья не должно быть пустым");
        if (health <= 0)
            throw new UserInputException("Значение здоровья должно быть больше 0");
    }

    public static void checkCategory(AstartesCategory category) throws UserInputException {
        if (category == null)
            throw new UserInputException("Значение категории бойца не должно быть пустым");
    }

    public static void checkWeaponType(Weapon weaponType) throws UserInputException {
        
    }

    public static void checkMeleeWeapon(MeleeWeapon meleeWeapon) throws UserInputException {

    }

    public static void checkChapter(Chapter chapter) throws UserInputException {
        if (chapter == null)
            throw new UserInputException("Подразделение бойца не должно быть пустым");
    }

    public void setId(int id) throws BadIdException {
        checkId(id);
        super.setId(id);
    }

    public void setName(String name) throws UserInputException {
        checkName(name);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCoordinates(Coordinates coordinates) throws UserInputException {
        checkCoordinates(coordinates);
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setHealth(Long health) throws UserInputException {
        checkHealth(health);
        this.health = health;
    }

    public Long getHealth() {
        return this.health;
    }

    public void setCategory(AstartesCategory category) throws UserInputException {
        checkCategory(category);
        this.category = category;
    }

    public AstartesCategory getCategory() {
        return this.category;
    }

    public void setWeaponType(Weapon weaponType) throws UserInputException {
        checkWeaponType(weaponType);
        this.weaponType = weaponType;
    }

    public Weapon getWeaponType() {
        return this.weaponType;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) throws UserInputException {
        checkMeleeWeapon(meleeWeapon);
        this.meleeWeapon = meleeWeapon;
    }

    public MeleeWeapon getMeleeWeapon() {
        return this.meleeWeapon;
    }

    public void setChapter(Chapter chapter) throws UserInputException {
        checkChapter(chapter);
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return this.chapter;
    }

    public void setCreationDate(java.util.Date creationDate) {
        this.creationDate = creationDate;
    }

    public java.util.Date getCreationDate() {
        return this.creationDate;
    }
    
    private void generateCreationDate() {
        this.creationDate = new java.util.Date();
    }

    @Override
    public String toString() {
        String creationDateString = IModel.getDefaultDateFormat().format(this.creationDate);
        return String.format("ID: %d\n" +
                        "Имя бойца: %s\n" +
                        "Текущие координаты: %s\n" +
                        "Дата создания: %s\n" +
                        "Здоровье: %d\n" +
                        "Категория бойца: %s\n" +
                        "Огнестрельное оружие: %s\n" +
                        "Холодное оружие: %s\n" +
                        "Подразделение бойца: %s",
                this.id, this.name, this.coordinates, creationDateString, this.health, this.category, this.weaponType, this.meleeWeapon, this.chapter
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            SpaceMarine convObj = (SpaceMarine) obj;
            return convObj.getId() == getId();
        } else {
            return false;
        }
    }

    /**
     * Сравнить двух SpaceMarine сначала по здоровью, если равны, то по координатам, если опять равны, то по имени, если снова равны, то по id
     * 
     * @param o
     * @return
     */
    @Override
    public int compareTo(SpaceMarine o) {
        if (o.getHealth().equals(this.health)) {
            if (o.getCoordinates().equals(this.coordinates)) {
                if (o.getName().equals(this.name)) {
                    return this.id - o.getId();
                } else {
                    return this.name.compareTo(o.getName());
                }
            } else {
                return this.coordinates.compareTo(o.getCoordinates());
            }
        } else {
            return this.health.compareTo(o.health);
        }
    }
    
}
