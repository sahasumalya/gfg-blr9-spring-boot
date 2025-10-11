package org.example.gfgblr9.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final String TOPIC_NAME = "order";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        System.out.println("Producing message: " + message);
        kafkaTemplate.send(TOPIC_NAME, message); // Publish message to Kafka
    }
}