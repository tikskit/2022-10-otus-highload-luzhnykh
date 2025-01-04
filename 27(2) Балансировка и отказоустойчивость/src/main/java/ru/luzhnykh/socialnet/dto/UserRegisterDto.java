package ru.luzhnykh.socialnet.dto;

import java.time.LocalDate;

/**
 * Dto регистрации пользователя
 *
 * @param first_name  Имя
 * @param second_name Фамилия
 * @param birthdate   Дата рождения
 * @param biography   Интересы
 * @param city        Город
 * @param password    Пароль
 */
public record UserRegisterDto(String first_name, String second_name, LocalDate birthdate, String biography,
                              String city, String password) {
}
