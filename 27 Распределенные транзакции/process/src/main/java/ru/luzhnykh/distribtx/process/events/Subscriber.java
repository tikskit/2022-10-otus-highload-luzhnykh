package ru.luzhnykh.distribtx.process.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.enums.Action;

@Slf4j
@Component
@RequiredArgsConstructor
public class DoctorRequestSubscriber {

    @KafkaListener(topics = "doctor-request")
    public void listen(@Payload DoctorRequestDto doctorRequestDto) {
        log.info("Действие {} запрос {}", doctorRequestDto.action(), doctorRequestDto);
        Action act = Action.valueOf(doctorRequestDto.action());
        switch (act) {
            case Action.ARRANGE:
                break;
            case Action.CANCEL:
                break;
            default:
        }
    }
}
