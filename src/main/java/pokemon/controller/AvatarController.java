package pokemon.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pokemon.model.Avatar;
import pokemon.service.AvatarService;

@ApplicationScoped
public class AvatarController {
    private AvatarService service;

    public AvatarController() {}

    @Inject
    public AvatarController(AvatarService service) {
        this.service = service;
    }

    public Optional<Avatar> getAvatar(UUID id) throws IOException {
        return service.find(id);
    }

    public void putAvatar(UUID id, InputStream inputStream) throws IOException {
        service.update(id, inputStream);
    }

    public void deleteAvatar(UUID id) throws IOException {
        service.delete(id);
    }
}
