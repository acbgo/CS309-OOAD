package com.example.springproject.app;

import com.example.springproject.bean.UserWithoutCity;
import com.example.springproject.domain.UserInfo;
import com.example.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInfoApp {
    @Autowired
    private UserService userService;

    @PostMapping("/addOne")
    public UserWithoutCity addOneUser(UserInfo userInfo, @RequestParam long
            city_id) {
        userService.saveWithFk(userInfo, city_id);
        return userInfo.convertToUserWithoutCity();
    }

}
