package ru.luzhnykh.distribtx.process;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.dto.resources.DoctorRequestResponseDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class Publisher {
    public static final String TOPIC = "doctor-request-reply";
    private final KafkaTemplate<String, DoctorRequestResponseDto> kafkaTemplate;

    public void publish(@NonNull DoctorRequestResponseDto responseDto) {
        kafkaTemplate.send(TOPIC, responseDto);
        log.info("Send {}", responseDto);
    }
}
