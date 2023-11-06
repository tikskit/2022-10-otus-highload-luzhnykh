package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.domain.User;

import java.util.Optional;

/**
 * Сервис для сущности Пользователь
 */
public interface UserService {

    /**
     * Получить пользователя по его ИД
     *
     * @param id ИД пользователя
     * @return Пользователь, либо empty если не найден с таким id
     */
    Optional<User> getUser(Long id);
}
