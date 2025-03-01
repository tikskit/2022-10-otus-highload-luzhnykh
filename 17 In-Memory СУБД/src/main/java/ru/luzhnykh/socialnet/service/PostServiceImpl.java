package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.PostDao;
import ru.luzhnykh.socialnet.dto.CreatePostDto;
import ru.luzhnykh.socialnet.dto.CreatePostResDto;
import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.Optional;
import java.util.UUID;

/**
 * Сервис сущности Пост
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDao postDao;

    @Override
    public CreatePostResDto add(CreatePostDto createPostDto) {
        String postId = UUID.randomUUID().toString();
        postDao.create(postId, createPostDto.author_user_id(), createPostDto.text());
        return new CreatePostResDto(postId);
    }

    @Override
    public Optional<PostDto> get(String id) {
        return postDao.get(id);
    }

}
