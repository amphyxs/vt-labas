package Lab3.Classes;

import Lab3.Interfaces.*;

public class DelicateThings implements Hideable {
    Boolean isDangerous = false;

    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    @Override
    public String toString() {
        return "Несколько деликатных вещей";
    }

    @Override
    public int hashCode() {
        return isDangerous.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return isDangerous.equals(((DelicateThings) obj).isDangerous);
    }
}
