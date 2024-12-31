package com.vickykumar.Journal.Repository;

import com.vickykumar.Journal.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId>{


}
