package Lab4.View;

import java.awt.event.ActionEvent;

public interface IView {
    void showData(String type, String place, String character, String description, String[] targets);

    String getAppName();

    void notifyAboutDataEnd();

    void start();
}
