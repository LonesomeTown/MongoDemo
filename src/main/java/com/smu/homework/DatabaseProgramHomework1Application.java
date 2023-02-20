package com.smu.homework;

import com.mongodb.client.result.DeleteResult;
import com.opencsv.CSVReader;
import com.smu.homework.dto.Food;
import com.smu.homework.dto.Match;
import com.smu.homework.dto.Restaurant;
import com.smu.homework.dto.Supplier;
import com.smu.homework.service.MatchRepository;
import com.smu.homework.service.RestaurantRepository;
import com.smu.homework.service.SupplierRepository;
import com.smu.homework.utils.CSVReaderUtil;
import com.smu.homework.utils.JacksonUtils;
import com.smu.homework.utils.MongoQueryCollectionUtil;
import com.smu.homework.utils.MongoQueryDocumentUtil;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class DatabaseProgramHomework1Application implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DatabaseProgramHomework1Application.class);

    @Resource
    private CSVReaderUtil csvReaderUtil;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private RestaurantRepository restaurantRepository;
    @Resource
    private SupplierRepository supplierRepository;
    @Resource
    private MatchRepository matchRepository;
    @Resource
    private MongoQueryCollectionUtil mongoQueryCollectionUtil;
    @Resource
    private MongoQueryDocumentUtil mongoQueryDocumentUtil;
    @Resource
    private MongoTemplate mongoTemplate;


    public static void main(String[] args) {
        SpringApplication.run(DatabaseProgramHomework1Application.class, args);
    }

    @Value("${taskName}")
    private String taskName;

    private final String outputPath = System.getProperty("user.dir") + File.separator + "output" + File.separator;

    @Override
    public void run(String... args) {
        if ("task1".equals(taskName)) {
            logger.info("============== Start executing Task 1 ==============");
            this.task1();
            logger.info("============== Finish executing Task 1 ==============");
        } else if ("task2".equals(taskName)) {
            logger.info("============== Start executing Task 2==============");
            this.task2();
            logger.info("============== Finish executing Task 2 ==============");
        }
    }

    private void task1() {
        mongoTemplate.dropCollection(Restaurant.class);
        mongoTemplate.dropCollection(Supplier.class);
        mongoTemplate.dropCollection(Match.class);

        //Read data from csv file and save it into Mongodb
        List<Restaurant> restaurants = new ArrayList<>();
        csvReaderUtil.read("restaurant.csv", restaurants);
        restaurantRepository.saveAll(restaurants);

        List<Supplier> suppliers = new ArrayList<>();
        csvReaderUtil.read("supplier.csv", suppliers);
        supplierRepository.saveAll(suppliers);

        List<Match> matches = new ArrayList<>();
        csvReaderUtil.read("match.csv", matches);
        matchRepository.saveAll(matches);

        this.printAllDocuments("result_Wang_Zhimin.txt");

        SpringApplication.exit(applicationContext);
    }

    private void task2() {
        String dirPath = System.getProperty("user.dir") + File.separator + "file" + File.separator;
        String filePath = dirPath + "command.csv";
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error("File doesn't exit!");
            return;
        }
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (int i = 0; i < line.length; i++) {
                    line[i] = line[i].trim().replace("“", "").replace("”", "");
                }
                int operateType = Integer.parseInt(line[0]);
                switch (operateType) {
                    case 0:
                        logger.info("============== Start executing Task 2 Operation 0==============");
                        Match match = new Match().setRestaurantName(line[1].trim()).setSupplierName(line[2]);
                        Food food = new Food();
                        food.setName(line[3]);
                        food.setOrigin(line[4]);
                        List<Supplier> foodsContains = supplierRepository.findAllByFoodsContains(food);
                        if (foodsContains.isEmpty()) {
                            logger.info("No such food from this place");
                            continue;
                        }
                        match.setFood(food);
                        boolean exists = matchRepository.exists(Example.of(match));
                        if (exists) {
                            logger.info("Info already exists");
                        } else {
                            matchRepository.save(match);
                            logger.info("Successful insertion");
                        }
                        break;
                    case 1:
                        logger.info("============== Start executing Task 2 Operation 1==============");
                        Match match1 = new Match().setRestaurantName(line[1].trim()).setSupplierName(line[2]);
                        Food food1 = new Food();
                        food1.setName(line[3]);
                        food1.setOrigin(line[4]);
                        match1.setFood(food1);
                        List<Match> all = matchRepository.findAll(Example.of(match1));
                        if (CollectionUtils.isEmpty(all)) {
                            logger.info("Info doesn't exist");
                        } else {
                            for (Match match2 : all) {
                                DeleteResult deleteResult = mongoTemplate.remove(new Query(Criteria.where("_id").is(match2.getId())), Match.class);
                                deleteResult.getDeletedCount();
                            }
                            logger.info("Successful Deletion");
                        }
                        break;
                    case 2:
                        logger.info("============== Start executing Task 2 Operation 2==============");
                        String restaurantName = line[1];
                        Optional<Restaurant> byId = restaurantRepository.findById(restaurantName);
                        if (!byId.isPresent()) {
                            logger.info("Not found");
                        } else {
                            Restaurant restaurantByName = byId.get();
                            logger.info(restaurantByName.getName() + " " + restaurantByName.getCity() + " " + restaurantByName.getState());
                        }
                        // Find all suppliers
                        List<Supplier> allSuppliers = supplierRepository.findAll();
                        if (CollectionUtils.isEmpty(allSuppliers)) {
                            logger.info("Not found");
                        } else {
                            // Find all foods that suppliers obtain
                            List<Food> allKindFoods = new ArrayList<>();
                            for (Supplier allSupplier : allSuppliers) {
                                List<Food> foods = allSupplier.getFoods();
                                if (CollectionUtils.isNotEmpty(foods)) {
                                    allKindFoods.addAll(foods);
                                }
                            }
                            //remove duplicate foods
                            allKindFoods = allKindFoods.stream().distinct().collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(allKindFoods)) {
                                for (Food allKindFood : allKindFoods) {
                                    // Find the suppler info by food info
                                    List<Supplier> allByFoodsContains = supplierRepository.findAllByFoodsContains(allKindFood);
                                    if (CollectionUtils.isEmpty(allByFoodsContains)) {
                                        logger.info("Not found");
                                    } else {
                                        StringBuilder suppliersName = new StringBuilder();
                                        for (Supplier allByFoodsContain : allByFoodsContains) {
                                            suppliersName.append(allByFoodsContain.getName()).append(" ");
                                        }
                                        logger.info(allKindFood + " " + suppliersName);
                                    }
                                }
                            }
                        }
                        break;
                    case 3:
                        logger.info("============== Start executing Task 2 Operation 3==============");
                        String foodName = line[1];
                        String foodOrigin = line[2];
                        Food food2 = new Food().setName(foodName).setOrigin(foodOrigin);
                        List<Match> byFoodContains = matchRepository.findByFood(food2);
                        if (CollectionUtils.isEmpty(byFoodContains)) {
                            logger.info("Not found");
                        } else {
                            for (Match byFoodContain : byFoodContains) {
                                String foodContainRestaurantName = byFoodContain.getRestaurantName();
                                Optional<Restaurant> restaurantRepositoryById = restaurantRepository.findById(foodContainRestaurantName);
                                if (restaurantRepositoryById.isPresent()) {
                                    Restaurant restaurant = restaurantRepositoryById.get();
                                    logger.info(restaurant.getName() + " " + restaurant.getCity());
                                } else {
                                    logger.info("Not found");
                                }
                            }
                        }
                        break;
                    default:
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        this.printAllDocuments("result2_Wang_Zhimin.txt");

        SpringApplication.exit(applicationContext);
    }

    private void printAllDocuments(String fileName) {
        //Each file has its own collection, get all collection names
        Set<String> collectionNames = mongoQueryCollectionUtil.getCollectionNames();
        List<String> results = new ArrayList<>();
        for (String collectionName : collectionNames) {
            switch (collectionName) {
                case "restaurant":
                    List<Restaurant> restaurantsResult = mongoQueryDocumentUtil.findAll(Restaurant.class, collectionName);
                    if (CollectionUtils.isNotEmpty(restaurantsResult)) {
                        results.add("-------------------------------------");
                        results.add("These are restaurant information:");
                        for (Restaurant restaurant : restaurantsResult) {
//                            String str = restaurant.getName() + "," +
//                                    restaurant.getCity() + "," +
//                                    restaurant.getState();

                            results.add(JacksonUtils.toJson(restaurant));
                        }
                    }
                    break;
                case "supplier":
                    List<Supplier> suppliersResult = mongoQueryDocumentUtil.findAll(Supplier.class, collectionName);
                    if (CollectionUtils.isNotEmpty(suppliersResult)) {
                        results.add("-------------------------------------");
                        results.add("These are supplier information:");
                        for (Supplier supplier : suppliersResult) {
//                            StringBuilder sb = new StringBuilder(supplier.getName() + "," +
//                                    supplier.getCity() + "," +
//                                    supplier.getState() + "," +
//                                    supplier.getNumber() + ",");
//                            List<Food> foods = supplier.getFoods();
//                            if (CollectionUtils.isNotEmpty(foods)) {
//                                for (Food food : foods) {
//                                    sb.append(food.getName()).append(",").append(food.getOrigin()).append(",");
//                                }
//                            }
//                            //Delete the last ","
//                            sb.deleteCharAt(sb.length() - 1);
                            results.add(JacksonUtils.toJson(supplier));
                        }
                    }
                    break;
                case "match":
                    List<Match> matchesResult = mongoQueryDocumentUtil.findAll(Match.class, collectionName);
                    if (CollectionUtils.isNotEmpty(matchesResult)) {
                        results.add("-------------------------------------");
                        results.add("These are match information:");
                        for (Match match : matchesResult) {
//                            String str = match.getRestaurantName() + "," +
//                                    match.getSupplierName();
//                            if (null != match.getFood()) {
//                                str = str + "," + match.getFood().getName() + "," +
//                                        match.getFood().getOrigin();
//                            }
                            results.add(JacksonUtils.toJson(match));
                        }
                    }
                    break;
                default:
            }
        }
        File filePath = new File(outputPath + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
