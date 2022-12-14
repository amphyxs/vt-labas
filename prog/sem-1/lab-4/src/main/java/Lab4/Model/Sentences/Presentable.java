package Lab4.Model.Sentences;

import Lab4.Model.Interfaces.IPlace;

public interface Presentable {
    SentenceType getType();
    IPlace getPlace();
    Object getCharacter();
    String getDescription();
    Object[] getTargets();
}
