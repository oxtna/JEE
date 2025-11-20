package pokemon.controller.simple;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pokemon.entity.Avatar;
import pokemon.service.AvatarService;

@ApplicationScoped
public class AvatarController {
    private AvatarService avatarService;

    public AvatarController() {}

    @Inject
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    public Optional<Avatar> getAvatar(UUID id) throws IOException {
        return avatarService.find(id);
    }

    public void putAvatar(UUID id, InputStream inputStream) throws IOException {
        avatarService.update(id, inputStream);
    }

    public void deleteAvatar(UUID id) throws IOException {
        avatarService.delete(id);
    }
}
