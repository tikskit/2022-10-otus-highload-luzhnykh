package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.luzhnykh.socialnet.dao.UserDao;
import ru.luzhnykh.socialnet.dto.AccountDto;
import ru.luzhnykh.socialnet.dto.UserDto;
import ru.luzhnykh.socialnet.dto.UserRegisterDto;
import ru.luzhnykh.socialnet.exceptions.IllegalUserIdException;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса для сущности Пользователь
 */
@Controller
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountService accountService;
    private final UserDao userDao;

    /**
     * Получить пользователя по его ИД
     *
     * @param id ИД пользователя
     * @return Пользователь, либо empty если не найден с таким id
     */
    @Override
    public Optional<UserDto> getUser(String id) {
        if (id == null) {
            throw new IllegalUserIdException(String.format("Недопустимый ИД пользователя: %s", id));
        }
        return userDao.getUser(id);
    }

    private AccountDto convertUserRegisterDtoToAccountDto(String userId, UserRegisterDto regUser) {
        if (regUser == null) {
            return null;
        } else {
            return new AccountDto(userId, regUser.password().hashCode());
        }
    }

    private UserDto convertUserRegisterDtoToUserDto(String userId, UserRegisterDto regUser) {
        if (regUser == null) {
            return null;
        } else {
            return new UserDto(userId, regUser.first_name(), regUser.second_name(), regUser.birthdate(),  regUser.biography(),
                    regUser.city());
        }
    }

    /**
     * Зарегистрировать нового пользователя
     *
     * @param regUser Данные нового пользователя
     * @return ИД нового пользователя
     */
    @Override
    public String register(UserRegisterDto regUser) {
        String userId = UUID.randomUUID().toString();
        AccountDto account = convertUserRegisterDtoToAccountDto(userId, regUser);
        UserDto newUser = convertUserRegisterDtoToUserDto(userId, regUser);
        accountService.addAccount(account);
        userDao.addUser(newUser);
        return userId;
    }
}
