package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.CreatePostDto;
import ru.luzhnykh.socialnet.dto.CreatePostResDto;
import ru.luzhnykh.socialnet.dto.PostDto;
import ru.luzhnykh.socialnet.dto.UpdatePostDto;

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
     * Обновить пост
     */
    void update(UpdatePostDto updatePostDto);

    /**
     * Удалить пост
     *
     * @param id ИД поста
     */
    void delete(String id);

    /**
     * Получить пост по идентификатору
     *
     * @param id ИД поста
     * @return Dto сущности пост
     */
    Optional<PostDto> get(String id);
}
