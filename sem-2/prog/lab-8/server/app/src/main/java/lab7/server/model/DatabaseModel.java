package lab7.server.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.postgresql.ds.PGSimpleDataSource;

import lab7.serializedobjects.auth.RootUser;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.dataclasses.*;
import lab7.serializedobjects.exceptions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import lab7.server.presenter.Presenter;


public class DatabaseModel implements Model {
    
    private Presenter presenter;
    private Connection dbConnection;
    private DatabaseCredentials credentials;
    private static String HASH_ALGORITHM = "SHA-512";

    public DatabaseModel(Presenter presenter, DatabaseCredentials credentials) throws SQLException {
        setPresenter(presenter);
        this.credentials = credentials;
        connectDB();
    }

    public DatabaseModel() throws SQLException {
        this(null, restoreCredentials());
    }

    public DatabaseModel(DatabaseCredentials credentials) throws SQLException {
        this(null, credentials);
    }
    
    @Override
    public Presenter getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
    
    private void connectDB() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(this.credentials.getDbUrl());
        ds.setUser(this.credentials.getUsername());
        ds.setPassword(this.credentials.getUserPassword());
        this.dbConnection = ds.getConnection();
    }

    private static DatabaseCredentials restoreCredentials() {
        // TODO
        return null;
    }

    @Override
    public SpaceMarine[] loadData() throws LoadFailedException {
        try (ResultSet resultSet = queryDataLoad()) {
            List<SpaceMarine> result = new ArrayList<SpaceMarine>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Coordinates coordinates = new Coordinates(resultSet.getDouble("x"), resultSet.getFloat("y"));
                java.util.Date creationDate = resultSet.getTimestamp("creation_date");
                Long health = resultSet.getLong("health");
                AstartesCategory category = AstartesCategory.class.getEnumConstants()[resultSet.getInt("category")];
                Weapon weaponType = Weapon.class.getEnumConstants()[resultSet.getInt("weapon_type")];
                MeleeWeapon meleeWeapon = MeleeWeapon.class.getEnumConstants()[resultSet.getInt("melee_weapon")];
                Chapter chapter = new Chapter(resultSet.getString("chapter_name"), resultSet.getString("chapter_world"));

                User owner;
                ResultSet userResultSet = queryUserLoad(resultSet.getInt("owner_id"));
                if (userResultSet.next()) {
                    String userName = userResultSet.getString("login");
                    if (userName == null)
                        owner = new RootUser();
                    else 
                        owner = new User(userName, null);
                } else {
                    owner = null;
                }

                SpaceMarine sp = new SpaceMarine(id, name, coordinates, health, category, weaponType, meleeWeapon, chapter, owner);
                sp.setCreationDate(creationDate);
                sp.setId(id);  // TODO: why we set id in constructor and here?
                result.add(sp);
            }

            SpaceMarine[] arrayResult = new SpaceMarine[result.size()];
            return result.toArray(arrayResult);
        } catch (SQLException | ValidationFailedException e) {
            throw new LoadFailedException(e.getLocalizedMessage());
        }
    }

    private ResultSet queryDataLoad() throws SQLException {
        String sql = String.join("\n",
            "SELECT sp.id id,",
            "sp.name name,", 
            "coords.x x,",
            "coords.y y,",
            "sp.creation_date creation_date,",
            "sp.health health,",
            "sp.category_id category,",
            "sp.weapon_type_id weapon_type,",
            "sp.melee_weapon_id melee_weapon,",
            "chaps.name chapter_name,",
            "chaps.world chapter_world,",
            "sp.owner_id",
            "FROM lab7_spacemarines sp",
            "JOIN lab7_coordinates coords ON coords.id = sp.coordinates_id",
            "JOIN lab7_chapters chaps ON chaps.id = sp.chapter_id"
        );
        Statement st = this.dbConnection.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        return resultSet;
    }

    private ResultSet queryUserLoad(int userId) throws SQLException {
        String sql = String.join("\n",
            "SELECT *",
            "FROM lab7_users",
            "WHERE id = ?"
        );
        PreparedStatement ps = this.dbConnection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet result = ps.executeQuery();
        return result;
    }

    @Override
    public SaveInfo saveData(SpaceMarine[] data, User executor) throws SaveFailedException {
        SaveInfo resultInfo = new SaveInfo();
        try {
            List<SpaceMarine> dataList = Arrays.asList(data);
            for (SpaceMarine stored : loadData()) {
                if (!dataList.contains(stored)) {
                    SaveInfo info;
                    try {
                        info = removeObject(stored, executor);
                    } catch (NotEnoughRightsExceptions e) {
                        continue;  // TODO: notify user that he hadn't rights to this object
                    }
                    resultInfo.addRemovedId(info.getRemovedIds()[0]);
                }
            }
            for (SpaceMarine sp : data) {
                try {
                    checkUserRights(sp, executor);
                } catch (NotEnoughRightsExceptions e) {
                    continue;  // TODO: notify user that he hadn't rights to this object
                }
                SaveInfo info = upsertObject(sp);
                resultInfo.addSavedId(info.getSavedIds()[0]);
            }
        } catch (SQLException | LoadFailedException | ObjectNotFoundException e) {
            throw new SaveFailedException(e.getLocalizedMessage());
        }
        return resultInfo.setSaveLocation(toString());
    }

    private SaveInfo upsertObject(SpaceMarine obj) throws SQLException {
        String sql = String.join("\n",
            "INSERT INTO lab7_spacemarines (id, name, coordinates_id, creation_date, health, category_id, weapon_type_id, melee_weapon_id, chapter_id)",
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            "ON CONFLICT (id)",
            "DO UPDATE SET name = EXCLUDED.name,",
            "coordinates_id = EXCLUDED.coordinates_id,",
            "creation_date = EXCLUDED.creation_date,",
            "health = EXCLUDED.health,",
            "category_id = EXCLUDED.category_id,",
            "weapon_type_id = EXCLUDED.weapon_type_id,",
            "melee_weapon_id = EXCLUDED.melee_weapon_id,",
            "chapter_id = EXCLUDED.chapter_id"   
        );

        SaveInfo resultInfo = new SaveInfo();
        
        try (PreparedStatement ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int coordinatesId = appendCoordinates(obj.getCoordinates()).getSavedIds()[0];  // TODO: make upsert
            int chapterId = appendChapter(obj.getChapter()).getSavedIds()[0];

            ps.setInt(1, obj.getId());
            ps.setString(2, obj.getName());
            ps.setInt(3, coordinatesId);
            ps.setTimestamp(4, new java.sql.Timestamp(obj.getCreationDate().getTime()));
            ps.setLong(5, obj.getHealth());
            ps.setInt(6, obj.getCategory().ordinal());
            if (obj.getWeaponType() == null)
                ps.setNull(7, Types.INTEGER);
            else
                ps.setInt(7, obj.getWeaponType().ordinal());
            if (obj.getMeleeWeapon() == null)
                ps.setNull(8, Types.INTEGER);
            else
                ps.setInt(8, obj.getMeleeWeapon().ordinal());
            ps.setInt(9, chapterId);

            ps.execute();
            try(ResultSet insertedObject = ps.getGeneratedKeys()) {
                if (insertedObject.next())
                    resultInfo.addSavedId(insertedObject.getInt("id"));
            }
        }

        return resultInfo;
    }

    @Override
    public SaveInfo appendObject(SpaceMarine object, User owner) throws SaveFailedException {        
        try {
            int coordinatesId = appendCoordinates(object.getCoordinates()).getSavedIds()[0];
            int chapterId = appendChapter(object.getChapter()).getSavedIds()[0];
            return queryObjectAppend(object, coordinatesId, chapterId, getUserId(owner));
        } catch (SQLException | LoadFailedException e) {
            throw new SaveFailedException(e.getLocalizedMessage());
        }
    }

    private SaveInfo appendCoordinates(Coordinates coordinates) throws SQLException {
        String sql = String.join("\n",
            "INSERT INTO lab7_coordinates (x, y)",
            "VALUES (?, ?)"
        );
        SaveInfo resultInfo = new SaveInfo();
        try(PreparedStatement ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, coordinates.getX());
            ps.setFloat(2, coordinates.getY());
            ps.execute();
            try(ResultSet insertedCoordinates = ps.getGeneratedKeys()) {
                if (insertedCoordinates.next())
                    resultInfo.addSavedId(insertedCoordinates.getInt("id"));
            }
        }
        return resultInfo;
    }
    
    private SaveInfo appendChapter(Chapter chapter) throws SQLException {
        String sql = String.join("\n",
            "INSERT INTO lab7_chapters (name, world)",
            "VALUES (?, ?)"
        );
        SaveInfo resultInfo = new SaveInfo();
        try(PreparedStatement ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, chapter.getName());
            ps.setString(2, chapter.getWorld());
            ps.execute();
            try(ResultSet insertedChapter = ps.getGeneratedKeys()) {
                if (insertedChapter.next())
                    resultInfo.addSavedId(insertedChapter.getInt("id"));
            }
        };
        return resultInfo;
    }

    private SaveInfo queryObjectAppend(SpaceMarine obj, int coordinatesId, int chapterId, int ownerId) throws SQLException {
        String sql = String.join("\n",
            "INSERT INTO lab7_spacemarines (name, coordinates_id, creation_date, health, category_id, weapon_type_id, melee_weapon_id, chapter_id, owner_id)",
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        SaveInfo resultInfo = new SaveInfo();
        try(PreparedStatement ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, obj.getName());
            ps.setInt(2, coordinatesId);
            ps.setTimestamp(3, new java.sql.Timestamp(obj.getCreationDate().getTime()));
            ps.setLong(4, obj.getHealth());
            ps.setInt(5, obj.getCategory().ordinal());
            if (obj.getWeaponType() == null)
                ps.setNull(6, Types.INTEGER);
            else
                ps.setInt(6, obj.getWeaponType().ordinal());
            if (obj.getMeleeWeapon() == null)
                ps.setNull(7, Types.INTEGER);
            else
                ps.setInt(7, obj.getMeleeWeapon().ordinal());
            ps.setInt(8, chapterId);
            ps.setInt(9, ownerId);
            ps.execute();
            try(ResultSet insertedObject = ps.getGeneratedKeys()) {
                if (insertedObject.next())
                    resultInfo.addSavedId(insertedObject.getInt("id"));
            }
        }
        return resultInfo;
    }

    @Override
    public SaveInfo updateObject(SpaceMarine updatedObject, User executor)
        throws SaveFailedException, ObjectNotFoundException, NotEnoughRightsExceptions {
        try {
            checkUserRights(updatedObject, executor);
            int coordinatesId = updateCoordinates(updatedObject.getCoordinates()).getSavedIds()[0];  // TODO: check owner
            int chapterId = updateChapter(updatedObject.getChapter()).getSavedIds()[0];
            SaveInfo info = queryObjectUpdate(updatedObject, coordinatesId, chapterId);

            if (info.getSavedIds().length != 1)
                throw new ObjectNotFoundException(updatedObject);
                
            return info;
        } catch (SQLException | LoadFailedException e) {
            throw new SaveFailedException(e.getLocalizedMessage());
        }
    }

    private SaveInfo updateCoordinates(Coordinates coordinates) throws SQLException {
        return appendCoordinates(coordinates);   // TODO: make it another way
    }

    private SaveInfo updateChapter(Chapter chapter) throws SQLException {
        return appendChapter(chapter);   // TODO: make it another way
    }

    private SaveInfo queryObjectUpdate(SpaceMarine obj, int coordinatesId, int chapterId) throws SQLException {
        String sql = String.join("\n",
            "UPDATE lab7_spacemarines",
            "SET name = ?,",
            "coordinates_id = ?,",
            "creation_date = ?,",
            "health = ?,",
            "category_id = ?,",
            "weapon_type_id = ?,",
            "melee_weapon_id = ?,",
            "chapter_id = ?",
            "WHERE id = ?"
        );
        SaveInfo resultInfo = new SaveInfo();
        try(PreparedStatement ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, obj.getName());
            ps.setInt(2, coordinatesId);
            ps.setTimestamp(3, new java.sql.Timestamp(obj.getCreationDate().getTime()));
            ps.setLong(4, obj.getHealth());
            ps.setInt(5, obj.getCategory().ordinal());
            if (obj.getWeaponType() == null)
                ps.setNull(6, Types.INTEGER);
            else
                ps.setInt(6, obj.getWeaponType().ordinal());
            if (obj.getMeleeWeapon() == null)
                ps.setNull(7, Types.INTEGER);
            else
                ps.setInt(7, obj.getMeleeWeapon().ordinal());
            ps.setInt(8, chapterId);
            ps.setInt(9, obj.getId());
            boolean res = ps.execute();
            if (res == false && ps.getUpdateCount() == 1)
                resultInfo.addSavedId(obj.getId());
        }
        return resultInfo;
    }

    @Override
    public SaveInfo removeObject(SpaceMarine objectToRemove, User executor) 
        throws SaveFailedException, ObjectNotFoundException, NotEnoughRightsExceptions {
        String sql = String.join("\n",
            "DELETE FROM lab7_spacemarines",
            "WHERE id = ?"
        );
        SaveInfo resultInfo = new SaveInfo();
        try (PreparedStatement ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            checkUserRights(objectToRemove, executor);
            ps.setInt(1, objectToRemove.getId());
            boolean isResultSet = ps.execute();
            if (!isResultSet && ps.getUpdateCount() == 1)
                resultInfo.addRemovedId(objectToRemove.getId());
        
        } catch(SQLException | LoadFailedException e) {
            throw new SaveFailedException(e.getLocalizedMessage());
        }
        return resultInfo;
    }

    @Override
    public SaveInfo onExit(SpaceMarine[] data) throws SaveFailedException {
        return new SaveInfo();
    }

    @Override
    public SaveInfo createNewUser(User user) throws SaveFailedException {
        String sql = String.join("\n",
            "INSERT INTO lab7_users (login, password, salt)",
            "VALUES (?, ?, ?)"
        );
        SaveInfo resultInfo = new SaveInfo();
        try (PreparedStatement ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setBytes(2, user.getHashedPassword(HASH_ALGORITHM));  // TODO: hash it
            ps.setString(3, user.getSalt());
            ps.execute();
            try(ResultSet insertedUser = ps.getGeneratedKeys()) {
                if (insertedUser.next())
                    resultInfo.addSavedId(insertedUser.getInt("id"));
            }
        } catch (SQLException | NoSuchAlgorithmException e) {  // TODO: make user already exists exception
            throw new SaveFailedException(e.getLocalizedMessage());
        }

        return resultInfo;
    }

    @Override
    public boolean loginUser(User user) throws LoadFailedException {
        String sql = String.join("\n",
            "SELECT password, salt",
            "FROM lab7_users",
            "WHERE login = ?"
        );
        try (PreparedStatement ps = this.dbConnection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    byte[] hashedPassword = resultSet.getBytes("password");
                    String salt = resultSet.getString("salt"); 
                    boolean isEquals = user.compareHashedPasswords(HASH_ALGORITHM, hashedPassword, salt);
                    if (isEquals)
                        return true;
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new LoadFailedException(e.getLocalizedMessage());
        }

        return false;
    }

    private void checkUserRights(SpaceMarine obj, User user) throws LoadFailedException, NotEnoughRightsExceptions {
        if (user instanceof RootUser)
            return;
        
        String sql = String.join("\n",
            "SELECT owner_id",
            "FROM lab7_spacemarines",
            "WHERE id = ?"
        );
        boolean hasRights = false;
        try (PreparedStatement ps = this.dbConnection.prepareStatement(sql)) {
            ps.setInt(1, obj.getId());
            try (ResultSet resultSet = ps.executeQuery()) {
                int userId = getUserId(user);
                while (resultSet.next()) {
                    int ownerId = resultSet.getInt("owner_id");
                    if (ownerId == userId) {
                        hasRights = true; 
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new LoadFailedException(e.getLocalizedMessage());
        }

        if (!hasRights)
            throw new NotEnoughRightsExceptions();
    } 

    private int getUserId(User user) throws LoadFailedException {
        String sql = String.join("\n", 
            "SELECT id",
            "FROM lab7_users",
            "WHERE login = ?"
        );
        int id = -1;
        try (PreparedStatement ps = this.dbConnection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new LoadFailedException(e.getLocalizedMessage());
        }
        return id;
    }

    public static String getDefaultURL(String url) {
        return String.format("jdbc:postgresql://%s", url);
    }

    @Override   
    public String toString() {
        return "БД";
    }

}
