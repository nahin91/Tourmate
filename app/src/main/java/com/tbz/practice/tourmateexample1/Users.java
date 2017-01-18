package com.tbz.practice.tourmateexample1;

/**
 * Created by USER on 28-Dec-16.
 */

public class Users {
    private int id;
    private String name;
    private String email;
    private String password;
    private String mobile;

    public Users() {

    }

    public Users(int id, String name, String email, String password, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public Users(int id, String email, String password) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public Users(String name, String email, String password, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
