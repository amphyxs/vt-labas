package Lab3.Classes;

import Lab3.Interfaces.*;

public class DelicateThings implements Hideable {
    // Singleton
    private static DelicateThings instance;

    private DelicateThings() { }

    public static DelicateThings getInstance() {
        if (instance == null)
            instance = new DelicateThings();
        return instance;
    }

    @Override
    public String toString() {
        return "Несколько деликатных вещей";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }
}
