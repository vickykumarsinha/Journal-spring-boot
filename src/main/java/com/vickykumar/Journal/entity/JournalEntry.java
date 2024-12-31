package com.vickykumar.Journal.entity;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
// Lombok is Used to generate GETTER SETTER & reduce boiler plate code by making it during execution
@Getter
@Setter
public class JournalEntry {

    @Id
    private ObjectId id; // this is default unique ObjectId for storing data in mongo
    private String title;
    private String content;


}
