package Lab4.Model.Objects;

import Lab4.Model.Interfaces.*;

public class DelicateThings implements Hideable {
    Boolean isDangerous = false;

    public void setDangerous(boolean dangerous) {
        this.isDangerous = dangerous;
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


    @Override
    public String toString() {
        return "несколько деликатных вещей";
    }
}
