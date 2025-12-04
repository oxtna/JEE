package pokemon.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import pokemon.chat.ChatMessageEvent;
import pokemon.chat.ChatWebSocket;
import pokemon.entity.Trainer;
import pokemon.service.TrainerService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

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

    public String getUsername() {
        if (securityContext == null || securityContext.getCallerPrincipal() == null) {
            return null;
        }
        return securityContext.getCallerPrincipal().getName();
    }

    public void sendMessage() {
        // TODO
    }

    public Collection<Trainer> getOtherTrainers() {
        String username = getUsername();
        return trainerService.findAll()
                .stream()
                .filter(t -> !t.getLogin().equals(username))
                .toList();
    }

    public Set<String> getActiveTrainers() {
        return ChatWebSocket.getActiveTrainers();
    }

    public boolean isTrainerActive(String username) {
        return true;
    }
}
