package lab7.serializedobjects.dataclasses;

import java.io.Serializable;

import lab7.serializedobjects.exceptions.ValidationFailedException;

/**
 * Подразделения солдат
 */
public class Chapter implements Comparable<Chapter>, Serializable {

    public String name; //Поле не может быть null, Строка не может быть пустой
    public String world; //Поле может быть null

    /**
     * 
     * 
     * @param name Название подразделения
     * @param world Название мира, которому принадлежит подразделение
     * @throws ValidationFailedException Если параметры не соответствуют ограничениям
     */
    public Chapter(String name, String world) throws ValidationFailedException{
        setName(name);
        setWorld(world);
    }

    /**
     * Проверит поле name на соответствие ограничениям
     * 
     * @param name Название подразделения
     * @throws ValidationFailedException Если не соответствует ограничениям
     */
    public static void checkName(String name) throws ValidationFailedException {
        if (name == null || name.isBlank())
            throw new ValidationFailedException("Имя подразделения не должно быть пустым");
    }

    /**
     * 
     * @param name Название подразделения
     * @throws ValidationFailedException
     */
    public void setName(String name) throws ValidationFailedException {
        // checkName(name);
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
     * @throws ValidationFailedException
     */
    public  void setWorld(String world) throws ValidationFailedException {
        // checkWorld(world);
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
     * @throws ValidationFailedException Если не соответствует ограничениям
     */
    public static void checkWorld(String world) throws ValidationFailedException {

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
