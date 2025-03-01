package ru.luzhnykh.socialnet.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Обработчик событий веб-сокетов
 */
@Service
public class FeedWebSocketHandler extends TextWebSocketHandler {

    public static final String USER_ID_PARAM = "user_id";

    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessions.remove(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        URI uri = session.getUri();
        if (uri != null) {
            Map<String, String> parameters = UriComponentsBuilder.fromUri(uri).build().getQueryParams().toSingleValueMap();
            String userId = parameters.get(USER_ID_PARAM);
            if (StringUtils.isNotBlank(userId)) {
                webSocketSessions.add(session);
                session.getAttributes().put(USER_ID_PARAM, userId);
            }
        }
    }
}
