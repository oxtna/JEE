package pokemon.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<Entity, Key> {
    Optional<Entity> find(Key id);
    Collection<Entity> findAll();
    void create(Entity entity);
    void update(Entity entity);
    void delete(Entity entity);
}
