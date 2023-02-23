package Lab4;

import Lab4.Model.IModel;
import Lab4.Model.StoryData;
import Lab4.Model.StoryModel;
import Lab4.Presenter.IPresenter;
import Lab4.Presenter.Presenter;
import Lab4.View.ConsoleView;
import Lab4.View.ConsoleViewEvent;
import Lab4.View.EventHandler;
import Lab4.View.Gui.GuiView;
import Lab4.View.Gui.GuiViewEvent;
import Lab4.View.IView;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    static class GuiStoryModule extends AbstractModule {
        @Provides
        static IModel provideModel() {
            return StoryModel.getInstance(StoryData.getSentences());
        }

        @Provides
        static IView provideView() {
            return new GuiView();
        }
    }

    static class ConsoleStoryModule extends AbstractModule {
        @Provides
        static IModel provideModel() {
            return StoryModel.getInstance(StoryData.getSentences());
        }

        @Provides
        static IView provideView() {
            return new ConsoleView();
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, MalformedURLException {
        Injector injector = Guice.createInjector(new GuiStoryModule());
        IPresenter presenter = injector.getInstance(Presenter.class);
        presenter.getView().bindEventHandler(
                GuiViewEvent.NEXT_BTN_CLICK,
                new EventHandler(presenter, presenter.getClass().getDeclaredMethod("showNextData"))
        );
        presenter.getView().show();
    }
}
