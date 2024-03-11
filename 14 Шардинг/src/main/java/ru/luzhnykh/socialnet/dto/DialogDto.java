package ru.luzhnykh.socialnet.dto;

/**
 * Dto Диалог
 * @param senderId ИД пользователя
 * @param text Текст
 */
public record DialogDto(String senderId, String receiverId, String text) {
}
