package lab7.server.model;

import java.text.DateFormat;
import java.util.Locale;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.exceptions.LoadFailedException;
import lab7.serializedobjects.exceptions.SaveFailedException;
import lab7.serializedobjects.auth.User;

import lab7.server.presenter.Presenter;

/**
 * Интерфейс модели данных
 */
public interface Model {

    /**
     * Сохранить набор данных
     * 
     * @param data Набор данных из объектов SpaceMarine
     * @return В случае успеха путь к данным или какую-то информацию
     * @throws SaveFailedException Если не удалось сохранить
     */
    SaveInfo saveData(SpaceMarine[] data, User executor) throws SaveFailedException;

    /**
     * Загрузить набор данных
     * 
     * @return Набор данных из объектов SpaceMarine
     * @throws LoadFailedException Если не удалось загрузить
     */
    SpaceMarine[] loadData() throws LoadFailedException;

    SaveInfo appendObject(SpaceMarine object, User owner) throws SaveFailedException;

    SaveInfo updateObject(SpaceMarine updatedObject, User executor) throws SaveFailedException, ObjectNotFoundException, NotEnoughRightsExceptions;

    SaveInfo removeObject(SpaceMarine objectToRemove, User executor) throws SaveFailedException, ObjectNotFoundException, NotEnoughRightsExceptions;

    SaveInfo onExit(SpaceMarine[] data) throws SaveFailedException;

    SaveInfo createNewUser(User user) throws SaveFailedException;

    boolean loginUser(User user) throws LoadFailedException;

    /**
     * 
     * @return Привязанное представление
     */
    Presenter getPresenter();

    /**
     * 
     * @param presenter Представление для привязки
     */
    void setPresenter(Presenter presenter);

    /**
     * Полуить формат дат по умолчанию
     * 
     * @return Формат дат
     */
    static DateFormat getDefaultDateFormat() {
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, new Locale("ru"));
    };

}