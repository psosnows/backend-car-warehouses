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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.com.sosnowski.develoment.backendCarWarehouses.entities.Car;
import pl.com.sosnowski.develoment.backendCarWarehouses.entities.Warehouse;
import pl.com.sosnowski.develoment.backendCarWarehouses.repositories.CarRepository;
import pl.com.sosnowski.develoment.backendCarWarehouses.repositories.WarehouseRepository;

import java.util.*;
import java.util.logging.Level;

@Log
@Controller
public class WarehouseController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

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

    @GetMapping("/cars_sorted")
    public ResponseEntity<Map<String, Object>> getAllCarsSortedPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        try {
            Pageable paging = PageRequest.of(page, size, Sort.by("date_added").ascending());
            Page<Car> pageCars = carRepository.findAll(paging);

            List<Car> cars = pageCars.getContent();
            if(cars.isEmpty()) {
                log.log(Level.WARNING, "Got empty car list!");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("cars", cars);
            response.put("currentPage", pageCars.getNumber());
            response.put("totalItems", pageCars.getTotalElements());
            response.put("totalPages", pageCars.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/warehouse")
    public ResponseEntity<Object> getWarehouse(
            @RequestParam() String id
    ) {
        try {
            Optional<Warehouse> warehouse = warehouseRepository.findById(id);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
