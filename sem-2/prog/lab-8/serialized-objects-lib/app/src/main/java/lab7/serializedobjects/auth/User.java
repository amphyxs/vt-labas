package lab7.serializedobjects.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import lab7.serializedobjects.exceptions.ValidationFailedException;


public class User implements Serializable {
    
    private String name;
    private String password;
    private String salt;
    private final static String PEPPER = "I-L0v3_Pr0g@";
    private final static int MIN_SALT_LENGTH = 3;
    private final static int MAX_SALT_LENGTH = 10;
    private final static String SALT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!#$%&\'()*+,-./:;\"<=>?@[\\]^_`{|}~"; 

    public User(String name, String password) throws ValidationFailedException {
        setName(name);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getHashedPassword(String algorithm) throws NoSuchAlgorithmException {
        return getHashedPassword(algorithm, generateSalt());
    }
    
    public byte[] getHashedPassword(String algorithm, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        setSalt(salt);
        String toHash = PEPPER + this.password + this.salt;
        byte[] hash;
        try {
            hash = md.digest(toHash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            hash = md.digest(toHash.getBytes());
        }
        return hash;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public static String generateSalt() {
        Random rnd = new Random();
        int saltLength = MIN_SALT_LENGTH + rnd.nextInt(MAX_SALT_LENGTH - MIN_SALT_LENGTH);
        String salt = "";
        for (int i = 0; i < saltLength; i++) {
            char rndChar = SALT_ALPHABET.charAt(rnd.nextInt(SALT_ALPHABET.length()));
            salt += rndChar;
        }
        return salt;
    }

    public void setName(String name) throws ValidationFailedException {
        checkName(name);
        this.name = name;
    }

    public void setPassword(String password) throws ValidationFailedException {
        checkPassword(password);
        this.password = password;
    }
    
    public static void checkName(String name) throws ValidationFailedException {
        if (name == null)
            throw new ValidationFailedException("Имя пользователя не должно быть null");
    }
    
    public static void checkPassword(String password) throws ValidationFailedException {
        return;
    }

    public boolean compareHashedPasswords(String algorithm, byte[] hashedPassword, String salt)
        throws NoSuchAlgorithmException {
        byte[] thisHashedPassword = getHashedPassword(algorithm, salt);
        return Arrays.equals(hashedPassword, thisHashedPassword);
    }

}
