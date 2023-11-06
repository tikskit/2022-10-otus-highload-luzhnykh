package ru.luzhnykh.socialnet.dto;

import ru.luzhnykh.socialnet.enums.Sex;

import java.time.LocalDate;

/**
 * Dto Пользователь
 *
 * @param accountName Наименование аккакнта (логин)
 * @param firstName   Имя
 * @param lastName    Фамилия
 * @param dob         Дата рождения
 * @param sex         Пол
 * @param biography   Интересы
 * @param city        Город
 */
public record UserDto(String accountName, String firstName, String lastName, LocalDate dob, Sex sex, String biography,
                      String city) {
}
