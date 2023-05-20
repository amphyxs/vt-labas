package lab7.serializedobjects.dataclasses;

import java.io.Serializable;

import lab7.serializedobjects.exceptions.BadIdException;
import lab7.serializedobjects.exceptions.ValidationFailedException;

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
