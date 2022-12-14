package Lab4.Model.Interfaces;

public interface Nameable {
    String getRepr();

    String getName();

    void setName(String name);

    void setRepr(String repr);

    default String toStringWithName() {
        if (getName().length() > 0)
            return getRepr() + " " + getName();
        else
            return getRepr();
    };
}
