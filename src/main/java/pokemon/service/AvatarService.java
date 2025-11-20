package pokemon.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import pokemon.entity.Avatar;

@ApplicationScoped
public class AvatarService {
    public Optional<Avatar> find(UUID id) throws IOException {
        Avatar avatar = new Avatar(id);
        Path path = avatar.getPath();
        if (Files.exists(path)) {
            avatar.setData(Files.readAllBytes(path));
            return Optional.of(avatar);
        }
        return Optional.empty();
    }

    public void update(UUID id, InputStream inputStream) throws IOException {
        Avatar avatar = new Avatar(id);
        Path path = avatar.getPath();
        Files.write(path, inputStream.readAllBytes());
    }

    public void delete(UUID id) throws IOException {
        Avatar avatar = new Avatar(id);
        Files.deleteIfExists(avatar.getPath());
    }
}
