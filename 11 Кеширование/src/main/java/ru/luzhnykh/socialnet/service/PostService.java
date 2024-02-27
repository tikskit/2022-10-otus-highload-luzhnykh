package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.CreatePostDto;
import ru.luzhnykh.socialnet.dto.CreatePostResDto;
import ru.luzhnykh.socialnet.dto.PostDto;
import ru.luzhnykh.socialnet.dto.UpdatePostDto;

import java.util.List;
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

    /**
     * Получить ленту друзей пользователя
     *
     * @param userId ИД пользователя
     * @param offset Оффсет постов друзей
     * @param limit  Количество постов друзей
     * @return Список постов друзей
     */
    List<PostDto> getFeed(String userId, Integer offset, Integer limit);
}
