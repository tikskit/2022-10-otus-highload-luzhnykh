package ru.luzhnykh.distribtx.process.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.common.dto.DoctorRequestDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class Publisher {
    public static final String TOPIC = "doctor-request";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishDoctorRequest(DoctorRequestDto requestDto) {
        try {
            kafkaTemplate.send(TOPIC, requestDto);
            log.info("Отправлен запрос врача {}", requestDto);
        } catch (Exception e) {
            log.error("Ошибка отправки запроса врача {}", requestDto, e);
            throw e;
        }
    }
}
