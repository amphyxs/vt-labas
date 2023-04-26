package lab7.serializedobjects.dataclasses;

import java.io.Serializable;

import lab7.serializedobjects.exceptions.ValidationFailedException;

/**
 * Координаты
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {

    public double x;
    public Float y; //Значение поля должно быть больше -273, Поле не может быть null

    /**
     * 
     * @param x Координата X
     * @param y Координата Y
     * @throws ValidationFailedException Если параметры не соответствуют ограничениям
     */
    public Coordinates(Double x, Float y) throws ValidationFailedException {
        setX(x);
        setY(y);
    }

    /**
     * Проверит поле x на соответствие ограничениям
     * 
     * @param x Координата X
     * @throws ValidationFailedException Если не соответствует ограничениям
     */
    public static void checkX(Double x) throws ValidationFailedException {
    }
    
    /**
     * Проверит поле y на соответствие ограничениям
     * 
     * @param y Координата Y
     * @throws ValidationFailedException Если не соответствует ограничениям
     */
    public static void checkY(Float y) throws ValidationFailedException {
        if (y == null)
            throw new ValidationFailedException("Значение координаты Y не должно быть пустым");
        if (y <= -273)
            throw new ValidationFailedException("Значение координаты Y должно быть больше -273");
    }
    
    /**
     * 
     * @param x
     * @throws ValidationFailedException Если не соответствует ограничениям
     */
    public void setX(Double x) throws ValidationFailedException {
        if (x == null)
            x = 0d;
            
        // checkX(x);
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
     * @throws ValidationFailedException Если не соответствует ограничениям
     */
    public void setY(Float y) throws ValidationFailedException {
        // checkY(y);
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
