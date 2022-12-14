package Lab4.Presenter;

import Lab4.Model.IModel;
import Lab4.Model.Sentences.Presentable;
import Lab4.View.IView;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Presenter implements IPresenter {
    private IModel model;
    private IView view;

    public Presenter() {}

    public void setModel(IModel model) {
        this.model = model;
    }

    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void showNextData() {
        Presentable data = model.getNextData();
        if (data == null) {
            this.view.notifyAboutDataEnd();
            return;
        }
        String type = data.getType().toString();
        String place = data.getPlace() == null ? null : data.getPlace().toString();
        String character = data.getCharacter().toString();
        String description;
        switch (data.getType()) {
            case ACTION:
                description = String.format("%s", data.getDescription());
                break;
            case PHRASE:
                description = String.format("говорит: %s", capitalize(data.getDescription()));
                break;
            case REACTION:
                if (data.getTargets().length > 0)
                    description = String.format("вызывает %s у", data.getDescription());
                else
                    description = String.format("чувствует %s", data.getDescription());
                break;
            default:
                description = "";
                break;
        }
        if (data.getTargets().length > 0)
            description += ":";
        String[] targets = Arrays.stream(data.getTargets()).map(Object::toString).toArray(String[]::new);
        view.showData(type, place, character, description, targets);
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
