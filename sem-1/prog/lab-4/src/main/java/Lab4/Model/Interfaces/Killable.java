package Lab4.Model.Interfaces;

import Lab4.Model.Enums.*;
import Lab4.Model.Objects.*;

public interface Killable {
    Corpse die(DeathType deathType);
    void defendSelf(IKiller enemy, IPlace where);
}
