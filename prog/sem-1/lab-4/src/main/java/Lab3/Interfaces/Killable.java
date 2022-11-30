package Lab3.Interfaces;

import Lab3.AbstractClasses.*;
import Lab3.Enums.DeathType;

public interface Killable {
    Corpse die(DeathType deathType);
    void defendSelf(Nameable enemy);
}
