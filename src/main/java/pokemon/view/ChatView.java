package pokemon.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Event;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import pokemon.chat.ChatMessageEvent;
import pokemon.chat.ChatWebSocket;
import pokemon.dto.ChatMessage;
import pokemon.entity.Trainer;
import pokemon.service.TrainerService;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ChatView implements Serializable {
    private static final Logger logger = Logger.getLogger(ChatView.class.getName());

    @Inject
    private SecurityContext securityContext;

    @Inject
    private TrainerService trainerService;

    @Inject
    private Event<ChatMessageEvent> chatMessageEvent;

    private String messageContent;
    private String selectedRecipient;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSelectedRecipient() {
        return selectedRecipient;
    }

    public void setSelectedRecipient(String selectedRecipient) {
        this.selectedRecipient = selectedRecipient;
    }

    public String getCurrentUsername() {
        if (securityContext != null && securityContext.getCallerPrincipal() != null) {
            return securityContext.getCallerPrincipal().getName();
        }
        return null;
    }

    public void sendMessage() {
        String sender = getCurrentUsername();

        if (sender == null) {
            addErrorMessage("You must be logged in to send a message.");
            return;
        }

        if (messageContent == null || messageContent.trim().isEmpty()) {
            addErrorMessage("Message content cannot be empty.");
            return;
        }

        boolean isPrivate = selectedRecipient != null && !selectedRecipient.trim().isEmpty()
                && !"ALL".equals(selectedRecipient);

        ChatMessage message = new ChatMessage(
                sender,
                isPrivate ? selectedRecipient : null,
                messageContent.trim(),
                LocalDateTime.now(),
                isPrivate
        );

        logger.info("Sending chat message from " + sender +
                " to " + (isPrivate ? selectedRecipient : "ALL") +
                ": " + messageContent);

        chatMessageEvent.fire(new ChatMessageEvent(message));
        messageContent = "";
    }

    public List<Trainer> getAllUsers() {
        String currentUser = getCurrentUsername();
        return trainerService.findAll().stream()
                .filter(user -> !user.getLogin().equals(currentUser))
                .collect(Collectors.toList());
    }

    public Set<String> getOnlineUsers() {
        return ChatWebSocket.getOnlineUsers();
    }

    public boolean isUserOnline(String username) {
        return ChatWebSocket.getOnlineUsers().contains(username);
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
}
