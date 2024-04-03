package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.domain.Post;
import ru.luzhnykh.socialnet.dto.PostDto;
import ru.luzhnykh.socialnet.repository.PostRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Dao сущности поста
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class PostDaoJdbc implements PostDao {
    private final JdbcOperations jdbc;
    private final PostRepository postRepository;

    @Override
    public void create(String postId, String authorUserId, String text) {
        postRepository.addPost(postId, authorUserId, text);
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
            Post post = postRepository.findByPostId(id);
            return Optional.of(new PostDto(post.getPostId(), post.getText(), post.getAuthorId()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PostDto> getFeed(String userId, Integer offset, Integer limit) {
        return jdbc.query("""
                select p.postid, p."text", p.authorid
                from socnet.users u
                inner join socnet.friends f on f.userid = u.userid
                inner join socnet.posts p on p.authorid = f.friendid
                where u.userid = ?
                order by p.postedat, p.postid
                offset ?
                limit ?
                """, new PostMapper(),
                userId, offset, limit);
    }

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
