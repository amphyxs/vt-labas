package org.lab5.Model.DataClasses;

import org.lab5.Model.Exceptions.BadIdException;

/**
 * Абстрактная сущность, имеющая поле id
 */
public abstract class EntityWithId {
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
    public void setId(int id) throws BadIdException {
        this.id = id;
    }
}
