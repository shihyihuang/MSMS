package org.fit5136.e;

import java.util.Objects;

/**
 * Class which is for storing the user information.
 *
 * @version 1.0.0
 */
public class User {

    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean equals(User user) {
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
