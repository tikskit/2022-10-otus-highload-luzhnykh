package ru.luzhnykh.distribtx.resources.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.distribtx.resources.domain.DoctorRequest;
import ru.luzhnykh.distribtx.resources.enums.DoctorRequestState;

/**
 * Реализация Dao для работы с заявками на врачей на Jdbc
 */
@Repository
@RequiredArgsConstructor
public class DoctorRequestDaoJdbc implements DoctorRequestDao {

    private final JdbcOperations jdbc;

    @Override
    public void addRequest(DoctorRequest request) {
        jdbc.update("""
                        insert into resources.doctor_requests(requestid, doctorid, prescriptionid, \"start\", \"end\", state)
                        values(?, ?, ?, ?, ?, ?)
                        """,
                request.requestId(), request.doctorId(), request.prescriptionId(), request.start(),
                request.end(), DoctorRequestState.ARRANGED.name());
    }

    @Override
    public boolean activeExist(@NonNull DoctorRequest request) {
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
                        Boolean.class, request.doctorId(), request.start(), request.end(), DoctorRequestState.ARRANGED.name())
        );
    }

    @Override
    public void cancelRequest(DoctorRequest request) {
        jdbc.update(
                """
                        update resources.doctor_requests
                        set state = ?
                        where requestid = ?
                        """,
                DoctorRequestState.CANCELLED.name(), request.requestId()
        );
    }
}
