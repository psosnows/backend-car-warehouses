package pl.com.sosnowski.develoment.backendCarWarehouses.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.standard.expression.GreaterThanExpression;

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

        }

        return "Success";
    }
}
