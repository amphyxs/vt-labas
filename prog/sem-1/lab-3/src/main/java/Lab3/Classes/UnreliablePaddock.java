package Lab3.Classes;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import Lab3.Interfaces.*;

public class UnreliablePaddock {
    private final List<Dog> dogs = new ArrayList<Dog>();

    public void addDogs(Dog... dogs) {
        this.dogs.addAll(Arrays.asList(dogs));
        System.out.printf("%s открылся и в него посадили: %s\n", toString(), dogs[0].toString());
    }

    public Dog[] getDogs() {
        return this.dogs.toArray(new Dog[] {});
    }

    public boolean deleteDog(Dog dogToDel) {
        // TODO: применить паттерн "Двойной диспетчерезации (паттерн посетитель)"
        // If the dog found - deletes it, describes the story and returns true, otherwise - returns false
        if (dogs.remove(dogToDel)) {
            System.out.printf("%s разрушен и из него вырвались: %s\n", toString(), dogToDel.toString());
            return true;
        }
        return false;
    }

    public void build() {
        System.out.printf("%s пришлось городить на некотором расстоянии от лагеря\n", toString());
    }

    @Override
    public String toString() {
        return "Ненадежный загончик";
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(dogs);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass())
            return false;
        return dogs.equals(((UnreliablePaddock) obj).dogs);
    }
}
