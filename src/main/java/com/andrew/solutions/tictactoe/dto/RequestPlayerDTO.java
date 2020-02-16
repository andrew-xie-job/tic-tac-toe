package com.andrew.solutions.tictactoe.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestPlayerDTO implements Serializable {

    @NotNull
    private String userName;

    @NotNull
    private String password;

    public RequestPlayerDTO(@NotNull String userName, @NotNull String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
