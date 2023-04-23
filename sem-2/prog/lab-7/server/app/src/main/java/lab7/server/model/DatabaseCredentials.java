package lab7.server.model;


public class DatabaseCredentials {
    
    private String dbUrl;
    private String username;
    private String userPassword;  // TODO: make secured

    public DatabaseCredentials(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.userPassword = password;
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

}
