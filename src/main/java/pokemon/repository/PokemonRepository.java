package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pokemon.entity.Pokemon;

@Stateless
public class PokemonRepository implements Repository<Pokemon, UUID> {
    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    @Override
    public Optional<Pokemon> find(UUID id) {
        return Optional.ofNullable(em.find(Pokemon.class, id));
    }

    @Override
    public Collection<Pokemon> findAll() {
        return em.createQuery("select p from Pokemon p", Pokemon.class).getResultList();
    }

    @Override
    public void create(Pokemon pokemon) {
        em.persist(pokemon);
    }

    @Override
    public void update(Pokemon pokemon) {
        em.merge(pokemon);
    }

    @Override
    public void delete(Pokemon pokemon) {
        em.remove(pokemon);
    }
}
