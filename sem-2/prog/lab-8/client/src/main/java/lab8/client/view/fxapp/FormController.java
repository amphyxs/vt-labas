package lab8.client.view.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab7.serializedobjects.form.EnumField;
import lab7.serializedobjects.form.Field;
import lab7.serializedobjects.form.Form;
import lab7.serializedobjects.form.FormElement;


public class FormController {
    public ScrollPane formPane;
    public AnchorPane rootPane;
    private Form<?> form;
    private Stage stage;
    private GuiView view;
    private boolean isFormFilled = false;

    public void setView(GuiView view) {
        this.view = view;
    }

    public void setForm(Form<?> form) {
        this.form = form;
    }

    public Form<?> getForm() {
        return this.form;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeForm(ActionEvent actionEvent) {
        this.stage.hide();  // FIXME: don't send the form if it's not filled
    }

    public boolean getIsFormFilled() {
        return this.isFormFilled;
    }

    public void submitForm(ActionEvent actionEvent) {
        try {
            fillForm(this.form, 0);
            this.isFormFilled = true;
            closeForm(actionEvent);
        } catch (ValidationFailedException e) {
            String badFieldName = this.view.lc(String.format(e.getField().getName()), "form", String.format(e.getField().getName()));
            String errorMessage = String.format("%s \"%s\"",
                    this.view.lc("Validation error on field", "exceptions", "validation_error"),
                    badFieldName
            );
            this.view.showError(errorMessage + ": " + this.view.lc(e.getLocalizedMessage(), "exceptions", e.getLocalizedMessage()));
            this.isFormFilled = false;
        }
    }

    private <T> void fillForm(Form<T> form, int inputIndex) throws ValidationFailedException {
        for (FormElement el : form.getElements()) {
            if (el instanceof Field) {
                Field<?> field = (Field<?>) el;
                Node node = this.stage.getScene().lookup(String.format("#input%d", inputIndex));
                String rawValue = "";
                if (field instanceof EnumField) {
                    ComboBox<?> fieldElement = (ComboBox<?>) node;
                    if (fieldElement.getValue() == null)
                        rawValue = null;
                    else
                        rawValue = fieldElement.getValue().toString();
                } else if (node instanceof TextField) {
                    TextField fieldElement = (TextField) node;
                    rawValue = fieldElement.getText();
                    if (rawValue.isBlank())
                        rawValue = null;
                }
                field.setRawValue(rawValue);
                inputIndex += 1;
            } else if (el instanceof Form) {
                Form<?> innerForm = (Form<?>) el;
                fillForm(innerForm, inputIndex);
                inputIndex += innerForm.getFields().length;
            }
        }
    }

}
