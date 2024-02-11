package ru.luzhnykh.socialnet.dao;

/**
 * Dao сущности поста
 */
public interface PostDao {
    /**
     * добавить новый пост
     *
     * @param postId ИД поста
     * @param authorUserId ИД автора
     * @param text         Текст поста
     * @return ИД поста
     */
    void create(String postId, String authorUserId, String text);

    /**
     * Обновить пост
     *
     * @param id   ИД поста
     * @param text Текст поста
     */
    void update(String id, String text);

    /**
     * Удалить пост
     * @param id ИД поста
     */
    void delete(String id);
}
