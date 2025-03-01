package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.PostDao;
import ru.luzhnykh.socialnet.dto.CreatePostDto;
import ru.luzhnykh.socialnet.dto.CreatePostResDto;
import ru.luzhnykh.socialnet.dto.PostDto;
import ru.luzhnykh.socialnet.dto.UpdatePostDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис сущности Пост
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao postDao;
    private final FeedService feedService;
    private final FeedUpdateNotifierService feedUpdateNotifierService;

    @Override
    public CreatePostResDto add(CreatePostDto createPostDto) {
        String postId = UUID.randomUUID().toString();
        postDao.create(postId, createPostDto.author_user_id(), createPostDto.text());
        feedUpdateNotifierService.afterUpdate(postId, createPostDto.text(), createPostDto.author_user_id());
        return new CreatePostResDto(postId);
    }

    @Override
    public void update(UpdatePostDto updatePostDto) {
        postDao.update(updatePostDto.id(), updatePostDto.text());
    }

    @Override
    public void delete(String id) {
        postDao.delete(id);
    }

    @Override
    public Optional<PostDto> get(String id) {
        return postDao.get(id);
    }

    @Override
    public List<PostDto> getFeed(String userId, Integer offset, Integer limit) {
        return feedService.getFeed(userId);
    }
}
