package pl.com.sosnowski.develoment.backendCarWarehouses.controllers;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.java.Log;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@Log
@Controller
public class WarehouseController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello()
    {
        return "Hi, we are here!";
    }

    @GetMapping("/insert_db")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public String GetWarehouseData(@RequestBody String body) {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonArray.add(jsonParser.parse(body));
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Failed to insert to database.");
            return "Failed";
        }

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase theDB = mongoClient.getDatabase("car_sales");

            // keep cars and places (warehouses) in separate collections
            MongoCollection<Document> placesCollection = theDB.getCollection("places");
            MongoCollection<Document> carsCollection = theDB.getCollection("cars");

            for (Object jsonObject : (JSONArray) jsonArray.get(0)) {
                Document doced = Document.parse(jsonObject.toString());
                Document cars = (Document) doced.get("cars");
                Document place = new Document();

                // parsing warehouse information to put into db
                for (String key : Arrays.asList("_id", "name", "location")) {
                    place.append(key, doced.get(key));
                }
                place.append("cars_location", cars.get("location"));
                try {
                    placesCollection.insertOne(place);
                } catch (MongoWriteException e) {
                    log.log(Level.WARNING, "Duplicate record found: place, not inserting.");
                }

                // dealing with all the cars in particular warehouse
                for (Document car : (List<Document>) cars.get("vehicles")) {
                    // adding reference id to identify where the car is located
                    car.append("warehouse_id", place.get("_id"));
                    try {
                        carsCollection.insertOne(car);
                    } catch (MongoWriteException e) {
                        log.log(Level.WARNING, "Duplicate record found: car, not inserting.");
                    }
                }
            }
        }
        return "Success";
    }
}
