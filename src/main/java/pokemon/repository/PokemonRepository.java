package pokemon.repository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import pokemon.entity.Pokemon;

@ApplicationScoped
public class PokemonRepository implements Repository<Pokemon, UUID> {
    private final Map<UUID, Pokemon> pokemon = new HashMap<>();

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
        if (this.pokemon.containsKey(pokemon.getId())) {
            throw new IllegalArgumentException("Pokemon with this ID already exists");
        }
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
