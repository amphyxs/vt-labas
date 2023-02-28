package Lab4.Model.Interfaces;

public interface IPrison extends IPlace {
    void addPrisoner(Prisonable prisoner);
    void removePrisoner(Prisonable prisoner);
}
