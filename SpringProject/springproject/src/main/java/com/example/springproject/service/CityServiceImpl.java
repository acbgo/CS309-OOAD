package com.example.springproject.service;

import com.example.springproject.domain.City;
import com.example.springproject.domain.UserInfo;
import com.example.springproject.domain.api.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{
    @Autowired
    CityRepository cityRepository;

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public List<UserInfo> findUsersInCity(long id) {
        City city = cityRepository.findCityById(id);
        return city.getUsers();
    }
}
