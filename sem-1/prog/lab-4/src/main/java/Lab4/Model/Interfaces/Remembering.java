package Lab4.Model.Interfaces;

public interface Remembering {
    void forget(String forgetAbout, IPlace where);

    void recall(String recallAbout, IPlace where);
}
