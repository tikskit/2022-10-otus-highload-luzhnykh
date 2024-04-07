package ru.luzhnykh.distribtx.resources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestKafkaListener {
    @KafkaListener(topics = "test-topic")
    public void listen(String message) {
        log.info(message);
    }
}
