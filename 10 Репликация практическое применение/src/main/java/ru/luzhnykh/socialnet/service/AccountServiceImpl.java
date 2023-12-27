package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.luzhnykh.socialnet.dao.AccountDao;
import ru.luzhnykh.socialnet.dto.AccountDto;
import ru.luzhnykh.socialnet.dto.LoginDto;

import java.util.Objects;
import java.util.Optional;

/**
 * Реализация сервиса сущности Аккаунт
 */
@Controller
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final LoginDto ROOT = new LoginDto("root", "root");
    private final AccountDao accountDao;
    private final TokenService tokenService;

    private AccountDto convertLoginToAccount(LoginDto loginDto) {
        return new AccountDto(loginDto.id(), loginDto.password().hashCode());
    }

    /**
     * Аутентификация пользователя
     * @param loginDto Данные аутентификации
     * @return Токен, либо empty, если аутентификация не пройдена
     */
    @Override
    public Optional<String> login(LoginDto loginDto) {
        Objects.requireNonNull(loginDto);
        if (ROOT.equals(loginDto) || accountDao.match(convertLoginToAccount(loginDto))) {
            return Optional.of(tokenService.generate());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Добавление нового аккакнта
     */
    @Override
    public void addAccount(AccountDto accountDto) {
        accountDao.addAccount(accountDto);
    }
}
