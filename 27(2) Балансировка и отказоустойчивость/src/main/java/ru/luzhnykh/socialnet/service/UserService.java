package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.UserDto;
import ru.luzhnykh.socialnet.dto.UserRegisterDto;

import java.util.List;
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
    Optional<UserDto> getUser(String id);

    /**
     * Зарегистрировать нового пользователя
     *
     * @param regUser Данные нового пользователя
     * @return ИД нового пользователя, либо empty если пользователь с таким Ид уже есть
     */
    String register(UserRegisterDto regUser);

    /**
     * Выполняет поиск пользоваетелй по имени и фамилии
     *
     * @param firstName Имя пользователя
     * @param lastName  Фамилия пользователя
     * @return Найденные пользователи
     */
    List<UserDto> search(String firstName, String lastName);
}
