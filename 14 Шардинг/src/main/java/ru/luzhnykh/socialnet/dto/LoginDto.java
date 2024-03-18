package ru.luzhnykh.socialnet.dto;

/**
 * Dto для body эндпойнта /userId
 *
 * @param id       ИД пользователя
 * @param password Пароль
 */
public record LoginDto(String id, String password) {
}
