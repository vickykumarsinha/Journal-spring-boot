package com.vickykumar.Journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement	//	this will help to manage trnxs
public class JournalApplication {

	public static void main(String[] args) {

		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	// Provide Trnx manager for Mongo
	// PlatformTransactionManager - Manage trnx, rollback, commit, begin
	// MongoDatabaseFactory- provides the connection to your MongoDB instance
	public PlatformTransactionManager anyName (MongoDatabaseFactory dbFact){
		return new MongoTransactionManager(dbFact);
	}

}
