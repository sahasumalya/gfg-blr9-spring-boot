package org.example.gfgblr9.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

    @KafkaListener(topics = "order", groupId = "my-group") // Kafka topic and group
    public void consumeMessage(String message) {
        System.out.println("Consumed message: " + message+" thread: " + Thread.currentThread().getName());
        log.info(message);
    }
}