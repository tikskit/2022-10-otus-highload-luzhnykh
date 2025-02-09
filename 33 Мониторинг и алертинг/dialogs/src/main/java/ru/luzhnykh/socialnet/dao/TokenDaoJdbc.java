package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.TokenDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * JDBC реализация DAO токена
 */
@Repository
@RequiredArgsConstructor
public class TokenDaoJdbc implements TokenDao {
    private final JdbcOperations jdbc;

    /**
     * Получить токен из БД
     */
    @Override
    public Optional<TokenDto> get(String token) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("select token from socnet.tokens where token=?",
                            new TokenMapper(), token)
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

    private static class TokenMapper implements RowMapper<TokenDto> {
        @Override
        public TokenDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TokenDto(
                    rs.getString("token")
            );
        }
    }
}
