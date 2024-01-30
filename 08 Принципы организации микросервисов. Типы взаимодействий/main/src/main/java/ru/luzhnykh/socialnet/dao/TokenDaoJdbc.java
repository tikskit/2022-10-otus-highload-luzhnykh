package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.TokenDto;

/**
 * JDBC реализация DAO токена
 */
@Repository
@RequiredArgsConstructor
public class TokenDaoJdbc implements TokenDao {
    private final JdbcOperations jdbc;

    /**
     * добавить токен
     */
    @Override
    public void add(TokenDto token) {
        jdbc.update("insert into socnet.tokens(token) values(?)", token.token());
    }
}
