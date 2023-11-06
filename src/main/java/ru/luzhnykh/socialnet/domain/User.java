package ru.luzhnykh.socialnet.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.luzhnykh.socialnet.enums.Sex;

/**
 * Доменная сущность Пользователь
 */
@RequiredArgsConstructor
@Data
public class User {
    /**
     * ИД пользователя
     */
    private final Long id;

    /**
     * Имя
     */
    private final String firstName;

    /**
     * Фамилия
     */
    private final String lastName;

    /**
     * Возраст
     */
    private final int age;

    /**
     * Пол
     */
    private final Sex sex;

    /**
     * Интересы
     */
    private final String interests;

    /**
     * Город
     */
    private final String city;
}
