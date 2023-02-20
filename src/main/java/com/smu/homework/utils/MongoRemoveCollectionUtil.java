package com.smu.homework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MongoRemoveCollectionUtil {

    @Resource
    private MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Remove a collection
     *
     * @return removing result
     */
    public boolean dropCollection(String collectionName) {
        // Execute the deletion of a collection
        mongoTemplate.getCollection(collectionName).drop();
        // Detects if a new collection exists and returns the creation result
        boolean result = mongoTemplate.collectionExists(collectionName);
        if (result) {
            logger.info("Collection removed successfully: {}!", collectionName);
        } else {
            logger.error("Failed to remove a collection: {}!", collectionName);
        }
        return result;
    }



}