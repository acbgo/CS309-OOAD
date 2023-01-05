package com.example.springproject.bean;

public class UserWithoutCity {
    private int id;
    private String username;
    private String password;
    private long city_id;

    public UserWithoutCity(int id, String username, String password, long
            city_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.city_id = city_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }
}
