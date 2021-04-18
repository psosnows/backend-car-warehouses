package pl.com.sosnowski.develoment.backendCarWarehouses.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@Document(collection = "places")
@AllArgsConstructor
public class Warehouse {
    @Id
    @Field("_id")
    private String id;

    @Field("name")
    private String name;

    @Field("cars_location")
    private String cars_location;

    @Field("location")
    private Coords coords;
}
