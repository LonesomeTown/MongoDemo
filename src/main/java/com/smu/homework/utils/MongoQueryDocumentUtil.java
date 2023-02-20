package com.smu.homework.utils;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * MongoQueryDocumentUtil
 *
 * @author T.W 10/8/22
 */
@Service
public class MongoQueryDocumentUtil {
    @Resource
    private MongoTemplate mongoTemplate;
    public <T> List<T> findAll(Class<T> clazz, String collectionName) {
        // 执行查询集合中全部文档信息
        return mongoTemplate.findAll(clazz, collectionName);
    }
}
