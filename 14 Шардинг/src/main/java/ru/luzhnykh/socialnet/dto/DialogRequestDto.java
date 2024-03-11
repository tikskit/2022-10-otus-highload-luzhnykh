package ru.luzhnykh.socialnet.dto;

/**
 * Запрос на добавления сообщения диалога
 *
 * @param text       Текст сообщения
 */
public record DialogRequestDto(String text) {
}
