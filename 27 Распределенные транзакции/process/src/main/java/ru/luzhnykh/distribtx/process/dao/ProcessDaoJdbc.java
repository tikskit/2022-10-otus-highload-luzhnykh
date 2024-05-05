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
    public Optional<Process> findById(String processId) {
        try {
            Process process = jdbc.queryForObject(
                    """
                            select processId, params, state, prescription_step, doctor_step, payment_step, notification_step 
                            from processes.processes where processId=?
                            """,
                    new ProcessMapper(), processId);
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
                    rs.getString("processId"),
                    rs.getString("params"),
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
                        insert into processes.processes(processId, params, state, prescription_step, doctor_step, payment_step, notification_step)
                        values(?, ?, ?, ?, ?, ?, ?)
                        on conflict(processId) do update
                        set
                            state = excluded.state,
                            params = excluded.params,
                            prescription_step = excluded.prescription_step,
                            doctor_step = excluded.doctor_step,
                            payment_step = excluded.payment_step,
                            notification_step = excluded.notification_step
                        """,
                process.processId(), process.params(), process.state(), process.prescriptionStep(), process.doctorStep(), process.paymentStep(),
                process.notificationStep());
    }
}
