package ru.luzhnykh.socialnet.dto;

/**
 * Сообщение, которое отправляется по web socket, содержит информацию о новом посте
 *
 * @param postId       ИД нового поста
 * @param postText     Текст нового поста
 * @param authorUserId ИД автора нового поста
 */
public record NewPostMessage(String postId, String postText, String authorUserId) {
}
