package com.smu.homework.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * Match
 *
 * @author T.W 10/14/22
 */
@Data
@Accessors(chain = true)
public class Match {
    @MongoId
    private ObjectId id;
    private String restaurantName;
    private String supplierName;
    private Food food;
}
