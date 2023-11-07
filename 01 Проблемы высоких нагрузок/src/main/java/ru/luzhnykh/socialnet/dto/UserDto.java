package ru.luzhnykh.socialnet.dto;

import java.time.LocalDate;

/**
 * Dto Пользователь
 *
 * @param id          ИД пользователя
 * @param first_name  Имя
 * @param second_name Фамилия
 * @param birthdate   Дата рождения
 * @param biography   Интересы
 * @param city        Город
 */
public record UserDto(String id, String first_name, String second_name, LocalDate birthdate, String biography,
                      String city) {
}
