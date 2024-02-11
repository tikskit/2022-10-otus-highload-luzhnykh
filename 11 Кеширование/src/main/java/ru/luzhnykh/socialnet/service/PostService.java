package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.CreatePostDto;
import ru.luzhnykh.socialnet.dto.CreatePostResDto;
import ru.luzhnykh.socialnet.dto.UpdatePostDto;

/**
 * Сервис сущности Post
 */
public interface PostService {
    /**
     * Добавить пост
     * @param createPostDto Текст поста
     */
    CreatePostResDto add(CreatePostDto createPostDto);

    /**
     * Обновить пост
     */
    void update(UpdatePostDto updatePostDto);

    /**
     * Удалить пост
     * @param id ИД поста
     */
    void delete(String id);
}
