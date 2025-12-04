package pokemon.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import pokemon.entity.Trainer;
import pokemon.repository.TrainerRepository;

@LocalBean
@Stateless
public class TrainerService implements Serializable {
    private TrainerRepository repository;
    private Pbkdf2PasswordHash passwordHash;

    public TrainerService() {}

    @Inject
    public TrainerService(TrainerRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    public Collection<Trainer> findAll() {
        return repository.findAll();
    }

    public Optional<Trainer> find(UUID id) {
        return repository.find(id);
    }

    public Optional<Trainer> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<Trainer> findByName(String name) {
        return repository.findByName(name);
    }

    public void create(Trainer trainer) {
        trainer.setPassword(passwordHash.generate(trainer.getPassword().toCharArray()));
        repository.create(trainer);
    }

    public void update(Trainer trainer) {
        repository.update(trainer);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }
}
