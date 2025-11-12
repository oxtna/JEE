package pokemon.model;

import java.nio.file.Path;
import java.util.UUID;

public class Avatar {
    private UUID id;
    private byte[] data;

    public Avatar(UUID id) {
        this.id = id;
        this.data = null;
    }

    public UUID getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Path getPath() {
        return Path.of(id.toString());
    }
}
