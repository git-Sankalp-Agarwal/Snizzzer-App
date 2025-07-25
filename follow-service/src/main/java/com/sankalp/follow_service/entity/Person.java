package com.sankalp.follow_service.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node
@Data
@Builder
public class Person {
    @Id
    private Long userId;
    private String name;

}
