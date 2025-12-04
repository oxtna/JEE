package pokemon.chat;

import pokemon.dto.ChatMessage;

import java.io.Serializable;

public class ChatMessageEvent implements Serializable {
    private ChatMessage message;

    public ChatMessageEvent() {}

    public ChatMessageEvent(ChatMessage message) {
        this.message = message;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
