package Lab4.Model.Objects;

import Lab4.Model.Interfaces.*;
import Lab4.Model.Enums.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.StoryData;

public class HurricaneWind implements Provokable {
    Integer power = 0;

    public void startBlowing(IPlace where) {
        // TODO: may be, it can throw unchecked exception?
        StoryData.createSentence(SentenceType.ACTION, where, this, "начинает свои первые яростные порывы");
    }

    public void setPower(int power) {
        if (0 <= power && power <= 10)
            this.power = power;
        else
            throw new RuntimeException(String.format("%s's power should be integer from 1 to 10", this.getClass().getSimpleName()));
    }

    public void frighten(Reactionable target, IPlace where) {
        provokeReactionTo(ReactionType.FEAR, target, where);
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

    @Override
    public String toString() {
        return "ураганный ветер";
    }
}
