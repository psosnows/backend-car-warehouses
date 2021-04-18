package pl.com.sosnowski.develoment.backendCarWarehouses.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.sosnowski.develoment.backendCarWarehouses.entities.Car;
import pl.com.sosnowski.develoment.backendCarWarehouses.entities.Coords;
import pl.com.sosnowski.develoment.backendCarWarehouses.entities.Warehouse;
import pl.com.sosnowski.develoment.backendCarWarehouses.repositories.CarRepository;
import pl.com.sosnowski.develoment.backendCarWarehouses.repositories.WarehouseRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class WarehouseControllerTest {

    @Autowired
    private WarehouseController warehouseController;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private WarehouseRepository warehouseRepository;


    @Test
    void handleGetAllCarsSortedPaged() {
        Pageable paging = PageRequest.of(0, 1, Sort.by("date_added").ascending());
        when(carRepository.findAll(paging)).thenReturn(mockFindAll());
        ResponseEntity<Map<String, Object>> response = warehouseController.getAllCarsSortedPaged(0, 1);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).get("totalItems"), 1L);

    }

    private Page<Car> mockFindAll() {
        List<Car> content = new ArrayList<>();
        content.add(new Car(9999, "VW","Golf", 1999, 500.01, true, "2021-01-01", "0"));
        Pageable paging = PageRequest.of(0, 1, Sort.by("date_added").ascending());
        return new PageImpl<>(content, paging, 1);
    }


    @Test
    void getWarehouse() {
        when(warehouseRepository.findById("0")).thenReturn(mockFindWarehouse());
        ResponseEntity<Object> response = warehouseController.getWarehouse("0");
        Warehouse responseWarehouse =((Optional<Warehouse>) response.getBody()).get();

        Assertions.assertEquals(responseWarehouse.getName(), "Name");

    }

    private Optional<Warehouse> mockFindWarehouse() {
        return Optional.of(new Warehouse("0", "Name", "Loc", new Coords("0", "0")));
    }
}