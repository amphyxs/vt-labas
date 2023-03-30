package lab6.serializedobjects.Form;

import java.io.Serializable;

/**
 * Элемент формы. Как правило, это либо поле ({@link IField}), либо форма ({@link IForm}).
 * У любого элемента формы должно быть имя.
 */
public interface IFormElement extends Serializable {
    /**
     * 
     * @return Имя элемента
     */
    String getName();
}