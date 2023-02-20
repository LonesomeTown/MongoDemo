package com.smu.homework.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

/**
 * Supplier
 *
 * @author T.W 10/11/22
 */
@Data
@Accessors(chain = true)
public class Supplier {
    @MongoId
    private String name;
    private String city;
    private String state;
    private Integer number;
    private List<Food> foods;
}
