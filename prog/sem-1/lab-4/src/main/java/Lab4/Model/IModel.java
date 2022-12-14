package Lab4.Model;

import Lab4.Model.Sentences.Presentable;

public interface IModel {
    void setSource(Iterable<Presentable> src);
    Presentable getNextData();
}
