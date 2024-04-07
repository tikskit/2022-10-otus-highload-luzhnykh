package ru.luzhnykh.distribtx.resources.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.enums.Action;
import ru.luzhnykh.distribtx.resources.services.DoctorRequestService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DoctorRequestSubscriber {
    private final DoctorRequestService doctorRequestService;

    @KafkaListener(topics = "doctor-request")
    public void listen(@Payload DoctorRequestDto doctorRequestDto) {
        log.info("Действие {} запрос {}", doctorRequestDto.action(), doctorRequestDto);
        Action act = Action.valueOf(doctorRequestDto.action());
        switch (act) {
            case Action.ARRANGE:
                doctorRequestService.addRequest(doctorRequestDto);
                break;
            case Action.CANCEL:
                doctorRequestService.cancelRequest(doctorRequestDto);
                break;
            default:
        }
    }
}
