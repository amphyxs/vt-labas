package org.lab5;

import org.lab5.Model.IModel;
import org.lab5.Model.JsonModel;
import org.lab5.Presenter.CommandsPresenter;
import org.lab5.Presenter.IPresenter;
import org.lab5.View.ConsoleView;
import org.lab5.View.IView;

/**
 * Точка запуска приложения
 */
public class Main {
    public static void main(String[] args) {
        IView view = new ConsoleView();
        IModel model = new JsonModel();
        IPresenter presenter = new CommandsPresenter(view, model);
        model.setPresenter(presenter);

        presenter.start();
    }
}