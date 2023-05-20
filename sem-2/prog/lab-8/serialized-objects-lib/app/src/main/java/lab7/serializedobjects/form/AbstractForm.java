package lab7.serializedobjects.form;

import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Абстрактная форма
 */
public abstract class AbstractForm<T> implements Form<T> {
    
    private FormElement[] elements;
    private String name;

    
    public AbstractForm(String name, FormElement... elements) {
        this.name = name;
        this.elements = elements;
    }

    public AbstractForm(String name) {
        this(name, new FormElement[0]);
    }

    @Override
    public FormElement[] getElements() {
        return this.elements;
    }

    @Override
    public void setElements(FormElement[] elements) {
        this.elements = elements;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Field<?>[] getFields() {
        List<Field<?>> result = new ArrayList<Field<?>>();
        for (FormElement el : getElements()) {
            if (el instanceof Field) {
                result.add((Field<?>) el);
            } else if (el instanceof Form) {
                Form<?> nestedForm = (Form<?>) el;
                result.addAll(Arrays.asList(nestedForm.getFields()));
            }
        }
        Field<?>[] arrayRepresentation = new Field<?>[result.size()];
        return result.toArray(arrayRepresentation);
    }

}
