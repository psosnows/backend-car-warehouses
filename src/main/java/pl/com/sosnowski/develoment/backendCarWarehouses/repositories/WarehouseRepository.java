package pl.com.sosnowski.develoment.backendCarWarehouses.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.com.sosnowski.develoment.backendCarWarehouses.entities.Warehouse;

import java.util.Optional;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    Optional<Warehouse> findById(String id);
}
