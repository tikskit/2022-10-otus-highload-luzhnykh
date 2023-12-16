package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.AccountDto;
import ru.luzhnykh.socialnet.dto.LoginDto;

import java.util.Optional;

/**
 * Сервис сущности Аккаунт
 */
public interface AccountService {

    /**
     * Аутентификация пользователя
     *
     * @param loginDto Данные аутентификации
     * @return Токен, либо empty, если аутентификация не пройдена
     */
    Optional<String> login(LoginDto loginDto);

    /**
     * Добавление нового аккакнта
     */
    void addAccount(AccountDto accountDto);
}
