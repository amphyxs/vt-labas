package Lab4.Presenter;

import Lab4.Model.IModel;
import Lab4.View.IView;

public interface IPresenter {
    void showNextData();
    IView getView();
    void setView(IView view);
    IModel getModel();
    void setModel(IModel model);
}
