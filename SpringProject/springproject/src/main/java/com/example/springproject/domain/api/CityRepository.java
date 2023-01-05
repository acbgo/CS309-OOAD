package com.example.springproject.domain.api;

import com.example.springproject.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
    public City findCityById(long id);
}
