package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.UserDto;

import java.util.Optional;

/**
 * DAO пользователя
 */
public interface UserDao {
    /**
     * Добавить пользователя
     *
     * @param user Пользователь
     */
    void addUser(UserDto user);

    /**
     * Получить пользователя по идентификатору
     *
     * @param id Идентификатор пользователя
     */
    Optional<UserDto> getUser(String id);
}
