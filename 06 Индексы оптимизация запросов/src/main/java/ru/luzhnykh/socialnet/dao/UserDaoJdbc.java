package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    public void addUser(UserDto user) {
        jdbc.update("insert into socnet.users(userid, firstname, lastname, dob, biography, city) values(?, ?, ?, ?, ?, ?)",
                user.id(), user.first_name(), user.second_name(), user.birthdate(), user.biography(), user.city());
    }

    /**
     * Получить пользователя по идентификатору
     *
     * @param id Идентификатор пользователя
     * @return Пользователь
     */
    @Override
    public Optional<UserDto> getUser(String id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("select userid, firstname, lastname, dob, biography, city from socnet.users where userid=?",
                            new UserMapper(), id)
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

    private static class UserMapper implements RowMapper<UserDto> {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserDto(
                    rs.getString("userid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getDate("dob").toLocalDate(),
                    rs.getString("biography"),
                    rs.getString("city")
            );
        }
    }

    /**
     * Выполняет поиск пользоваетелй по имени и фамилии
     *
     * @param firstName Имя пользователя
     * @param lastName  Фамилия пользователя
     * @return Найденные пользователи
     */
    @Override
    public List<UserDto> search(String firstName, String lastName) {
        return jdbc.query(
                "select userid, firstname, lastname, dob, biography, city from socnet.users where firstname=? and lastname=?",
                new UserMapper(), firstName, lastName);
    }
}
