package ru.luzhnykh.socialnet.dto;

/**
 * Dto аккаунт пользователя
 *
 * @param login    Логин аккакнта
 * @param passHash Хэш пароля
 */
public record AccountDto(String login, Integer passHash) {

}
