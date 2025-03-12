package com.sankalp.follow_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createUserFollowedTopic() {
        return TopicBuilder.name("user-followed-topic")
                           .partitions(3)
                           .replicas(1)
                           .build();
    }

    @Bean
    public NewTopic createUserFollowRequestTopic() {
        return TopicBuilder.name("user-follow-request-topic")
                           .partitions(3)
                           .replicas(1)
                           .build();
    }


}
