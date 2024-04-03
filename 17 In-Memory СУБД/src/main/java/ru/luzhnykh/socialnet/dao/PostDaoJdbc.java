package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.domain.Post;
import ru.luzhnykh.socialnet.dto.PostDto;
import ru.luzhnykh.socialnet.repository.PostRepository;

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
    public Optional<PostDto> get(String id) {
        try {
            Post post = postRepository.findByPostId(id);
            return Optional.of(new PostDto(post.getPostId(), post.getText(), post.getAuthorId()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
