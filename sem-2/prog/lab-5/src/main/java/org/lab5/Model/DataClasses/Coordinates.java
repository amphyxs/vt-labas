package org.lab5.Model.DataClasses;

import org.lab5.Model.Exceptions.UserInputException;

/**
 * Координаты
 */
public class Coordinates implements Comparable<Coordinates> {

    private double x;
    private Float y; //Значение поля должно быть больше -273, Поле не может быть null

    /**
     * 
     * @param x Координата X
     * @param y Координата Y
     * @throws UserInputException Если параметры не соответствуют ограничениям
     */
    public Coordinates(Double x, Float y) throws UserInputException {
        setX(x);
        setY(y);
    }

    /**
     * Проверит поле x на соответствие ограничениям
     * 
     * @param x Координата X
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkX(Double x) throws UserInputException {
    }
    
    /**
     * Проверит поле y на соответствие ограничениям
     * 
     * @param x Координата Y
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkY(Float y) throws UserInputException {
        if (y == null)
            throw new UserInputException("Значение координаты Y не должно быть пустым");
        if (y <= -273)
            throw new UserInputException("Значение координаты Y должно быть больше -273");
    }
    
    /**
     * 
     * @param x
     * @throws UserInputException Если не соответствует ограничениям
     */
    public void setX(Double x) throws UserInputException {
        if (x == null)
            x = 0d;
            
        checkX(x);
        this.x = x;
    }

    /**
     * 
     * @return Координата X
     */
    public double getX() {
        return this.x;
    }

    /**
     * 
     * @return Координата Y
     */
    public Float getY() {
        return this.y;
    }

    /**
     * 
     * @param y Координата Y
     * @throws UserInputException Если не соответствует ограничениям
     */
    public void setY(Float y) throws UserInputException {
        checkY(y);
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%f; %f)", this.x, this.y);
    }

    /**
     * Сранить координаты сначала по X. Если они равны, то по Y.
     * 
     * @param o
     * @return
     */
    @Override
    public int compareTo(Coordinates o) {
        if (o.getX() == this.x) {
            return this.y.compareTo(o.getY());
        } else {
            return (int) Math.floor(this.x - o.getX());
        }
    }
}
