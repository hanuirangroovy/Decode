package com.ssafy.authsvr.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import java.util.List;

@Document("users")
@NoArgsConstructor
@Getter
public class PreferenceDocument {

    @Id
    private ObjectId id;

    private String name;

    private Integer age;

    private String gender;

    private List<String> location;

    private Integer thrill;

    private Integer romance;

    private Integer reasoning;

    private Integer sfFantasy;

    private Integer adventure;

    private Integer comedy;

    private Integer crime;

    private Integer horror;

    private Integer adult;

    private Integer drama;

    @Builder
    public PreferenceDocument(ObjectId id, String name, Integer age, String gender, List<String> location, Integer thrill, Integer romance, Integer reasoning, Integer sfFantasy, Integer adventure, Integer comedy, Integer crime, Integer horror, Integer adult, Integer drama) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.thrill = thrill;
        this.romance = romance;
        this.reasoning = reasoning;
        this.sfFantasy = sfFantasy;
        this.adventure = adventure;
        this.comedy = comedy;
        this.crime = crime;
        this.horror = horror;
        this.adult = adult;
        this.drama = drama;
    }

}