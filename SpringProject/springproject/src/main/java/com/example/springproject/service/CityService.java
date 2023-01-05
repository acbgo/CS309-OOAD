package com.example.springproject.service;

import com.example.springproject.domain.City;
import com.example.springproject.domain.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    public void saveCity(City city);

    public List<UserInfo> findUsersInCity(long id);
}
