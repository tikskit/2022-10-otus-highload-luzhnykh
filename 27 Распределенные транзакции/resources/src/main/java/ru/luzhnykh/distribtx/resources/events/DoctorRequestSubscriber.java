package ru.luzhnykh.distribtx.resources.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.services.DoctorRequestService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DoctorRequestSubscriber {
    private final DoctorRequestService doctorRequestService;

    @KafkaListener(topics = "doctor-request")
    public void listen(DoctorRequestDto doctorRequestDto) {
        log.info(doctorRequestDto.toString());
        doctorRequestService.addRequest(doctorRequestDto);
    }
}
