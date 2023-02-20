package com.smu.homework.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.smu.homework.dto.Food;
import com.smu.homework.dto.Match;
import com.smu.homework.dto.Restaurant;
import com.smu.homework.dto.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CSVReaderUtil
 *
 * @author T.W 10/8/22
 */
@Component
public class CSVReaderUtil {
    private final Logger logger = LoggerFactory.getLogger(CSVReaderUtil.class);

    public <T> void read(String fileName, List<T> objects) {
        String dirPath = System.getProperty("user.dir") + File.separator + "file" + File.separator;
        String filePath = dirPath + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            logger.error("File doesn't exit!");
            return;
        }
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (int i = 0; i < line.length; i++) {
                    line[i] = line[i].trim().replace("“", "").replace("”", "");
                }
//                logger.info("Reading from the csv file:{}", JacksonUtils.toJson(line));
                switch (fileName) {
                    case "restaurant.csv":
                        Restaurant restaurant = new Restaurant().setName(line[0]).setCity(line[1]).setState(line[2]);
                        objects.add((T) restaurant);
                        break;
                    case "supplier.csv":
                        int foodNums = Integer.parseInt(line[3].trim());
                        Supplier supplier = new Supplier().setName(line[0]).setCity(line[1]).setState(line[2]).setNumber(foodNums);
                        List<Food> foods = new ArrayList<>();
                        //The rest of this line are food information
                        for (int i = 0; i < foodNums * 2; i = i + 2) {
                            Food food = new Food();
                            food.setName(line[4 + i]);
                            food.setOrigin(line[i + 5]);
                            foods.add(food);
                        }
                        supplier.setFoods(foods);
                        objects.add((T) supplier);
                        break;
                    case "match.csv":
                        Match match = new Match().setRestaurantName(line[0]).setSupplierName(line[1]);
                        Food food = new Food();
                        food.setName(line[2]);
                        food.setOrigin(line[3]);
                        match.setFood(food);
                        objects.add((T) match);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String a = "“Supplier 1”";
        a = a.trim().replace("“", "").replace("”", "");
        System.out.println(a);

    }


}
