package ru.luzhnykh.socialnet.dto;

/**
 * Dto друг пользователя
 * @param userId ИД пользователя
 * @param friendId ИД друга пользователя
 */
public record FriendDto(String userId, String friendId) {
}
