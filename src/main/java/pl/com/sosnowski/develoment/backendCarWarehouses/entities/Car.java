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
@Document(collection = "cars")
@AllArgsConstructor
public class Car {
    @Id
    @Field("_id")
    private Integer id;

    @Field("make")
    private String make;

    @Field("model")
    private String model;

    @Field("year_model")
    private Integer year_model;

    @Field("price")
    private Double price;

    @Field("licensed")
    private Boolean is_licensed;

    @Field("date_added")
    private String date_added;

    @Field("warehouse_id")
    private String warehouse_id;
}
