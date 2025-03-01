package ru.luzhnykh.socialnet.service;

/**
 * Сервис уведомления об обновлении лент друзей
 */
public interface FeedUpdateNotifierService {
    /**
     * Уведомление всех подключенных пользователей, о создании нового поста. Уведомление отправляется только друзьям
     * автора
     *
     * @param postId       ИД нового поста
     * @param postText     Текст нового сообщения
     * @param authorUserId ИД автора поста
     */
    void afterUpdate(String postId, String postText, String authorUserId);
}
