package src.javaproject.classes;

import src.javaproject.interfaces.SerializeMarker;

import java.io.Serializable;

/**
 * Generic class for handling accounts when logging in and using the app
 * @param <R> can be String or null, depending on the user role
 */
public final class Account<R> implements Serializable, SerializeMarker {
    private final String username;
    private final String password;
    private final R role;

    public Account(String username, String password, R role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String usernameGetter() {
        return username;
    }

    public String passwordGetter() {
        return password;
    }

    public R roleGetter() {
        return role;
    }

    @Override
    public String toString() {
        if (role == null) {
            return STR."\{username} \{password}";
        } else {
            return STR."\{username} \{password} \{role}";
        }
    }
}
