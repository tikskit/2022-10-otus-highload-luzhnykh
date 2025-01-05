package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.luzhnykh.socialnet.dao.AccountDao;
import ru.luzhnykh.socialnet.dto.AccountDto;
import ru.luzhnykh.socialnet.dto.LoginDto;

/**
 * Реализация сервиса сущности Аккаунт
 */
@Controller
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final LoginDto ROOT = new LoginDto("root", "root");
    private final AccountDao accountDao;

    private AccountDto convertLoginToAccount(LoginDto loginDto) {
        return new AccountDto(loginDto.id(), loginDto.password().hashCode());
    }

    /**
     * Добавление нового аккакнта
     */
    @Override
    public void addAccount(AccountDto accountDto) {
        accountDao.addAccount(accountDto);
    }
}
