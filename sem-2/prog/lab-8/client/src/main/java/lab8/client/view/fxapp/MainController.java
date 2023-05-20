package lab8.client.view.fxapp;

import javafx.animation.ScaleTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab7.serializedobjects.form.*;
import lab7.serializedobjects.form.TextField;
import lab8.client.presenter.Presenter;
import lab8.client.presenter.commands.*;
import lab8.client.presenter.exceptions.BadCommandArgException;
import lab8.client.presenter.exceptions.InputEndedException;

import java.io.File;
import java.util.*;


public class MainController {

    @FXML
    public Menu collectionMenu;
    @FXML
    public Menu editMenu;
    @FXML
    public Menu toolsMenu;
    @FXML
    public TableView<SpaceMarine> spacemarinesTable;
    @FXML
    public javafx.scene.control.TextField filterTextField;
    @FXML
    public ChoiceBox<String> filterColumnSelect;
    @FXML
    public AnchorPane spacemarinesMapPane;
    public Group spacemarinesMapGroup;
    public Label usernameLabel;
    private Presenter presenter;
    private GuiView view;
    private int filteringColumnIndex;

    private final Command[] collectionCommands;
    private final Command[] editCommands;
    private final Command[] toolsCommands;
    private Integer selectedSpacemarineId;
    private ObservableList<SpaceMarine> currentData;
    private ObservableList<SpaceMarine> newSpacemarines;
    private FilteredList<SpaceMarine> filteredData;
    private static final double MIN_CANVAS_COORD_BOUND = 100d;
    private static final double SPACEMARINE_MAX_SIZE = 20d;
    private static final double SPACEMARINE_MIN_SIZE = 5d;
    private Map<String, Color> usersColors;
    private Stage stage;

    {
        this.collectionCommands = new Command[] {
                new AddCommand(),
                new ClearCommand(),
                new PrintFieldDescendingChapterCommand(),
                new ShuffleCommand(),
                new RemoveAllByHealth(),
                new RemoveFirstCommand(),
                new RemoveLastCommand()
        };
        this.editCommands = new Command[] {
                new UpdateCommand(),
                new RemoveByIdCommand(),
        };
        this.toolsCommands = new Command[] {
                new InfoCommand(),
                new HelpCommand(),
                new ExecuteScriptCommand(),
                new ShowSettingsCommand(),
                new ExitCommand()
        };

        this.usersColors = new HashMap<>();
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setView(GuiView view) {
        this.view = view;
    }

    public void setUsernameLabel(String username) {
        this.usernameLabel.setText(username);
    }

    private void setSelectedSpacemarineId(Integer selectedSpacemarineId) {
        this.selectedSpacemarineId = selectedSpacemarineId;
        boolean shouldDisableEditing = (selectedSpacemarineId == null);
        for (MenuItem item : this.editMenu.getItems())
            item.setDisable(shouldDisableEditing);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Integer getSelectedSpacemarineId() {
        return this.selectedSpacemarineId;
    }

    public void buildMenu() {
        buildMenu(collectionCommands, collectionMenu);
        buildMenu(editCommands, editMenu);
        buildMenu(toolsCommands, toolsMenu);
    }

    private void buildMenu(Command[] commands, Menu menu) {
        for (Command command : commands) {
            MenuItem menuItem = new MenuItem(view.lc(command.getName(), "command", command.getName()));
            menuItem.setOnAction(getCommandActionHandler(command));
            menu.getItems().add(menuItem);
        }
    }

    private EventHandler<ActionEvent> getCommandActionHandler(Command command) {
        EventHandler<ActionEvent> result;
        result = event -> {
            boolean isRunInAnotherThread = false;
            if (command instanceof UpdateCommand | command instanceof RemoveByIdCommand) {
                try {
                    command.setArg("id", this.selectedSpacemarineId.toString());
                } catch (BadCommandArgException e) {
                    view.showError(e.getLocalizedMessage());
                }
            } else if (command instanceof RemoveAllByHealth) {
                Field<Long> healthField = new TextField<Long>(Long.class, "health");
                Form<Long> healthForm = new AbstractForm<Long>("health_form", healthField) {
                    @Override
                    public String getSimpleTypeName() {
                        return "enter_health";
                    }

                    @Override
                    public Long createObject() throws ValidationFailedException {
                        Field<Long> healthField = (Field<Long>) getElements()[0];
                        return healthField.getValue();
                    }
                };
                try {
                    healthForm = this.view.readForm(healthForm);
                    command.setArg("health", healthForm.createObject().toString());
                } catch (InputEndedException | ValidationFailedException | BadCommandArgException e) {
                    this.view.showError(e.getLocalizedMessage());
                }
            } else if (command instanceof ExecuteScriptCommand) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(view.lc("Choose script file", "window_title", "choose_script_file"));
                File scriptFile = fileChooser.showOpenDialog(this.stage);
                if (scriptFile != null && scriptFile.isFile()) {
                    try {
                        command.setArg("file_name", scriptFile.getAbsolutePath());
                    } catch (BadCommandArgException e) {
                        this.view.showError(e.getLocalizedMessage());
                    }
                }
                else {
                    return;
                }
                isRunInAnotherThread = true;
            } else if (command instanceof ClearCommand) {
                isRunInAnotherThread = true;
            }
            if (isRunInAnotherThread) {
                new Thread(() -> {
                    command.execute(this.presenter);
                }).start();
            } else {
                command.execute(this.presenter);
                this.view.updateDataNow();
            }
        };
        return result;
    }

    public void buildSpacemarinesTable() {
        TableColumn<SpaceMarine, Integer> idColumn = new TableColumn<>(view.lc("ID","form", "id"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<SpaceMarine, String> nameColumn = new TableColumn<>(view.lc("Name","form", "name"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<SpaceMarine, String> xCoordColumn = new TableColumn<>(view.lc("X","form", "x"));
        xCoordColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(view.lcNumber(cell.getValue().getCoordinates().getX())));
        TableColumn<SpaceMarine, String> yCoordColumn = new TableColumn<>(view.lc("Y","form", "y"));
        yCoordColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(view.lcNumber(cell.getValue().getCoordinates().getY())));
        TableColumn<SpaceMarine, String> healthColumn = new TableColumn<>(view.lc("Health","form", "health"));
        healthColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(view.lcNumber(cell.getValue().getHealth())));
        TableColumn<SpaceMarine, String> categoryColumn = new TableColumn<>(view.lc("Category","form", "category"));
        categoryColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(nullStr(cell.getValue().getCategory())));
        TableColumn<SpaceMarine, String> weaponTypeColumn = new TableColumn<>(view.lc("Weapon type","form", "weapon_type"));
        weaponTypeColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(nullStr(cell.getValue().getWeaponType())));
        TableColumn<SpaceMarine, String> meleeWeaponColumn = new TableColumn<>(view.lc("Melee weapon","form", "melee_weapon"));
        meleeWeaponColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(nullStr(cell.getValue().getMeleeWeapon())));
        TableColumn<SpaceMarine, String> chapterNameColumn = new TableColumn<>(view.lc("Chapter name","form", "chapter_name"));
        chapterNameColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(nullStr(cell.getValue().getChapter().getName())));
        TableColumn<SpaceMarine, String> chapterWorldColumn = new TableColumn<>(view.lc("Chapter world","form", "chapter_world"));
        chapterWorldColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(nullStr(cell.getValue().getChapter().getWorld())));
        TableColumn<SpaceMarine, String> ownerColumn = new TableColumn<>(view.lc("Owner","form", "owner"));
        ownerColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getOwner().getName()));
        TableColumn<SpaceMarine, String> creationDateColumn = new TableColumn<>(view.lc("Creation date","form", "creation_date"));
        creationDateColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(view.lcDate(cell.getValue().getCreationDate())));  // TODO: to local string

        this.spacemarinesTable.getColumns().clear();
        this.spacemarinesTable.getColumns().addAll(
                idColumn,
                nameColumn,
                xCoordColumn,
                yCoordColumn,
                healthColumn,
                categoryColumn,
                weaponTypeColumn,
                meleeWeaponColumn,
                chapterNameColumn,
                chapterWorldColumn,
                ownerColumn,
                creationDateColumn
        );
        this.spacemarinesTable.getColumns().forEach(c -> c.setSortable(true));

        this.spacemarinesTable.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            SpaceMarine selected = this.spacemarinesTable.getSelectionModel().getSelectedItem();
            setSelectedSpacemarineId(selected == null ? null : selected.getId());
        });

        this.spacemarinesTable.setRowFactory(tv -> {
            TableRow<SpaceMarine> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
               if (event.getClickCount() >= 2 && !(row.isEmpty())) {
                   SpaceMarine rowData = row.getItem();
                   updateSelectedSpacemarine(rowData);
               }
            });
            return row;
        });
        setSelectedSpacemarineId(null);
    }

    public void updateData(SpaceMarine[] data) {
        ObservableList<SpaceMarine> newList = FXCollections.observableArrayList(data);
        this.newSpacemarines = findNewSpacemarines(this.currentData, newList);
        this.currentData = newList;
        updateUsersColors();
        filterAndUpdateData(this.filterTextField.getText());
    }

    private void updateSpacemarinesTable() {
        SortedList<SpaceMarine> sortedList = new SortedList<>(this.filteredData);
        sortedList.comparatorProperty().bind(this.spacemarinesTable.comparatorProperty());
        this.spacemarinesTable.setItems(sortedList);
        this.spacemarinesTable.refresh();
        SpaceMarine selected = this.spacemarinesTable.getSelectionModel().getSelectedItem();
        setSelectedSpacemarineId(selected == null ? null : selected.getId());
    }

    private String nullStr(Object o) {
        return o == null ? "" : o.toString();
    }

    public void initFilters() {
        this.filteringColumnIndex = 0;
        this.filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAndUpdateData(newValue);
        });
        ObservableList<String> filterColumnsList = FXCollections.observableArrayList();
        for (TableColumn<?, ?> column : this.spacemarinesTable.getColumns()) {
            filterColumnsList.add(column.getText());
        }
        this.filterColumnSelect.setItems(filterColumnsList);
        this.filterColumnSelect.getSelectionModel().select(this.filteringColumnIndex);
    }

    private void filterAndUpdateData(String filterString) {
        if (this.currentData == null)
            return;

        this.filteredData = this.currentData.filtered(sp -> {
            String value;
            int idx = this.filteringColumnIndex;
            if (idx == 0)
                value = nullStr(sp.getId());
            else if (idx == 1)
                value = nullStr(sp.getName());
            else if (idx == 2)
                value = nullStr(sp.getCoordinates().getX());
            else if (idx == 3)
                value = nullStr(sp.getCoordinates().getY());
            else if (idx == 4)
                value = nullStr(sp.getHealth());
            else if (idx == 5)
                value = nullStr(sp.getCategory());
            else if (idx == 6)
                value = nullStr(sp.getWeaponType());
            else if (idx == 7)
                value = nullStr(sp.getMeleeWeapon());
            else if (idx == 8)
                value = nullStr(sp.getChapter().getName());
            else if (idx == 9)
                value = nullStr(sp.getChapter().getWorld());
            else if (idx == 10)
                value = nullStr(sp.getOwner().getName());
            else if (idx == 11)
                value = view.lcDate(sp.getCreationDate());
            else {
                System.out.printf("Unknown filter index: %d\n", idx);
                return true;
            }
            return value.contains(filterString.trim());
        });
        updateSpacemarinesTable();
        updateSpacemarinesCanvas();
    }

    public void changeColumnFilter(ActionEvent actionEvent) {
        this.filteringColumnIndex = this.filterColumnSelect.getSelectionModel().getSelectedIndex();
        filterAndUpdateData(this.filterTextField.getText());
    }

    public void buildSpacemarinesCanvas() {
        this.spacemarinesMapPane.widthProperty().addListener(((observable, oldValue, newValue) -> {
            updateSpacemarinesCanvas();
        }));
        this.spacemarinesMapPane.heightProperty().addListener(((observable, oldValue, newValue) -> {
            updateSpacemarinesCanvas();
        }));
    }

    private void updateSpacemarinesCanvas() {
        double side = Math.min(this.spacemarinesMapPane.getWidth(), this.spacemarinesMapPane.getHeight());
        Optional<Double> maxXOptional = this.filteredData.stream().map(sp -> sp.getCoordinates().getX()).max(Double::compare);
        Optional<Float> maxYOptional = this.filteredData.stream().map(sp -> sp.getCoordinates().getY()).max(Float::compare);
        double maxX = MIN_CANVAS_COORD_BOUND, maxY = MIN_CANVAS_COORD_BOUND;
        if (maxXOptional.isPresent() && maxYOptional.isPresent()) {
            maxX = maxXOptional.get();
            maxY = maxYOptional.get();
        }

        this.spacemarinesMapGroup.setLayoutX(Math.max((this.spacemarinesMapPane.getWidth() - side) / 2, 0));
        this.spacemarinesMapGroup.getChildren().clear();
        Rectangle bgRect = new Rectangle();
        bgRect.setX(0);
        bgRect.setY(0);
        bgRect.setWidth(side);
        bgRect.setHeight(side);
        bgRect.setFill(Color.BLACK);
        this.spacemarinesMapGroup.getChildren().add(bgRect);

        for (SpaceMarine sp : this.filteredData) {
            double x = scaleCoordinate(sp.getCoordinates().getX(), maxX, side);
            double y = scaleCoordinate(sp.getCoordinates().getY(), maxY, side);
            double size = scaleSize(sp.getHealth());
            Rectangle spRect = new Rectangle();
            Color spRectColor = this.usersColors.get(sp.getOwner().getName());
            spRect.setFill(spRectColor);
            spRect.setStroke(spRectColor.brighter().brighter());
            spRect.setStrokeWidth(2);
            spRect.setX(x);
            spRect.setY(y);
            spRect.setWidth(size);
            spRect.setHeight(size);
            spRect.setCursor(Cursor.HAND);
            spRect.setOnMouseClicked(event -> {
                this.spacemarinesTable.getSelectionModel().select(sp);
                this.spacemarinesTable.scrollTo(sp);
                if (event.getClickCount() >= 2) {
                    updateSelectedSpacemarine(sp);
                }
            });
            if (newSpacemarines != null && newSpacemarines.contains(sp)) {
                ScaleTransition transition = new ScaleTransition(Duration.seconds(1), spRect);
                transition.setByX(0.5d);
                transition.setByY(0.5d);
                transition.setCycleCount(2);
                transition.setAutoReverse(true);

                transition.play();
            }
            this.spacemarinesMapGroup.getChildren().add(spRect);
        }
    }

    private double scaleCoordinate(double coord, double maxCoord, double canvasSide) {
        double c = Math.max((maxCoord * canvasSide) / (canvasSide - 25), 100);
        return (coord / c) * canvasSide;
    }

    private double scaleSize(double size) {
        return SPACEMARINE_MIN_SIZE + (size / 100d) * SPACEMARINE_MAX_SIZE;
    }

    private void updateUsersColors() {
        for (SpaceMarine sp : this.currentData) {
            String username = sp.getOwner().getName();
            if (!this.usersColors.containsKey(username)) {
                int userNumber = this.usersColors.keySet().size();
                List<Color> allColors = getNamedColors();
                Color newColor = allColors.get(userNumber % allColors.size());
                for (int i = 0; i <= userNumber / allColors.size(); i++) {
                    newColor = newColor.darker();
                }
                this.usersColors.put(username, newColor);
            }
        }
    }

    private ObservableList<SpaceMarine> findNewSpacemarines(ObservableList<SpaceMarine> oldList, ObservableList<SpaceMarine> newList) {
        return oldList == null ? newList : newList.filtered(sp -> !oldList.contains(sp));
    }

    private static List<Color> getNamedColors() {
        List<Color> colors = new ArrayList<>();

        colors.add(Color.ALICEBLUE);
        colors.add(Color.ANTIQUEWHITE);
        colors.add(Color.AQUA);
        colors.add(Color.AQUAMARINE);
        colors.add(Color.AZURE);
        colors.add(Color.BEIGE);
        colors.add(Color.BISQUE);
        colors.add(Color.BLACK);
        colors.add(Color.BLANCHEDALMOND);
        colors.add(Color.BLUE);
        colors.add(Color.BLUEVIOLET);
        colors.add(Color.BROWN);
        colors.add(Color.BURLYWOOD);
        colors.add(Color.CADETBLUE);
        colors.add(Color.CHARTREUSE);
        colors.add(Color.CHOCOLATE);
        colors.add(Color.CORAL);
        colors.add(Color.CORNFLOWERBLUE);
        colors.add(Color.CORNSILK);
        colors.add(Color.CRIMSON);
        colors.add(Color.CYAN);
        colors.add(Color.DARKBLUE);
        colors.add(Color.DARKCYAN);
        colors.add(Color.DARKGOLDENROD);
        colors.add(Color.DARKGRAY);
        colors.add(Color.DARKGREEN);
        colors.add(Color.DARKGREY);
        colors.add(Color.DARKKHAKI);
        colors.add(Color.DARKMAGENTA);
        colors.add(Color.DARKOLIVEGREEN);
        colors.add(Color.DARKORANGE);
        colors.add(Color.DARKORCHID);
        colors.add(Color.DARKRED);
        colors.add(Color.DARKSALMON);
        colors.add(Color.DARKSEAGREEN);
        colors.add(Color.DARKSLATEBLUE);
        colors.add(Color.DARKSLATEGRAY);
        colors.add(Color.DARKSLATEGREY);
        colors.add(Color.DARKTURQUOISE);
        colors.add(Color.DARKVIOLET);
        colors.add(Color.DEEPPINK);
        colors.add(Color.DEEPSKYBLUE);
        colors.add(Color.DIMGRAY);
        colors.add(Color.DIMGREY);
        colors.add(Color.DODGERBLUE);
        colors.add(Color.FIREBRICK);
        colors.add(Color.FLORALWHITE);
        colors.add(Color.FORESTGREEN);
        colors.add(Color.FUCHSIA);
        colors.add(Color.GAINSBORO);
        colors.add(Color.GHOSTWHITE);
        colors.add(Color.GOLD);
        colors.add(Color.GOLDENROD);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.GREENYELLOW);
        colors.add(Color.GREY);
        colors.add(Color.HONEYDEW);
        colors.add(Color.HOTPINK);
        colors.add(Color.INDIANRED);
        colors.add(Color.INDIGO);
        colors.add(Color.IVORY);
        colors.add(Color.KHAKI);
        colors.add(Color.LAVENDER);
        colors.add(Color.LAVENDERBLUSH);
        colors.add(Color.LAWNGREEN);
        colors.add(Color.LEMONCHIFFON);
        colors.add(Color.LIGHTBLUE);
        colors.add(Color.LIGHTCORAL);
        colors.add(Color.LIGHTCYAN);
        colors.add(Color.LIGHTGOLDENRODYELLOW);
        colors.add(Color.LIGHTGRAY);
        colors.add(Color.LIGHTGREEN);
        colors.add(Color.LIGHTGREY);
        colors.add(Color.LIGHTPINK);
        colors.add(Color.LIGHTSALMON);
        colors.add(Color.LIGHTSEAGREEN);
        colors.add(Color.LIGHTSKYBLUE);
        colors.add(Color.LIGHTSLATEGRAY);
        colors.add(Color.LIGHTSLATEGREY);
        colors.add(Color.LIGHTSTEELBLUE);
        colors.add(Color.LIGHTYELLOW);
        colors.add(Color.LIME);
        colors.add(Color.LIMEGREEN);
        colors.add(Color.LINEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.MAROON);
        colors.add(Color.MEDIUMAQUAMARINE);
        colors.add(Color.MEDIUMBLUE);
        colors.add(Color.MEDIUMORCHID);
        colors.add(Color.MEDIUMPURPLE);
        colors.add(Color.MEDIUMSEAGREEN);
        colors.add(Color.MEDIUMSLATEBLUE);
        colors.add(Color.MEDIUMSPRINGGREEN);
        colors.add(Color.MEDIUMTURQUOISE);
        colors.add(Color.MEDIUMVIOLETRED);
        colors.add(Color.MIDNIGHTBLUE);
        colors.add(Color.MINTCREAM);
        colors.add(Color.MISTYROSE);
        colors.add(Color.MOCCASIN);
        colors.add(Color.NAVAJOWHITE);
        colors.add(Color.NAVY);
        colors.add(Color.OLDLACE);
        colors.add(Color.OLIVE);
        colors.add(Color.OLIVEDRAB);
        colors.add(Color.ORANGE);
        colors.add(Color.ORANGERED);
        colors.add(Color.ORCHID);
        colors.add(Color.PALEGOLDENROD);
        colors.add(Color.PALEGREEN);
        colors.add(Color.PALETURQUOISE);
        colors.add(Color.PALEVIOLETRED);
        colors.add(Color.PAPAYAWHIP);
        colors.add(Color.PEACHPUFF);
        colors.add(Color.PERU);
        colors.add(Color.PINK);
        colors.add(Color.PLUM);
        colors.add(Color.POWDERBLUE);
        colors.add(Color.PURPLE);
        colors.add(Color.RED);
        colors.add(Color.ROSYBROWN);
        colors.add(Color.ROYALBLUE);
        colors.add(Color.SADDLEBROWN);
        colors.add(Color.SALMON);
        colors.add(Color.SANDYBROWN);
        colors.add(Color.SEAGREEN);
        colors.add(Color.SEASHELL);
        colors.add(Color.SIENNA);
        colors.add(Color.SILVER);
        colors.add(Color.SKYBLUE);
        colors.add(Color.SLATEBLUE);
        colors.add(Color.SLATEGRAY);
        colors.add(Color.SLATEGREY);
        colors.add(Color.SNOW);
        colors.add(Color.SPRINGGREEN);
        colors.add(Color.STEELBLUE);
        colors.add(Color.TAN);
        colors.add(Color.TEAL);
        colors.add(Color.THISTLE);
        colors.add(Color.TOMATO);
        colors.add(Color.TRANSPARENT);
        colors.add(Color.TURQUOISE);
        colors.add(Color.VIOLET);
        colors.add(Color.WHEAT);
        colors.add(Color.WHITE);
        colors.add(Color.WHITESMOKE);
        colors.add(Color.YELLOW);
        colors.add(Color.YELLOWGREEN);
        return colors;
    }

    private void updateSelectedSpacemarine(SpaceMarine sp) {
        UpdateCommand command = new UpdateCommand();
        try {
            command.setArg("id", String.valueOf(sp.getId()));
            command.execute(presenter);
            this.view.updateDataNow();
        } catch (BadCommandArgException e) {
            this.view.showError(e.getLocalizedMessage());
        }
    }
}
