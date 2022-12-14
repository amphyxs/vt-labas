package Lab4.Model.Exceptions;

import Lab4.Model.Interfaces.*;

public class SecrecyRevealedException extends RuntimeException {
    public SecrecyRevealedException(Hideable whoRevealed) {
        super(String.format("Внимание! %s могут приоткрыть ужасную завесу тайны и дать объяснение, казалось бы, бессвязным и непостижимым событиям.", whoRevealed.toString()));
    }
}
