package pl.com.sosnowski.develoment.backendCarWarehouses.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Coords {
    @Field("long")
    private String lon;

    @Field("lat")
    private String lat;
}
