package Lab4.Model.Characters;

import Lab4.Model.Interfaces.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.StoryData;

public abstract class AliveCreature implements Killable, Reactionable, Remembering, Nameable {
    protected String name;
    protected String repr = "gavno";

    public AliveCreature() {
        this("");
    }

    public AliveCreature(String name) {
        this.setName(name);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setRepr(String repr) {
        this.repr = repr;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRepr() {
        return this.repr;
    }

    @Override
    public void defendSelf(IKiller enemy, IPlace where) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "защищается от", enemy);
    }

    @Override
    public void forget(String forgetAbout, IPlace where) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "забывает про", forgetAbout);
    }

    @Override
    public void recall(String recallAbout, IPlace where) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "помнит про", recallAbout);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return this.name.equals(((AliveCreature) obj).name);
    }

    @Override
    public String toString() {
        return toStringWithName();
    }
}
