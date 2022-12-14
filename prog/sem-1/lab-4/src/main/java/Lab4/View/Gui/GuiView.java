package Lab4.View.Gui;

import Lab4.Presenter.IPresenter;
import Lab4.View.IView;

public class GuiView implements IView {
    private final String APP_NAME = "Lab4";
    private static IView instance;
    private StartWindow startWindow;
    private MainWindow mainWindow;
    private IPresenter presenter;

    private GuiView(IPresenter presenter) {
        this.presenter = presenter;
    }

    public static IView getInstance(IPresenter presenter) {
        if (instance == null)
            instance = new GuiView(presenter);
        return instance;
    }

    @Override
    public void start() {
        this.startWindow = new StartWindow(this);
        startWindow.showWindow();
    }

    void onStartBtnClick() {
        this.startWindow.closeWindow();
        this.mainWindow = new MainWindow(this);
        this.mainWindow.showWindow();
    }

    void onNextBtnClick() {
        presenter.showNextData();
    }

    @Override
    public void showData(String type, String place, String character, String description, String[] targets) {
        mainWindow.addNewData(type, place, character, description, targets);
    }

    @Override
    public String getAppName() {
        return this.APP_NAME;
    }

    @Override
    public void notifyAboutDataEnd() {
        mainWindow.onDataEnd();
    }

}
