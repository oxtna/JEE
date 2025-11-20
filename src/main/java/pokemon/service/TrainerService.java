package pokemon.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pokemon.entity.Trainer;
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

    @Transactional
    public void create(Trainer trainer) {
        repository.create(trainer);
    }

    @Transactional
    public void update(Trainer trainer) {
        repository.update(trainer);
    }

    @Transactional
    public void delete(Trainer trainer) {
        repository.delete(trainer);
    }
}
