package lab7.server.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseCredentials {
    
    private String dbUrl;
    private String username;
    private String userPassword;  // TODO: make secured

    public DatabaseCredentials(String dbUrl, String username, String password) {
        setDbUrl(dbUrl);
        setUsername(username);
        setUserPassword(password);
    }

    public DatabaseCredentials(Path configPath) throws BadConfigException {
        getCredentialsFromConfig(configPath);
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUsername() {
        return username;
    }
    
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private void getCredentialsFromConfig(Path configPath) throws BadConfigException {
        File configFile = configPath.toFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String jsonData = reader.lines().reduce((a, b) -> a.strip() + b.strip()).get();
            JSONObject jsonObject = new JSONObject(jsonData);
            String fullDbUrl = DatabaseModel.getDefaultURL(jsonObject.get("url").toString());
            setDbUrl(fullDbUrl);
            setUsername(jsonObject.get("user").toString());
            setUserPassword(jsonObject.get("password").toString());
        } catch (FileNotFoundException e) {
            throw new BadConfigException(String.format("Конфигурационный файл \"%s\" не найден", configFile));
        } catch (IOException e) {
            throw new BadConfigException(String.format("Не удалось прочитать конфигурационный файл \"%s\"", configFile));
        } catch (JSONException e) {
            throw new BadConfigException(String.format("Не удалось провести парсинг конфигурационного файла \"%s\"", configFile));
        } 
    }

}
