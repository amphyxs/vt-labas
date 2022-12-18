package Lab4.View;

public class ConsoleView implements IView {
    private boolean isDataEnd = false;

    private EventHandler nextDataHandler;

    @Override
    public void bindEventHandler(IViewEvent event, EventHandler handler) {
        if (event == ConsoleViewEvent.NEXT_DATA_REQUIRED)
            nextDataHandler = handler;
        else
            System.out.printf("Unknown event: %s\n", event.getClass().getSimpleName());
    }

    @Override
    public void show() {
        while (!isDataEnd)
            nextDataHandler.handleEvent();
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
