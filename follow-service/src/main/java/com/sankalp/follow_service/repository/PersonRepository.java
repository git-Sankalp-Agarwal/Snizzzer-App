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
}
