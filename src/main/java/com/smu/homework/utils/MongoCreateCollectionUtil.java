package com.smu.homework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.validation.Validator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MongoCreateCollectionUtil {

    @Resource
    private MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Create [Collection]
     * Create a collection without size limit (the default creat method)
     *
     * @param collectionName collectionName
     * @return result of creation
     */
    public boolean createCollection(String collectionName) {
        // Create a collection and return the information
        mongoTemplate.createCollection(collectionName);
        // Detects if a new collection exists and returns the creation result
        boolean result = mongoTemplate.collectionExists(collectionName);
        if (result) {
            logger.info("Collection created successfully: {}!", collectionName);
        } else {
            logger.error("Failed to create a collection: {}!", collectionName);
        }
        return result;
    }

    /**
     * Create [fixed size collection]
     * Create a collection and set `capped=true`, you can also set the parameter: `size` limit the size of document, `max` limit the numbers of document.
     *
     * @param collectionName collection name
     * @param size           collection size
     * @param max            max documents of collection
     * @return {@link Boolean}
     */
    public boolean createCollectionFixedSize(String collectionName, Long size, Long max) {
        // Create a fixed size collection
        CollectionOptions collectionOptions = CollectionOptions.empty()
                // Fixed collection means collection with fixed size, when the maximum value is reached, it automatically overwrites the oldest document。
                .capped()
                // Fixed collection specifies a maximum value in kilobytes (KB), which is also required if capped is true.
                .size(size)
                // Specifies the maximum number of documents to be included in a fixed collection.
                .maxDocuments(max);
        // Execute the creation of a collection
        mongoTemplate.createCollection(collectionName, collectionOptions);
        // Detects if a new collection exists and returns the creation result
        boolean result = mongoTemplate.collectionExists(collectionName);
        if (result) {
            logger.info("Collection created successfully: {}!", collectionName);
        } else {
            logger.error("Failed to create a collection: {}!", collectionName);
        }
        return result;
    }

    /**
     * Create a collection with [document data validation]
     * <p>
     * Create a collection and "insert" and "update" in the document data validation, if the conditions set by the creation of the collection into allowing updates and insertions, otherwise, in accordance with the policy set by the settings for processing.
     * <p>
     * * Validation level：
     * - off：Close data validation.
     * - strict：(default) Valid for all document "Insert" and "Update" operations.
     * - moderate：Valid only for "Insert" and "Update" operations for "Documents" that meet the validation rules. It is not valid for existing "documents" that do not meet the verification rules.
     * * Execution strategy：
     * - error：(default) The document must satisfy the validation rules before it can be written.
     * - warn：MongoDB allows writing for "documents" that do not match the validation rules but logs an alert to mongod.log. The log records the error message and a full record of the "document".
     *
     * @param collectionName collection name
     * @param criteria       validation rules (e.g: CriteriaDefinition criteria = Criteria.where("age").gt(20);)
     * @return creating result
     */
    public boolean createCollectionValidation(String collectionName, CriteriaDefinition criteria) {
        // Set collection option validation object
        CollectionOptions collectionOptions = CollectionOptions.empty()
                .validator(Validator.criteria(criteria))
                // Set validation level
                .strictValidation()
                // Set the action to be performed if the validation does not pass
                .failOnValidationError();
        // Execute the creation of a collection
        mongoTemplate.createCollection(collectionName, collectionOptions);
        // Detects if a new collection exists and returns the creation result
        boolean result = mongoTemplate.collectionExists(collectionName);
        if (result) {
            logger.info("Collection created successfully: {}!", collectionName);
        } else {
            logger.error("Failed to create a collection: {}!", collectionName);
        }
        return result;
    }

}