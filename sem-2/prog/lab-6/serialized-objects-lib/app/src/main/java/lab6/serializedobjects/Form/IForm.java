package lab6.serializedobjects.Form;

import lab6.serializedobjects.Exceptions.ValidationFailedException;

/**
 * Интерфейс формы. Форма состоит из полей и других форм (они реализуют {@link IFormElement}).
 * Форма используется для хранения своих элементов и создания нужного объекта на основе полученных
 * её полями значений.
 */
public interface IForm<T> extends IFormElement {
    /**
     * 
     * @return Элементы формы
     */
    IFormElement[] getElements();

    void setElements(IFormElement[] elements);

    /**
     * Создать объект с помощью значений полей данной формы
     * 
     * @return Экземпляр объекта
     * @throws ValidationFailedException При неуспешной валидации значений некоторых полей
     */
    T createObject() throws ValidationFailedException;
}
