package com.enoch.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.enoch.model.Attribute;

public interface AttributeRepository extends MongoRepository<Attribute, String>, QuerydslPredicateExecutor<Attribute> {
    @Query("{ 'name' : ?0 }")
    List<Attribute> findAttributesByName(String name);

	/*
	 * @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }") List<User>
	 * findUsersByAgeBetween(int ageGT, int ageLT);
	 * 
	 * @Query("{ 'name' : { $regex: ?0 } }") List<User> findUsersByRegexpName(String
	 * regexp);
	 * 
	 * List<User> findByName(String name);
	 * 
	 * List<User> findByNameLikeOrderByAgeAsc(String name);
	 * 
	 * List<User> findByAgeBetween(int ageGT, int ageLT);
	 * 
	 * List<User> findByNameStartingWith(String regexp);
	 * 
	 * List<User> findByNameEndingWith(String regexp);
	 * 
	 * @Query(value = "{}", fields = "{name : 1}") List<User> findNameAndId();
	 * 
	 * @Query(value = "{}", fields = "{_id : 0}") List<User>
	 * findNameAndAgeExcludeId();
	 */
}