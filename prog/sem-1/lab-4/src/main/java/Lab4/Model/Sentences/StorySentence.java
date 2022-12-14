package Lab4.Model.Sentences;

import Lab4.Model.Interfaces.*;

public class StorySentence implements Presentable {
    private SentenceType type;
    private IPlace place;
    private Object character;
    private String description;
    private Object[] targets;

    public StorySentence(SentenceType type, IPlace place, Object character, String description, Object[] targets) {
        this.type = type;
        this.place = place;
        this.character = character;
        this.description = description;
        this.targets = targets;
    }

    @Override
    public SentenceType getType() {
        return type;
    }

    @Override
    public IPlace getPlace() {
        return place;
    }

    @Override
    public Object getCharacter() {
        return character;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Object[] getTargets() {
        return targets;
    }
}
