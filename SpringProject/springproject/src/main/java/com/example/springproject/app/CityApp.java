package com.example.springproject.app;

import com.example.springproject.bean.UserWithoutCity;
import com.example.springproject.domain.City;
import com.example.springproject.domain.UserInfo;
import com.example.springproject.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/city")
public class CityApp {
    @Autowired
    CityService cityService;

    @PostMapping("/addCity")
    public City addCity(City city){
        cityService.saveCity(city);
        return city;
    }

    @PostMapping("/findAllUser")
    public List<UserWithoutCity> findAllUsersByCityId(@RequestParam long
                                                              city_id) {
        return cityService.findUsersInCity(city_id).stream()
                .map(UserInfo::convertToUserWithoutCity).collect(Collectors.toList());
    }
}
