package pokemon.entity;

import java.nio.file.Path;
import java.util.UUID;

public class Avatar {
    private UUID id;
    private byte[] data;

    public Avatar(UUID id) {
        this.id = id;
        this.data = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Avatar other)) return false;
        return other.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
