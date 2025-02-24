package com.sankalp.follow_service.repository;

import com.sankalp.follow_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByName(String name);

    @Query("MATCH (a:Person {userId: $userId}) -[:follows]-> (b:Person) " +
            "RETURN b")
    List<Person> findFirstDegreeFollowers(Long userId);


    @Query("MATCH (personA :Person{userId: $userId}) -[:follows*2]-> (personC :Person) " +
            "WHERE NOT (personA) -[:follows]-> (personC) " +
            "AND personC <> personA " +
            "RETURN personC")
    List<Person> findSecondDegreeFollowers(Long userId);

    //REMOVE FOLLOW
    //CHECK if already followers
    //add feature for private account follow request

}
