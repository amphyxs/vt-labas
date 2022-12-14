package Lab4.Model;

import Lab4.Model.Sentences.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StoryModel implements IModel {
    private Iterator<Presentable> sourceIter;
    private static StoryModel instance;

    private StoryModel() {}

    public static StoryModel getInstance(Iterable<Presentable> i) {
        if (instance == null)
            instance = new StoryModel();
        instance.setSource(i);
        return instance;
    }

    @Override
    public void setSource(Iterable<Presentable> i) {
         this.sourceIter = i.iterator();
    }

    @Override
    public Presentable getNextData() {
        try {
            return this.sourceIter.next();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
