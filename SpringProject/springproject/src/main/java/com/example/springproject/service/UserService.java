package com.example.springproject.service;

import com.example.springproject.domain.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void saveWithFk(UserInfo userInfo, long id);
}
