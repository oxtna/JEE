package pokemon.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import pokemon.model.Trainer;
import pokemon.repository.TrainerRepository;

public class TrainerService {
    private final TrainerRepository repository;

    public TrainerService(TrainerRepository repository) {
        this.repository = repository;
    }

    public Collection<Trainer> findAll() {
        return repository.findAll();
    }

    public Optional<Trainer> find(UUID id) {
        return repository.find(id);
    }

    public void create(Trainer trainer) {
        repository.create(trainer);
    }

    public void update(Trainer trainer) {
        repository.update(trainer);
    }

    public void delete(Trainer trainer) {
        repository.delete(trainer);
    }
}
