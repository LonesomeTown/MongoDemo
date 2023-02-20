package com.smu.homework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class MongoQueryCollectionUtil {

    @Resource
    private MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Get the list of [collection name].
     *
     * @return list of collection name
     */
    public Set<String> getCollectionNames() {
        return mongoTemplate.getCollectionNames();
    }

    /**
     * Detect the collection [exists or not]
     *
     * @return collection exists or not
     */
    public boolean collectionExists(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

}