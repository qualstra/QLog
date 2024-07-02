package com.enoch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.enoch.reactive.repository")
public class MongoReactiveConfig extends AbstractReactiveMongoConfiguration {

	
	@Value("${mongo.url}")
	private String mongourl;

    @Override
    public MongoClient reactiveMongoClient() {
    	 final ConnectionString connectionString = new ConnectionString(mongourl);
 		String user = "appusr";
 		String pass = "pass";
 		String database = "appdb";

 	MongoCredential credential = MongoCredential.createCredential(user, database, pass.toCharArray());

         final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
             .applyConnectionString(connectionString).credential(credential)
             .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected String getDatabaseName() {
        return "appdb";
    }
}
