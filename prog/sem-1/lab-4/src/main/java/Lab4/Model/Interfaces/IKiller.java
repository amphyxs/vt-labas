package Lab4.Model.Interfaces;

import Lab4.Model.Objects.Corpse;

public interface IKiller {
    Corpse kill(Killable target, IPlace where);
}
