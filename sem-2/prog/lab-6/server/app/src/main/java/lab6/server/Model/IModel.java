package lab6.server.Model;

import java.text.DateFormat;
import java.util.Locale;

import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.Exceptions.LoadFailedException;
import lab6.serializedobjects.Exceptions.SaveFailedException;
import lab6.server.Presenter.IPresenter;

/**
 * Интерфейс модели данных
 */
public interface IModel {

    /**
     * Сохранить набор данных
     * 
     * @param data Набор данных из объектов SpaceMarine
     * @return В случае успеха путь к данным или какую-то информацию
     * @throws SaveFailedException Если не удалось сохранить
     */
    String saveData(SpaceMarine[] data) throws SaveFailedException;

    /**
     * Загрузить набор данных
     * 
     * @return Набор данных из объектов SpaceMarine
     * @throws LoadFailedException Если не удалось загрузить
     */
    SpaceMarine[] loadData() throws LoadFailedException;

    /**
     * 
     * @return Привязанное представление
     */
    IPresenter getPresenter();

    /**
     * 
     * @param presenter Представление для привязки
     */
    void setPresenter(IPresenter presenter);

    /**
     * Полуить формат дат по умолчанию
     * 
     * @return Формат дат
     */
    static DateFormat getDefaultDateFormat() {
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, new Locale("ru"));
    };

}