package org.lab5.Presenter.Form;


/**
 * Элемент формы. Как правило, это либо поле ({@link IField}), либо форма ({@link IForm}).
 * У любого элемента формы должно быть имя.
 */
public interface IFormElement {
    /**
     * 
     * @return Имя элемента
     */
    String getName();
}