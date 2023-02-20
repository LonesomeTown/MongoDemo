package com.smu.homework.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * Restaurant
 *
 * @author T.W 10/7/22
 */
@Data
@Accessors(chain = true)
public class Restaurant {
    @MongoId
    private String name;
    private String city;
    private String state;
}
