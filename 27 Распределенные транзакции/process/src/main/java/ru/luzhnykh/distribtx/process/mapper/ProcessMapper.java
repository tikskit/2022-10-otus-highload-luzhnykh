package ru.luzhnykh.distribtx.process.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.luzhnykh.distribtx.process.domain.Process;
import ru.luzhnykh.distribtx.process.dto.ProcessDto;
import ru.luzhnykh.distribtx.process.dto.StartProcessParamDto;
import ru.luzhnykh.distribtx.process.enums.ProcessExecutionState;
import ru.luzhnykh.distribtx.process.enums.StepState;

@Component
@RequiredArgsConstructor
public class ProcessMapper {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public ProcessDto toDto(Process domain) {
        if (domain == null) {
            return null;
        }

        return new ProcessDto(
                domain.processId(),
                objectMapper.readValue(domain.params(), StartProcessParamDto.class),
                ProcessExecutionState.valueOf(domain.state()),
                StepState.valueOf(domain.prescriptionStep()),
                StepState.valueOf(domain.doctorStep()),
                StepState.valueOf(domain.paymentStep()),
                StepState.valueOf(domain.notificationStep())
        );
    }

    @SneakyThrows
    public Process toDomain(ProcessDto dto) {
        if (dto == null) {
            return null;
        }
        return new Process(
                dto.processId(),
                objectMapper.writeValueAsString(dto.startParams()),
                ProcessExecutionState.EXECUTING.name(),
                dto.prescriptionStep().name(),
                dto.doctorStep().name(),
                dto.paymentStep().name(),
                dto.notificationStep().name()
        );
    }
}
