package pokemon.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pokemon.model.Trainer;
import pokemon.repository.TrainerRepository;

@ApplicationScoped
public class TrainerService {
    private TrainerRepository repository;

    public TrainerService() {}

    @Inject
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
