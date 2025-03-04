package lab6.server.Model;

import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Date;
import lab6.serializedobjects.DataClasses.*;
import lab6.serializedobjects.Exceptions.*;

import lab6.server.Presenter.IPresenter;

/**
 * Модель данных для их хранения в одном файле JSON
 */
public class JsonModel implements IModel {

    final private String ENV_VAR = "LAB6_DATA_PATH";
    
    private IPresenter presenter;
    
    public JsonModel() {
        this.presenter = null;
    }
    
    /**
     * 
     * @param presenter Предсавление, которое необходимо присоединить к модели
     */
    public JsonModel(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public IPresenter getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public SpaceMarine[] loadData() throws LoadFailedException {
        List<SpaceMarine> result = new ArrayList<SpaceMarine>();
        
        try {
            String jsonData = readJson();
            JSONArray array = new JSONArray(jsonData);

            for (Object element : array) {
                JSONObject obj;
                obj = (JSONObject) element;
                result.add(parseJsonObject(obj)); 
            }
        } catch (ClassCastException | JSONException | ParseException | ValidationFailedException | BadIdException e) {
            throw new LoadFailedException("Проблема при парсинге JSON");
        }

        return result.toArray(new SpaceMarine[result.size()]);
    }

    @Override
    public String saveData(SpaceMarine[] data) throws SaveFailedException {
        JSONArray array = new JSONArray();
        if (data != null && data.length > 0) {
            for (SpaceMarine element : data)
                array.put(convertToJson(element));
        }
        
        String jsonData = array.toString();
        writeJson(jsonData);
        return getFilePath();
    }

    private JSONObject convertToJson(SpaceMarine sp) {
        JSONObject obj = new JSONObject();

        obj.put("id", sp.getId());
        obj.put("name", sp.getName());
        obj.put("coordinates", new JSONObject()
                                        .put("x", sp.getCoordinates().getX())
                                        .put("y", sp.getCoordinates().getY())
        );
        obj.put("creationDate", IModel.getDefaultDateFormat().format(sp.getCreationDate()));
        obj.put("health", sp.getHealth());
        obj.put("category", sp.getCategory());
        obj.put("weaponType", sp.getWeaponType() == null ? JSONObject.NULL : sp.getWeaponType());
        obj.put("meleeWeapon", sp.getMeleeWeapon() == null ? JSONObject.NULL : sp.getMeleeWeapon());
        obj.put("chapter", new JSONObject()
                                        .put("name", sp.getChapter().getName())
                                        .put("world", sp.getChapter().getWorld() == null ? JSONObject.NULL : sp.getChapter().getWorld())
        );

        return obj;
    }

    private SpaceMarine parseJsonObject(JSONObject obj) throws ParseException, ValidationFailedException, BadIdException {
        int id = obj.getInt("id");
        String name = obj.getString("name");
        Double x = obj.getJSONObject("coordinates").getDouble("x");
        Float y = obj.getJSONObject("coordinates").getFloat("y");
        Coordinates coordinates = new Coordinates(x, y);
        Date creationDate = IModel.getDefaultDateFormat().parse(obj.getString("creationDate"));
        Long health = obj.getLong("health");
        AstartesCategory category = obj.getEnum(AstartesCategory.class, "category");
        Weapon weaponType = obj.get("weaponType") == JSONObject.NULL ? null : obj.getEnum(Weapon.class, "weaponType");
        MeleeWeapon meleeWeapon = obj.get("meleeWeapon") == JSONObject.NULL ? null : obj.getEnum(MeleeWeapon.class, "meleeWeapon");
        String chapterName = obj.getJSONObject("chapter").getString("name");
        String chapterWorld = obj.getJSONObject("chapter").get("world") == JSONObject.NULL ? null : obj.getJSONObject("chapter").getString("world");
        Chapter chapter = new Chapter(chapterName, chapterWorld);

        SpaceMarine parsedObj = new SpaceMarine(id, name, coordinates, health, category, weaponType, meleeWeapon, chapter);
        parsedObj.setCreationDate(creationDate);
        return parsedObj;
    }

    private String readJson() throws LoadFailedException {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFileForReading()))) {
            String jsonData = reader.lines().reduce((a, b) -> a.strip() + b.strip()).get();
            return jsonData;
        } catch (FileNotFoundException e) {
            // TODO: использовать File для проверки прав и существования
            throw new LoadFailedException(String.format("Невозможно получить доступ к файлу \"%s\" (не найден или недостаточно прав)", getFilePath()));
        } catch (IOException | NoSuchElementException e) {
            throw new LoadFailedException("Не удалось прочитать файл");
        }
    }

    private void writeJson(String jsonData) throws SaveFailedException {
        try (FileOutputStream fs = new FileOutputStream(getFileForWriting())) {
            fs.write(jsonData.getBytes());
            fs.flush();
        } catch (FileNotFoundException e) {
            throw new SaveFailedException(String.format("Невозможно получить доступ к файлу \"%s\" (не найден или недостаточно прав)", getFilePath()));
        } catch (IOException e) {
            throw new SaveFailedException("Не удалось записать файл");
        }
    }

    private String getFilePath() {
        String result = System.getenv(this.ENV_VAR);
        if (result == null)
            result = Paths.get(System.getProperty("user.dir"), "spacemarines-data.json").toAbsolutePath().toString();

        return result;
    }

    private File getFileForReading() throws LoadFailedException {
        File f = new File(getFilePath());
        if (!f.exists() | !f.isFile())
            throw new LoadFailedException(String.format("Файл \"%s\" не найден", getFilePath()));
        if (!f.canRead())
            throw new LoadFailedException(String.format("Недостаточно прав для чтения файла \"%s\"", getFilePath()));
        return f;
    }

    private File getFileForWriting() throws SaveFailedException {
        File f = new File(getFilePath());
        if (!f.exists() | !f.isFile())
            throw new SaveFailedException(String.format("Файл \"%s\" не найден", getFilePath()));
        if (!f.canWrite())
            throw new SaveFailedException(String.format("Недостаточно прав для записи файла \"%s\"", getFilePath()));
        return f;
    }

}
