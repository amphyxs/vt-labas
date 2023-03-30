package lab6.serializedobjects.Form;

/**
 * Абстрактная форма
 */
public abstract class AbstractForm<T> implements IForm<T> {
    
    public IFormElement[] elements;
    public String name;

    
    public AbstractForm(String name, IFormElement... elements) {
        this.name = name;
        this.elements = elements;
    }

    public AbstractForm(String name) {
        this(name, new IFormElement[0]);
    }

    @Override
    public IFormElement[] getElements() {
        return this.elements;
    }

    @Override
    public void setElements(IFormElement[] elements) {
        this.elements = elements;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
