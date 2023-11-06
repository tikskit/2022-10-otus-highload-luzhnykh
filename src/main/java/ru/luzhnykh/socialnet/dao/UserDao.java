package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.domain.User;

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
    void addUser(User user);

    /**
     * Получить пользователя по идентификатору
     *
     * @param id Идентификатор пользователя
     */
    Optional<User> getUser(Long id);
}
