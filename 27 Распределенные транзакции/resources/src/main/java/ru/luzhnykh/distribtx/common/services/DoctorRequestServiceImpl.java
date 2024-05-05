package ru.luzhnykh.distribtx.common.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.common.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.common.dto.DoctorRequestResponseDto;
import ru.luzhnykh.distribtx.common.dao.DoctorRequestDao;
import ru.luzhnykh.distribtx.common.domain.DoctorRequest;
import ru.luzhnykh.distribtx.common.enums.DoctorRequestRetCodes;
import ru.luzhnykh.distribtx.common.events.DoctorRequestReplyPublisher;
import ru.luzhnykh.distribtx.common.mapper.DoctorRequestMapper;

/**
 * Реализация сервиса резервирования врачей
 */
@Service
@RequiredArgsConstructor
public class DoctorRequestServiceImpl implements DoctorRequestService {

    private static final Logger log = LoggerFactory.getLogger(DoctorRequestServiceImpl.class);
    private final DoctorRequestDao doctorRequestDao;
    private final DoctorRequestReplyPublisher replyPublisher;
    private final DoctorRequestMapper mapper;

    @Override
    public void addRequest(@NonNull DoctorRequestDto requestDto) {
        DoctorRequestResponseDto responseDto;
        DoctorRequest domain = mapper.toDomain(requestDto);
        try {
            if (doctorRequestDao.activeExist(domain)) {
                responseDto = new DoctorRequestResponseDto(requestDto.processId(), DoctorRequestRetCodes.BUSY.getCode(),
                        "", requestDto.action());
            } else {
                doctorRequestDao.addRequest(domain);
                responseDto = new DoctorRequestResponseDto(requestDto.processId(), DoctorRequestRetCodes.SUCCESS.getCode(),
                        "", requestDto.action());
            }
        } catch (Exception e) {
            responseDto = new DoctorRequestResponseDto(requestDto.processId(), DoctorRequestRetCodes.EXCEPTION.getCode(),
                    e.getMessage(), requestDto.action());
            log.error("Ошибка при добавлении заявки", e);
        }

        replyPublisher.publish(responseDto);
    }

    @Override
    public void cancelRequest(DoctorRequestDto requestDto) {
        DoctorRequestResponseDto responseDto;
        try {
            DoctorRequest domain = mapper.toDomain(requestDto);
            doctorRequestDao.cancelRequest(domain);
            responseDto = new DoctorRequestResponseDto(requestDto.processId(), DoctorRequestRetCodes.SUCCESS.getCode(),
                    "", requestDto.action());
        } catch (Exception e) {
            responseDto = new DoctorRequestResponseDto(requestDto.processId(), DoctorRequestRetCodes.EXCEPTION.getCode(),
                    e.getMessage(), requestDto.action());
            log.error("Ошибка при отмене заявки", e);
        }

        replyPublisher.publish(responseDto);
    }
}
