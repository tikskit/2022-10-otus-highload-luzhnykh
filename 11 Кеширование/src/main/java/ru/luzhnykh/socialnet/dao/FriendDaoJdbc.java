package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности Друг
 */
@Repository
@RequiredArgsConstructor
public class FriendDaoJdbc implements FriendDao {

    private final JdbcOperations jdbc;

    /**
     * Добавить друга
     *
     * @param userId   ИД пользователя, которому добавляем друга
     * @param friendId ИД друга
     */
    @Override
    public void add(String userId, String friendId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("Не задан userid");
        }
        if (Strings.isBlank(friendId)) {
            throw new IllegalArgumentException("Не задан friendId");
        }
        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("Нельзя добавить в друзья самого себя");
        }
        jdbc.update("insert into socnet.friends(userid, friendid) values(?, ?) on conflict do nothing;", userId, friendId);
    }

    /**
     * Отфрендить друга
     *
     * @param userId   ИД пользователя, у которого отфрендиваем
     * @param friendId ИД друга
     */
    @Override
    public void delete(String userId, String friendId) {
        jdbc.update("delete from socnet.friends where userid=? and friendid=?", userId, friendId);
    }
}
