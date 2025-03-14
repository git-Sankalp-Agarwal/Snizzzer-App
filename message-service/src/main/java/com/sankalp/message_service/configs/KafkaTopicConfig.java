package com.sankalp.message_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createTweetTopic() {
        return TopicBuilder.name("message-send-topic")
                           .partitions(3)
                           .replicas(1)
                           .build();
    }

}
