package pokemon.repository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import pokemon.model.Pokemon;

@ApplicationScoped
public class PokemonRepository implements Repository<Pokemon, UUID> {
    private Map<UUID, Pokemon> pokemon = new HashMap<>();

    @Override
    public Optional<Pokemon> find(UUID id) {
        return Optional.ofNullable(pokemon.get(id));
    }

    @Override
    public Collection<Pokemon> findAll() {
        return pokemon.values();
    }

    @Override
    public void create(Pokemon pokemon) {
        this.pokemon.put(pokemon.getId(), pokemon);
    }

    @Override
    public void update(Pokemon pokemon) {
        this.pokemon.put(pokemon.getId(), pokemon);
    }

    @Override
    public void delete(Pokemon pokemon) {
        this.pokemon.remove(pokemon.getId());
    }
}
