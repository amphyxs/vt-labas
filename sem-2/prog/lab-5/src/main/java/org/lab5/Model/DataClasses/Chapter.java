package org.lab5.Model.DataClasses;

import org.lab5.Model.Exceptions.UserInputException;

/**
 * Подразделения солдат
 */
public class Chapter implements Comparable<Chapter> {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private String world; //Поле может быть null

    /**
     * 
     * 
     * @param name Название подразделения
     * @param world Название мира, которому принадлежит подразделение
     * @throws UserInputException Если параметры не соответствуют ограничениям
     */
    public Chapter(String name, String world) throws UserInputException{
        setName(name);
        setWorld(world);
    }

    /**
     * Проверит поле name на соответствие ограничениям
     * 
     * @param name Название подразделения
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkName(String name) throws UserInputException {
        if (name == null || name.isBlank())
            throw new UserInputException("Имя подразделения не должно быть пустым");
    }

    /**
     * 
     * @param name Название подразделения
     * @throws UserInputException
     */
    public void setName(String name) throws UserInputException {
        checkName(name);
        this.name = name;
    }

    /**
     * 
     * @return Название подразделения
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @param world Название мира подразделения
     * @throws UserInputException
     */
    public  void setWorld(String world) throws UserInputException {
        checkWorld(world);
        this.world = world;
    }

    /**
     * 
     * @return Название мира подразделения
     */
    public String getWorld() {
        return this.world;
    }

    /**
     * Проверит поле name на соответствие ограничениям
     * 
     * @param world Название подразделения
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkWorld(String world) throws UserInputException {

    }

    @Override
    public String toString() {
        return String.format("\"%s\" из мира \"%s\"", this.name, this.world);
    }

    /**
     * Сравнить два Chapter по имени
     */
    @Override
    public int compareTo(Chapter o) {
        return getName().compareTo(o.getName());
    }

}
