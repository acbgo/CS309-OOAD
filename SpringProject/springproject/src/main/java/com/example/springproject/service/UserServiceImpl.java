package com.example.springproject.service;

import com.example.springproject.domain.City;
import com.example.springproject.domain.UserInfo;
import com.example.springproject.domain.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void saveWithFk(UserInfo userInfo, long id) {
        City city = entityManager.getReference(City.class, id);
        userInfo.setCity(city);
        userRepository.save(userInfo);
    }
}
