package com.example.springproject.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city_name;
    private String province;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    /**
     * mappedBy="city", city marked the attribute in UserInfo, which can help
     * to make association of JPA
     */
    @OneToMany(mappedBy = "city")
    private List<UserInfo> users = new ArrayList<>();

    public void setUsers(List<UserInfo> users) {
        for (UserInfo user : users
        ) {
            user.setCity(this);
        }
        this.users = users;
    }
}

