package lab8.client.view.fxapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.form.*;
import lab8.client.presenter.AppLanguage;
import lab8.client.presenter.Presenter;
import lab8.client.presenter.exceptions.InputEndedException;
import lab8.client.view.View;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;


public class GuiView implements View {

    private enum CurrentView {
        WELCOME,
        MAIN
    };

    private boolean isStopped;
    private Presenter presenter;
    private Stage mainStage;
    private ResourceBundle bundle;
    private final static int UPDATE_DELAY_SECONDS = 3;
    private ScheduledService<SpaceMarine[]> dataUpdateService;
    private MainController mainController;
    private AppLanguage appLanguage;
    private CurrentView currentView;

    public GuiView() {
        this.isStopped = false;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Presenter getPresenter() {
        return this.presenter;
    }

    @Override
    public boolean getIsScriptMode() {
        return false;
    }

    @Override
    public void start(Stage stage) {
        setAppLanguage(AppLanguage.RUSSIAN);
        this.mainStage = stage;

        try {
            if (getPresenter().getUser() == null) {
                showWelcomeView();
            } else {
                showMainView();
            }
        } catch (IOException e) {
            showError(e.getLocalizedMessage());
            stop();
        }
    }

    @Override
    public void stop() {
        this.isStopped = true;
        this.dataUpdateService.cancel();
        Platform.exit();
    }

    @Override
    public boolean checkIfStopped() {
        return this.isStopped;
    }

    @Override
    public <T> Form<T> readForm(Form<T> form) throws InputEndedException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiView.class.getResource("form-view.fxml"));
        fxmlLoader.setResources(this.bundle);
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new InputEndedException();
        }
        FormController controller = fxmlLoader.getController();
        controller.setView(this);
        Scene scene = new Scene(root);
        controller.setForm(form);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        buildForm(grid, form, 0, 0, 0);

        ScrollPane scrollPane = controller.formPane;
        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        Stage formStage = new Stage();

        formStage.setTitle(lc(form.getName(), "window_title", form.getName()));
        formStage.setResizable(false);
        formStage.setScene(scene);
        controller.setStage(formStage);
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.showAndWait();

        return controller.getIsFormFilled() ? (Form<T>) controller.getForm() : null;
    }

    private <T> void buildForm(GridPane grid, Form<T> form, int row, int depth, int inputIndex) {
        if (depth != 0) {
            Text formTitle = new Text(lc(form.getName(), "form", form.getName()));
            formTitle.setFont(Font.font(20));
            grid.add(formTitle, depth, row, 1, 1);
            row += 1;
        }
        for (FormElement el : form.getElements()) {
            if (el instanceof Field) {
                Field<?> field = (Field<?>) el;
                Label inputLabel = new Label(lc(field.getName(), "form", field.getName()));
                Node inputField;
                if (field instanceof EnumField) {
                    ObservableList<String> options = FXCollections.observableArrayList();
                    for (Object constant : field.getFieldType().getEnumConstants()) {
                        options.add(constant.toString());
                    }
                    ComboBox<String> inputCombBoxField = new ComboBox<String>(options);
                    inputCombBoxField.setMaxWidth(Double.MAX_VALUE);

                    if (field.getValue() != null) {
                        inputCombBoxField.setValue(field.getValue().toString());
                    }

                    inputField = inputCombBoxField;
                } else {
                    TextField inputTextField = new TextField();

                    if (field instanceof SecretTextField) {
                        inputTextField = new PasswordField();
                    }

                    if (field.getValue() != null) {
                        inputTextField.setText(field.getValue().toString());
                    }

                    inputField = inputTextField;
                }
                inputField.setId(String.format("input%d", inputIndex++));
                grid.add(inputLabel, 0, row, 1, 1);
                grid.add(inputField, 1, row, 1, 1);
                row += 1;
            } else if (el instanceof Form) {
                Form<?> innerForm = (Form<?>) el;
                buildForm(grid, innerForm, row + 1, depth + 1, inputIndex);
                row += innerForm.getFields().length + 2;
                inputIndex += innerForm.getFields().length;
            }
        }
    }

    @Override
    public void showResult(String result) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setTitle(lc("Result", "window_title", "result"));
        alert.setContentText(result);
        alert.show();
    }

    @Override
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(lc("Error", "window_title", "error"));
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setTitle(lc("Info", "window_title", "info"));
        alert.setContentText(message);
        alert.show();
    }

    public void showWelcomeView() throws IOException {
        this.currentView = CurrentView.WELCOME;
        FXMLLoader fxmlLoader = new FXMLLoader(GuiView.class.getResource("welcome-view.fxml"));
        fxmlLoader.setResources(this.bundle);
        Parent root = (Parent) fxmlLoader.load();
        WelcomeController controller = fxmlLoader.getController();
        controller.setView(this);
        Scene scene = new Scene(root, 320, 240);
        this.mainStage.setTitle(lc("Auth", "window_title", "auth"));
        this.mainStage.setScene(scene);
        this.mainStage.setResizable(false);
        this.mainStage.show();
    }

    public void showMainView() throws IOException {
        this.currentView = CurrentView.MAIN;
        FXMLLoader fxmlLoader = new FXMLLoader(GuiView.class.getResource("main-view.fxml"));
        fxmlLoader.setResources(this.bundle);

        Parent root = (Parent) fxmlLoader.load();

        MainController controller = fxmlLoader.getController();
        controller.setPresenter(getPresenter());
        controller.setView(this);
        controller.setStage(this.mainStage);
        controller.setUsernameLabel(getPresenter().getUser().getName());
        controller.buildMenu();
        controller.buildSpacemarinesTable();
        controller.initFilters();
        controller.buildSpacemarinesCanvas();
        controller.updateData(presenter.fetchData());

        this.mainStage.hide();
        this.mainStage = new Stage();
        this.mainStage.setTitle("Lab8");
        Scene scene = new Scene(root);
        this.mainStage.setScene(scene);
        this.mainStage.show();

        this.mainController = controller;
        startAutoUpdate();
    }

    public String lc(String ifKeyNotFound, String... keyParts) {
        String key = String.join(".", keyParts);
        if(!this.bundle.containsKey(key)) {
            System.out.printf("Отсутсвует локализация для ключа \"%s\"\n", key);
            return ifKeyNotFound;
        } else {
            return this.bundle.getString(key);
        }
    }

    public String lcDate(Date date) {
        Locale currentLocale = new Locale(getAppLanguage().getCode());
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, currentLocale);
        return df.format(date);
    }

    public String lcNumber(Number number) {
        Locale currentLocale = new Locale(getAppLanguage().getCode());
        NumberFormat nf = NumberFormat.getNumberInstance(currentLocale);
        return nf.format(number);
    }

    private void startAutoUpdate() {
        this.dataUpdateService = new ScheduledService<>() {
            @Override
            protected Task<SpaceMarine[]> createTask() {
                return new Task<>() {
                    @Override
                    protected SpaceMarine[] call() {
                        return presenter.fetchData();
                    }
                };
            }
        };
        this.dataUpdateService.setDelay(Duration.seconds(UPDATE_DELAY_SECONDS));
        this.dataUpdateService.setPeriod(Duration.seconds(UPDATE_DELAY_SECONDS));
        this.dataUpdateService.setOnSucceeded(eventHandler -> {
            if (dataUpdateService.getValue() != null) {
                this.mainController.updateData(dataUpdateService.getValue());
            }
        });
        this.dataUpdateService.start();
    }

    public void updateDataNow() {
        this.mainController.updateData(this.presenter.fetchData());
    }


    @Override
    public void setAppLanguage(AppLanguage language) {
        this.appLanguage = language;
        Locale currentLocale;
        if (language.getRegionCode() != null)
            currentLocale = new Locale(language.getCode(), language.getRegionCode());
        else
            currentLocale = new Locale(language.getCode());

        this.bundle = ResourceBundle.getBundle("bundles.locales", currentLocale);
        if (this.mainStage != null) {
            try {
                if (this.currentView == CurrentView.MAIN)
                    showMainView();
                else if (this.currentView == CurrentView.WELCOME)
                    showWelcomeView();
            } catch (IOException e) {
                showError(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public AppLanguage getAppLanguage() {
        return this.appLanguage;
    }
}
