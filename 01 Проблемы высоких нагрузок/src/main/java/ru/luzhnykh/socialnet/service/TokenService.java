package ru.luzhnykh.socialnet.service;

/**
 * Сервис токенов. Выдает и валидирует токены
 */
public interface TokenService {
    /**
     * Сгенерировать новый токен
     */
    String generate();

    /**
     * Является ли токен валидным
     *
     * @param token Токен для проверки
     * @return true если токен валидный, иначе false
     */
    boolean validate(String token);
}
