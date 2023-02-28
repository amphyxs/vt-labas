package Lab3.Classes;

import Lab3.Enums.*;
import Lab3.Interfaces.*;

public class HurricaneWind implements Provokable {
    Integer power = 0;

    public void startBlowing() {
        System.out.printf("%s начинает свои первые яростные порывы\n", toString());
    }

    public void setPower(int power) {
        if (0 <= power && power <= 10)
            this.power = power;
        else
            throw new RuntimeException("HurricaneWind's power should be integer from 1 to 10");
    }

    public void frighten(Reactionable d) {
        Provokable.provokeReaction(d, ReactionType.FEAR);
        Provokable.describeReaction(d, ReactionType.FEAR, toString(), true);
    }

    @Override
    public String toString() {
        return "Ураганный ветер";
    }

    @Override
    public int hashCode() {
        return power.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return power.equals(((HurricaneWind) obj).power);
    }
}
