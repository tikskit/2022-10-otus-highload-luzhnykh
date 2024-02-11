package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.PostDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Dao сущности поста
 */
@Repository
@RequiredArgsConstructor
public class PostDaoJdbc implements PostDao {
    private final JdbcOperations jdbc;
    @Override
    public void create(String postId, String authorUserId, String text) {
        jdbc.update("insert into socnet.posts(postid, authorid, text, postedat) values (?, ?, ?,now())", postId, authorUserId, text);
    }

    @Override
    public void update(String id, String text) {
        jdbc.update("update socnet.posts set text=?,postedat=now() where postid=?", text, id);
    }

    @Override
    public void delete(String id) {
        jdbc.update("delete from socnet.posts where postid=?", id);
    }

    @Override
    public Optional<PostDto> get(String id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("select postid, authorid, text from socnet.posts where postid=?",
                            new PostMapper(), id)
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                return Optional.empty();
            } else {
                throw e;
            }
        }    }

    private static class PostMapper implements RowMapper<PostDto> {
        @Override
        public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PostDto(
                    rs.getString("postid"),
                    rs.getString("text"),
                    rs.getString("authorid")
            );
        }
    }
}
