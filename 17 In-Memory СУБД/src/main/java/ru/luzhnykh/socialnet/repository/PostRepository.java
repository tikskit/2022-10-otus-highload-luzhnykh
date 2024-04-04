package ru.luzhnykh.socialnet.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.tarantool.repository.Query;
import org.springframework.data.tarantool.repository.TarantoolRepository;
import ru.luzhnykh.socialnet.domain.Post;

public interface PostRepository extends TarantoolRepository<Post, String> {
    @Query(function = "insert_post")
    Post addPost(String postid, String authorid, String text);
    Post save(Post post);

    @Query(function = "get_post_by_id")
    Post findByPostId(String postId) throws DataAccessException;
}
