package ru.luzhnykh.socialnet.dto;

/**
 * Dto аккаунт пользователя
 *
 * @param userId   Логин аккакнта
 * @param passHash Хэш пароля
 */
public record AccountDto(String userId, Integer passHash) {

}
