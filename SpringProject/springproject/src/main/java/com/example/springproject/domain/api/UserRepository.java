package com.example.springproject.domain.api;

import com.example.springproject.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
}
