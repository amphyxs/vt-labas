package Lab4.View;

import Lab4.Presenter.IPresenter;
import Lab4.View.Gui.GuiView;

public class ConsoleView implements IView {
    private static IView instance;

    private boolean isDataEnd = false;

    private IPresenter presenter;

    private ConsoleView(IPresenter presenter) {
        this.presenter = presenter;
    }

    public static IView getInstance(IPresenter presenter) {
        if (instance == null)
            instance = new ConsoleView(presenter);
        return instance;
    }

    @Override
    public void start() {
        while (!isDataEnd)
            presenter.showNextData();
    }

    @Override
    public void showData(String type, String place, String character, String description, String[] targets) {
        String targetsStr = String.join(", ", targets);
        System.out.printf("%s %s %s\n", character, description, targetsStr);
    }

    @Override
    public String getAppName() {
        return null;
    }

    @Override
    public void notifyAboutDataEnd() {
        this.isDataEnd = true;
    }
}
