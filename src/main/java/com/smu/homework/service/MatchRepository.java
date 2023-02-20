package com.smu.homework.service;

import com.smu.homework.dto.Food;
import com.smu.homework.dto.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MatchRepository
 *
 * @author T.W 10/14/22
 */
@Transactional(rollbackFor = Throwable.class)
public interface MatchRepository extends MongoRepository<Match, String> {
    List<Match> findByRestaurantName(String restaurantName);

    List<Match> findBySupplierName(String supplierName);

    List<Match> findByFood(Food food);
}
