package org.lab5.Presenter.Form;

/**
 * Абстрактная форма
 */
public abstract class AbstractForm<T> implements IForm<T> {
    
    protected IFormElement[] elements;
    protected String name;

    public AbstractForm(String name, IFormElement... elements) {
        this.name = name;
        this.elements = elements;
    }

    @Override
    public IFormElement[] getElements() {
        return this.elements;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
