package pokemon.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import pokemon.model.Trainer;

public class TrainerRepository implements Repository<Trainer, UUID> {
    private final Map<UUID, Trainer> trainers = new HashMap<>();

    @Override
    public Optional<Trainer> find(UUID id) {
        return Optional.ofNullable(trainers.get(id));
    }

    @Override
    public Collection<Trainer> findAll() {
        return trainers.values();
    }

    @Override
    public void create(Trainer trainer) {
        trainers.put(trainer.getId(), trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainers.put(trainer.getId(), trainer);
    }

    @Override
    public void delete(Trainer trainer) {
        trainers.remove(trainer.getId());
    }
}
