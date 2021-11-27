package com.example.newslab.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String password;
    private String tel;
    private String headPath;
    private String backgroundPath;

    public User() {

    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(String id, String name, String password, String tel) {
        this(id, password);
        this.name = name;
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }
}
