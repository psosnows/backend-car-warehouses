package pl.com.sosnowski.develoment.backendCarWarehouses.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.com.sosnowski.develoment.backendCarWarehouses.entities.Car;

import java.awt.print.Pageable;

public interface CarRepository extends MongoRepository<Car, Long> {
    Page<Car> findAll(Pageable pageable);
}
