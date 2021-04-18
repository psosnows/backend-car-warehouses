package pl.com.sosnowski.develoment.backendCarWarehouses.controllers;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log
@Controller
public class WarehouseController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello()
    {
        return "Hi, we are here!";
    }
}
