package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.luzhnykh.socialnet.dao.UserDao;
import ru.luzhnykh.socialnet.domain.User;
import ru.luzhnykh.socialnet.exceptions.IllegalUserIdException;

import java.util.Objects;
import java.util.Optional;

/**
 * Реализация сервиса для сущности Пользователь
 */
@Controller
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    /**
     * Получить пользователя по его ИД
     *
     * @param id ИД пользователя
     * @return Пользователь, либо empty если не найден с таким id
     */
    @Override
    public Optional<User> getUser(Long id) {
        if (id == null) {
            throw new IllegalUserIdException(String.format("Недопустимый ИД пользователя: %s", id));
        }
        return userDao.getUser(id);
    }
}
