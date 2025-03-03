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

    //query to start following
    @Query("MATCH (a:Person{userId : $senderId}),(b:Person{userId : $receiverId}) " +
            "MERGE (a)-[:follows]->(b)")
    void startFollowing(Long senderId, Long receiverId);

    //Query for remove follow
    @Query("MATCH (a:Person{userId : $senderId}) -[r:follows]-> (b:Person{userId : $receiverId}) " +
            "DELETE (r)")
    void startUnFollowing(Long senderId, Long receiverId);

    //query for checkIfAlreadyFollows
    @Query("MATCH (a:Person{userId : $senderId}) -[r:follows]-> (b:Person{userId : $receiverId}) " +
            "RETURN COUNT(r) > 0")
    boolean checkIfAlreadyFollows(Long senderId, Long receiverId);

    //TODO: add feature for private account follow request
    //TODO: Check if already follow request exists for private accounts
    //TODO: Send follow request to private account
    //TODO: Reject request for private account
    //TODO: Accept request for private account

}
