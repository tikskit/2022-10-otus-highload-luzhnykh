package ru.luzhnykh.socialnet.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.luzhnykh.socialnet.enums.Sex;

import java.time.LocalDate;

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
     * Дата рождения
     */
    private final LocalDate dob;

    /**
     * Пол
     */
    private final Sex sex;

    /**
     * Интересы
     */
    private final String biography;

    /**
     * Город
     */
    private final String city;
}
