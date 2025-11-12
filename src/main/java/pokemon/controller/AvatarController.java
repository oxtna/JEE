package pokemon.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import pokemon.model.Avatar;
import pokemon.service.AvatarService;

public class AvatarController {
    private final AvatarService service;

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
