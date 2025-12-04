package pokemon.chat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import pokemon.dto.ChatMessage;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint("/chat-ws")
public class ChatWebSocket {
    private static final Logger logger = Logger.getLogger(ChatWebSocket.class.getName());
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final Jsonb jsonb = JsonbBuilder.create();

    @OnOpen
    public void onOpen(Session session) {
        String username = getUsername(session);
        if (username.isBlank()) {
            return;
        }
        sessions.put(username, session);
        logger.info(String.format("New session for trainer: %s", username));
        // TODO: maybe add message like "new trainer joined"
    }

    @OnClose
    public void onClose(Session session) {
        String username = getUsername(session);
        if (username.isBlank()) {
            return;
        }
        sessions.remove(username);
        logger.info(String.format("Closed session for trainer: %s", username));
        // TODO: maybe add message like "trainer left"
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info(String.format("Received message: %s", message));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        String username = getUsername(session);
        logger.warning(String.format("Trainer '%s' crashed: %s", username, error.getMessage()));
        sessions.remove(username);
    }

    public void onChatMessage(@Observes ChatMessageEvent event) {
        ChatMessage message = event.getMessage();
        String jsonPayload = jsonb.toJson(message);
        logger.info(String.format("Sending message from trainer '%s': %s", message.getSender(), message.getContent()));
        if (message.isPublic()) {
            broadcast(jsonPayload);
        } else {
            sendPayload(sessions.get(message.getSender()), jsonPayload);
            sendPayload(sessions.get(message.getRecipient()), jsonPayload);
        }
    }

    public static Set<String> getActiveTrainers() {
        return sessions.keySet();
    }

    private void broadcast(String payload) {
        for (Session session : sessions.values()) {
            sendPayload(session, payload);
        }
    }

    private void sendPayload(Session session, String payload) {
        try {
            if (session == null || !session.isOpen()) {
                return;
            }
            session.getBasicRemote().sendText(payload);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    private String getUsername(Session session) {
        if (session == null) {
            return "";
        }
        String query = session.getQueryString();
        if (query == null || !query.contains("username=")) {
            return "";
        }
        return query.split("username=")[1];
    }
}
