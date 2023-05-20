package lab7.server.model;

import lab7.serializedobjects.dataclasses.EntityWithId;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(EntityWithId object) {
        super(String.format("Объект коллекции с id = %d не найден", object.getId()));
    }
}
