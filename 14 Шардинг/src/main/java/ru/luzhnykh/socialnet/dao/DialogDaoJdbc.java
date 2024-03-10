package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.DialogDto;

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
        jdbc.update("insert into socnet.dialogs(userid, text) values(?, ?)", dialog.userid(), dialog.text());
    }
}
