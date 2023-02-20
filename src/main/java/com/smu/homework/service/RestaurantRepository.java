package com.smu.homework.service;

import com.smu.homework.dto.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Restaurant findByName(String name);

    List<Restaurant> findByCity(String city);

    List<Restaurant> findByState(String state);

}