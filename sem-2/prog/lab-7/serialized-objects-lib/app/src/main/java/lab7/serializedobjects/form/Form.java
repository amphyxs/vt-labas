package lab7.serializedobjects.form;

import lab7.serializedobjects.exceptions.ValidationFailedException;

/**
 * Интерфейс формы. Форма состоит из полей и других форм (они реализуют {@link FormElement}).
 * Форма используется для хранения своих элементов и создания нужного объекта на основе полученных
 * её полями значений.
 */
public interface Form<T> extends FormElement {
    /**
     * 
     * @return Элементы формы
     */
    FormElement[] getElements();

    void setElements(FormElement[] elements);

    String getSimpleTypeName();

    /**
     * Создать объект с помощью значений полей данной формы
     * 
     * @return Экземпляр объекта
     * @throws ValidationFailedException При неуспешной валидации значений некоторых полей
     */
    T createObject() throws ValidationFailedException;

    Field<?>[] getFields();
}
