package it.snorcini.dev.orderplanner.entity;

import com.querydsl.core.annotations.QueryEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;

/**
 * Depot Entity.
 * <p>
 * Methods:
 * - constructor
 * - getter and setter
 * are auto-generated by Lombok.
 * <p>
 */
@Document(collection = "depot")
@QueryEntity
@Data
@Builder
@Validated
public class Depot implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String uid;

    @NotNull
    @Field(name = "name")
    private String name;

    @NotNull
    @Field(name = "latitude")
    private Long latitude;

    @NotNull
    @Field(name = "longitude")
    private Long longitude;

}