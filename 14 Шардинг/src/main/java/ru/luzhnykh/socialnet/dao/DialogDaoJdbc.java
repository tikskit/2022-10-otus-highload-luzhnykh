package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.AccountDto;
import ru.luzhnykh.socialnet.dto.DialogDto;
import ru.luzhnykh.socialnet.dto.DialogMessageDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * JDBC реализация DialogDao
 */
@Repository
@RequiredArgsConstructor
public class DialogDaoJdbc implements DialogDao {

    private final JdbcOperations jdbc;

    /**
     * Добавить диалог
     */
    @Override
    public void add(DialogDto dialog) {
        jdbc.update("insert into socnet.dialogs(senderid, receiverid, text, datetime) values(?, ?, ?, ?)", dialog.senderId(),
                dialog.receiverId(), dialog.text(), OffsetDateTime.now());
    }

    /**
     * Получить все сообщения, отправленные пользователем senderId для пользователя senderId и упорядоченные по времени отправки
     */
    @Override
    public List<DialogMessageDto> getMessage(String senderId, String receiverId) {
        List<DialogMessageDto> query = jdbc.query("""
                select senderid, receiverid, text, datetime
                from socnet.dialogs
                where senderid=? and receiverid=?
                order by datetime
                """, new DialogMapper(), senderId, receiverId);
        return query;
    }

    private static class DialogMapper implements RowMapper<DialogMessageDto> {
        @Override
        public DialogMessageDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            OffsetDateTime datetime = rs.getTimestamp("datetime").toInstant().atOffset(OffsetDateTime.now().getOffset());
            return new DialogMessageDto(
                    rs.getString("senderid"),
                    rs.getString("receiverid"),
                    rs.getString("text"),
                    datetime
            );
        }
    }
}
