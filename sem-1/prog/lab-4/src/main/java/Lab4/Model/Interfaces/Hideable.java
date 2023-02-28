package Lab4.Model.Interfaces;

import Lab4.Model.Exceptions.*;
import Lab4.Model.Sentences.*;

public interface Hideable {
    default void liftVeilOfSecrecy() {
        throw new SecrecyRevealedException(this);
    }
}
