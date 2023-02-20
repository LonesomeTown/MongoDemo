package com.smu.homework.service;

import com.smu.homework.dto.Food;
import com.smu.homework.dto.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * SupplierRepository
 *
 * @author T.W 10/14/22
 */
public interface SupplierRepository extends MongoRepository<Supplier, String> {
    Supplier findByName(String name);

    List<Supplier> findByCity(String city);

    List<Supplier> findByState(String state);

    List<Supplier> findAllByFoodsContains(Food food);
}
