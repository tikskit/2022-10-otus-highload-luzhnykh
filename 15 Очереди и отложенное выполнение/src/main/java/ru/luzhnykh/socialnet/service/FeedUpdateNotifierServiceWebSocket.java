package ru.luzhnykh.socialnet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import ru.luzhnykh.socialnet.dto.NewPostMessage;

import java.io.IOException;
import java.util.Set;

/**
 * Реализация сервиса уведомлений по протоколу WebSocket
 */
@Service
@RequiredArgsConstructor
public class FeedUpdateNotifierServiceWebSocket implements FeedUpdateNotifierService {

    private final FeedWebSocketHandler feedWebSocketHandler;
    private final FriendService friendService;
    private final ObjectMapper objectMapper;

    /**
     * Уведомление всех подключенных пользователей, о создании нового поста. Уведомление отправляется только друзьям
     * автора
     *
     * @param postId       ИД нового поста
     * @param postText     Текст нового сообщения
     * @param authorUserId ИД автора поста
     */
    @Override
    @SneakyThrows
    public void afterUpdate(String postId, String postText, String authorUserId) {
        Set<String> friends = friendService.getFriendsOf(authorUserId);
        NewPostMessage newPostMessage = new NewPostMessage(postId, postText, authorUserId);
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsBytes(newPostMessage));
        feedWebSocketHandler.webSocketSessions
                .forEach(
                        s -> {
                            String friendId = (String) s.getAttributes().get(FeedWebSocketHandler.USER_ID_PARAM);
                            if (friends.contains(friendId)) {
                                try {
                                    s.sendMessage(textMessage);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                );
    }
}
