package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.domain.User;
import ru.luzhnykh.socialnet.enums.Sex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Реализация UserDao на Jdbc
 */
@Repository
@RequiredArgsConstructor
public class UserDaoJdbc implements UserDao {
    private final JdbcOperations jdbc;

    /**
     * Добавить пользователя
     *
     * @param user Пользователь
     */
    @Override
    public void addUser(User user) {
        jdbc.update("insert into socnet.users(firstname, lastname, dob, sex, biography, city) values(?, ?, ?, ?, ?, ?)",
                user.getFirstName(), user.getLastName(), user.getDob(), user.getSex(), user.getBiography(), user.getCity());
    }

    /**
     * Получить пользователя по идентификатору
     *
     * @param id Идентификатор пользователя
     * @return Пользователь
     */
    @Override
    public Optional<User> getUser(Long id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("select id, firstname, lastname, dob, sex, biography, city from socnet.users where id=?",
                            new UserMapper(), id)
            );
        } catch(IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getDate("dob").toLocalDate(),
                    Sex.getById(rs.getInt("sex")),
                    rs.getString("biography"),
                    rs.getString("city")
            );
        }
    }
}
