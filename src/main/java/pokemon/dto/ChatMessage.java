package pokemon.dto;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String sender;
    private String recipient;
    private String content;

    public ChatMessage() {}

    public ChatMessage(String sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isForUser(String username) {
        return isPublic() || username.equals(sender) || username.equals(recipient);
    }

    public boolean isPublic() {
        return recipient == null || recipient.isBlank();
    }
}
