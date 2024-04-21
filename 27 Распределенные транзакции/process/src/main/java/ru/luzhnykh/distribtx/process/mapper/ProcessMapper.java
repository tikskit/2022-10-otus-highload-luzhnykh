package ru.luzhnykh.distribtx.process.mapper;

import org.springframework.stereotype.Component;
import ru.luzhnykh.distribtx.process.domain.Process;
import ru.luzhnykh.distribtx.process.dto.ProcessDto;
import ru.luzhnykh.distribtx.process.enums.ProcessState;
import ru.luzhnykh.distribtx.process.enums.StepState;

@Component
public class ProcessMapper {
    public ProcessDto toDto(Process domain) {
        if (domain == null) {
            return null;
        }

        return new ProcessDto(
                domain.requestId(),
                ProcessState.valueOf(domain.state()),
                StepState.valueOf(domain.prescriptionStep()),
                StepState.valueOf(domain.doctorStep()),
                StepState.valueOf(domain.paymentStep()),
                StepState.valueOf(domain.notificationStep())
        );
    }
}
