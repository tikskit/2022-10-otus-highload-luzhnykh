package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.FriendDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<FriendDto> getFriendsOf(String userId) {
        return jdbc.query("select userid, friendid from socnet.friends where userid = ?"
                , new FriendMapper()
                , userId
        );
    }

    private static class FriendMapper implements RowMapper<FriendDto> {
        @Override
        public FriendDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new FriendDto(
                    rs.getString("userid"),
                    rs.getString("friendid")
            );
        }
    }
}
