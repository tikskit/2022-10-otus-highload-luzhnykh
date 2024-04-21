package ru.luzhnykh.distribtx.process.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.luzhnykh.distribtx.process.dto.SurgeryArrangeDto;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestResponseDto;
import ru.luzhnykh.distribtx.resources.enums.Action;

@Slf4j
@Component
@RequiredArgsConstructor
public class Subscriber {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @KafkaListener(topics = "surgery-arrange")
    public void surgeryArrangeListener(String payload) {
        SurgeryArrangeDto surgeryArrange = objectMapper.readValue(payload, SurgeryArrangeDto.class);
        log.info(surgeryArrange.toString());
    }

    @SneakyThrows
    @KafkaListener(topics = "doctor-request-reply")
    public void doctorRequestReplyListener(String payload) {
        DoctorRequestResponseDto responseDto = objectMapper.readValue(payload, DoctorRequestResponseDto.class);
        log.info("Действие {} запрос {}", responseDto.action(), payload);
        Action act = Action.valueOf(responseDto.action());
        switch (act) {
            case Action.ARRANGE:

                break;
            case Action.CANCEL:
                break;
            default:
        }
    }


}
