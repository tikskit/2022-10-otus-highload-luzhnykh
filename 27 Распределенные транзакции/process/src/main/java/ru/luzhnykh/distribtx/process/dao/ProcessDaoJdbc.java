package ru.luzhnykh.distribtx.process.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.distribtx.process.domain.Process;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProcessDaoJdbc implements ProcessDao {

    private final JdbcOperations jdbc;

    @Override
    public Optional<Process> findById(String requestId) {
        try {
            Process process = jdbc.queryForObject(
                    """
                            select requestId, state, prescription_step, doctor_step, payment_step, notification_step 
                            from processes.processes where requestId=?
                            """,
                    new ProcessMapper(), requestId);
            return Optional.ofNullable(process);
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

    private static class ProcessMapper implements RowMapper<Process> {
        @Override
        public Process mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Process(
                    rs.getString("requestId"),
                    rs.getString("state"),
                    rs.getString("prescription_step"),
                    rs.getString("doctor_step"),
                    rs.getString("payment_step"),
                    rs.getString("notification_step")
            );
        }
    }

    @Override
    public void update(Process process) {
        jdbc.update(
                """
                        update processes.processes
                        set
                            state = ?,
                            prescription_step = ?,
                            doctor_step = ?,
                            payment_step = ?,
                            notification_step = ?
                        where requestId=?
                        """,
                process.state(), process.prescriptionStep(), process.doctorStep(), process.paymentStep(),
                process.notificationStep(), process.requestId());
    }
}
