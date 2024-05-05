package ru.luzhnykh.distribtx.process.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.common.enums.DoctorRequestRetCodes;
import ru.luzhnykh.distribtx.process.dto.StartProcessParamDto;
import ru.luzhnykh.distribtx.process.dto.SurgeryArrangeDto;
import ru.luzhnykh.distribtx.process.service.ProcessOrchestratorService;
import ru.luzhnykh.distribtx.common.dto.DoctorRequestResponseDto;
import ru.luzhnykh.distribtx.common.enums.Action;

@Slf4j
@Service
@RequiredArgsConstructor
public class Subscriber {
    private final ObjectMapper objectMapper;
    private final ProcessOrchestratorService orchestrator;

    @SneakyThrows
    @KafkaListener(topics = "start-process")
    public void startProcessListener(String payload) {
        StartProcessParamDto param = objectMapper.readValue(payload, StartProcessParamDto.class);

        log.info("Запуск процесса назначения операции: {}", param);
        orchestrator.startProcess(param);
    }

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

    }


}
