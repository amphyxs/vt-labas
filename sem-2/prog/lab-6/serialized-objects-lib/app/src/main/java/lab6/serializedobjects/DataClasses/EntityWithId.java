package lab6.serializedobjects.DataClasses;

import java.io.Serializable;

import lab6.serializedobjects.Exceptions.BadIdException;
import lab6.serializedobjects.Exceptions.ValidationFailedException;

/**
 * Абстрактная сущность, имеющая поле id
 */
public abstract class EntityWithId implements Serializable {
    protected int id;

    /**
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    };

    /**
     * 
     * @param id id
     * @throws BadIdException Если id не удовлетворяет ограничениям
     */
    public void setId(int id) throws ValidationFailedException {
        this.id = id;
    }
}
