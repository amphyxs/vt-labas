package Lab4.View;

public interface IView {
    void showData(String type, String place, String character, String description, String[] targets);

    String getAppName();

    void bindEventHandler(IViewEvent event, EventHandler handler);

    void notifyAboutDataEnd();

    void show();
}
