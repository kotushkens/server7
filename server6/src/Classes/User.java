package Classes;

import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
    private static final long serialVersionUID = 32L;

    private static final String SALT = "gosp@d1_p0m0gi";

    private int id;
    private String login;
    private String password;
    private boolean hashed = false;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHashed() {
        return hashed;
    }

    private String getHashedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] hashedBytes = md.digest((password + SALT).getBytes());
            BigInteger hashedBigInteger = new BigInteger(1, hashedBytes);
            this.hashed = true;
            return hashedBigInteger.toString();
        } catch (NoSuchAlgorithmException e) {
            LoggerFactory.getLogger(User.class).error("Этого не должно было случиться...");
            return "";
        }
    }

    public void checkHash() {
        if (!hashed) {
            this.password = getHashedPassword(this.password);
        }
    }

}
