package ru.luzhnykh.socialnet.repository;

import org.springframework.data.tarantool.repository.Query;
import org.springframework.data.tarantool.repository.TarantoolRepository;
import ru.luzhnykh.socialnet.domain.Post;

import java.util.Optional;

public interface PostRepository extends TarantoolRepository<Post, String> {
    @Query(function = "insert_post")
    Post addPost(String postid, String authorid, String text);
    Post save(Post post);

    @Query(function = "get_post_by_id")
    Optional<Post> findByPostId(String postId);
}
