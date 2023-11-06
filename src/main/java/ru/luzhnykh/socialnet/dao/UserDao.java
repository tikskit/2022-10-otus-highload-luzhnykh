package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.domain.User;

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
     * @return
     */
    User getUser(Long id);
}
