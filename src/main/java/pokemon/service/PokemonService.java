package pokemon.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import pokemon.model.Pokemon;
import pokemon.repository.PokemonRepository;

@ApplicationScoped
public class PokemonService {
    private PokemonRepository repository;

    public PokemonService() {}

    @Inject
    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }

    public Collection<Pokemon> findAll() {
        return repository.findAll();
    }

    public Optional<Pokemon> find(UUID id) {
        return repository.find(id);
    }

    public void create(Pokemon pokemon) {
        repository.create(pokemon);
    }

    public void update(Pokemon pokemon) {
        repository.update(pokemon);
    }

    public void delete(Pokemon pokemon) {
        repository.delete(pokemon);
    }
}
