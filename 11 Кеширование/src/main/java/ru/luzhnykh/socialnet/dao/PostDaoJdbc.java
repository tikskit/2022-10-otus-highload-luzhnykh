package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

/**
 * Dao сущности поста
 */
@Repository
@RequiredArgsConstructor
public class PostDaoJdbc implements PostDao {
    private final JdbcOperations jdbc;
    @Override
    public void create(String postId, String authorUserId, String text) {
        jdbc.update("insert into socnet.posts(postid, author_id, text) values (?, ?, ?)", postId, authorUserId, text);
    }

    @Override
    public void update(String id, String text) {
        jdbc.update("update socnet.posts set text=? where postid=?", text, id);
    }

    @Override
    public void delete(String id) {
        jdbc.update("delete from socnet.posts where postid=?", id);
    }
}
