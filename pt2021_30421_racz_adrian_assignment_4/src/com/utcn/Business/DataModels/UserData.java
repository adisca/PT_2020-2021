package com.utcn.Business.DataModels;

import java.io.Serializable;

/**
 * Class for user credentials
 */
public class UserData implements Serializable {
    private final Integer id;
    private final String username;
    private final String password;
    private final UserType type;

    public UserData(Integer id, String username, String password, UserType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }
}
