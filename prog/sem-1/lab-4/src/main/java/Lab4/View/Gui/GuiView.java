package Lab4.View.Gui;

import Lab4.View.EventHandler;
import Lab4.View.IView;
import Lab4.View.IViewEvent;

import java.util.HashMap;
import java.util.Map;

public class GuiView implements IView {
    private final String APP_NAME = "Lab4";
    private static IView instance;
    private StartWindow startWindow;
    private MainWindow mainWindow;
    private Map<IViewEvent, EventHandler> eventBindings;

    public GuiView() {
        this.eventBindings = new HashMap<IViewEvent, EventHandler>();
    }

    private void launchExternalHandler(GuiViewEvent ev) {
        if (this.eventBindings.containsKey(ev))
            this.eventBindings.get(ev).handleEvent();
    }

    void onStartBtnClick() {
        launchExternalHandler(GuiViewEvent.START_BTN_CLICK);
        this.startWindow.closeWindow();
        this.mainWindow = new MainWindow(this);
        this.mainWindow.showWindow();
    }

    void onNextBtnClick() {
        this.eventBindings.get(GuiViewEvent.NEXT_BTN_CLICK).handleEvent();
    }

    @Override
    public void bindEventHandler(IViewEvent event, EventHandler handler) {
        this.eventBindings.put(event, handler);
    }

    @Override
    public void show() {
        this.startWindow = new StartWindow(this);
        startWindow.showWindow();
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
