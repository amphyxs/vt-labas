package lab6.server.Presenter;

import java.util.List;
import java.time.LocalDateTime;

/**
 * Интерфейс коллекции данных с некоторым необходимым функционалом
 */
public interface IDataCollection {
    
    /**
     * 
     * @return Тип коллекции 
     */
    String getTypeName();

    /**
     * 
     * @return Дата инициализации
     */
    LocalDateTime getInitDate();

    /**
     * 
     * @return Дата модификации
     */
    LocalDateTime getModificationDate();

    /**
     * 
     * @return Количество элементов
     */
    int getSize(); 

    /**
     * Сгенерировать уникальный id
     * 
     * @return id
     */
    int generateUniqueId();

    /**
     * Получить все занятые id
     * 
     * @return Список из всех занятых id
     */
    List<Integer> getAllIds();

}
