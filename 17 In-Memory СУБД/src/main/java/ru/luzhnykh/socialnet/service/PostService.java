package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.CreatePostDto;
import ru.luzhnykh.socialnet.dto.CreatePostResDto;
import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.Optional;

/**
 * Сервис сущности Post
 */
public interface PostService {
    /**
     * Добавить пост
     *
     * @param createPostDto Текст поста
     */
    CreatePostResDto add(CreatePostDto createPostDto);

    /**
     * Получить пост по идентификатору
     *
     * @param id ИД поста
     * @return Dto сущности пост
     */
    Optional<PostDto> get(String id);
}
