package Lab4;

import Lab4.Model.IModel;
import Lab4.Model.StoryData;
import Lab4.Model.StoryModel;
import Lab4.Presenter.IPresenter;
import Lab4.Presenter.Presenter;
import Lab4.View.Gui.GuiView;
import Lab4.View.IView;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import java.lang.annotation.Retention;
import javax.inject.Inject;
import javax.inject.Qualifier;

public class Main {
    public static void main(String[] args) {
        IPresenter presenter = new Presenter();
        IModel model = StoryModel.getInstance(StoryData.getSentences());
        presenter.setModel(model);
        IView view = GuiView.getInstance(presenter);
        presenter.setView(view);
        view.start();
    }
}
