package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.UserDto;

import java.util.List;
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

    /**
     * Выполняет поиск пользоваетелй по имени и фамилии
     *
     * @param firstName Имя пользователя
     * @param lastName  Фамилия пользователя
     * @return Найденные пользователи
     */
    List<UserDto> search(String firstName, String lastName);
}
