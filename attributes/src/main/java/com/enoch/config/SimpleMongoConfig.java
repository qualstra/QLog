package com.enoch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.enoch.repository")
public class SimpleMongoConfig {
	@Value("${mongo.url}")
	private String mongourl;
	
    @Bean
    public MongoClient mongo() throws Exception {
        final ConnectionString connectionString = new ConnectionString(mongourl);
		String user = "appusr";
		String pass = "pass";
		String database = "appdb";
		int port = 8008;
		String host = "127.0.0.1";

	MongoCredential credential = MongoCredential.createCredential(user, database, pass.toCharArray());

        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).credential(credential).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "appdb");
    }

}
