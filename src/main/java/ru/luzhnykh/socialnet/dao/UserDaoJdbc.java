package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.domain.User;
import ru.luzhnykh.socialnet.enums.Sex;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        jdbc.update("insert into users(firstname, lastname, age, sex, interests, city) values(?, ?, ?, ?, ?, ?)",
                user.getFirstName(), user.getLastName(), user.getAge(), user.getSex(), user.getInterests(), user.getCity());
    }

    /**
     * Получить пользователя по идентификатору
     *
     * @param id Идентификатор пользователя
     * @return Пользователь
     */
    @Override
    public User getUser(Long id) {
        return jdbc.queryForObject("select id, firstname, lastname, age, sex, interests, city from users where id=?",
                new UserMapper(), id);
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getInt("age"),
                    Sex.getById(rs.getInt("sex")),
                    rs.getString("interests"),
                    rs.getString("city")
            );
        }
    }
}
