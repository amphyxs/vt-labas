package Lab4.Presenter;

import Lab4.Model.IModel;
import Lab4.Model.Sentences.Presentable;
import Lab4.View.IView;

public interface IPresenter {
    void setModel(IModel model);
    void setView(IView view);
    void showNextData();
}
