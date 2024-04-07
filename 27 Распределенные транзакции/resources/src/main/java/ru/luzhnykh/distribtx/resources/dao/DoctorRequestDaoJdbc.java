package ru.luzhnykh.distribtx.resources.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.enums.DoctorRequestState;

/**
 * Реализация Dao для работы с заявками на врачей на Jdbc
 */
@Repository
@RequiredArgsConstructor
public class DoctorRequestDaoJdbc implements DoctorRequestDao {

    private final JdbcOperations jdbc;

    @Override
    public void addRequest(DoctorRequestDto requestDto) {
        jdbc.update("""
                        insert into resources.doctor_requests(requestid, doctorid, prescriptionid, \"start\", \"end\", state)
                        values(?, ?, ?, ?, ?, ?)
                        """,
                requestDto.requestId(), requestDto.doctorId(), requestDto.prescriptionId(), requestDto.start(),
                requestDto.end(), DoctorRequestState.ARRANGED.name());
    }

    @Override
    public boolean activeExist(@NonNull DoctorRequestDto requestDto) {
        return Boolean.TRUE.equals(
                jdbc.queryForObject("""
                                select exists(
                                                select *
                                                from resources.doctor_requests r
                                                where r.doctorid = ?
                                                    and r."start" <= ?
                                                    and r."end" >= ?
                                                    and r.state = ?
                                ) as ex
                                """,
                        Boolean.class, requestDto.doctorId(), requestDto.start(), requestDto.end(), DoctorRequestState.ARRANGED.name())
        );
    }
}
