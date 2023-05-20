package lab7.serializedobjects.form;

import java.io.Serializable;

/**
 * Элемент формы. Как правило, это либо поле ({@link Field}), либо форма ({@link Form}).
 * У любого элемента формы должно быть имя.
 */
public interface FormElement extends Serializable {
    /**
     * 
     * @return Имя элемента
     */
    String getName();
}