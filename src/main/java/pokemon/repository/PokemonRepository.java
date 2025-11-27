package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pokemon.entity.Pokemon;

@Dependent
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

    public Collection<Pokemon> findAllByLogin(String trainerLogin) {
        return em.createQuery("select p from Pokemon p where p.trainer.login = :trainerLogin", Pokemon.class)
                .setParameter("trainerLogin", trainerLogin)
                .getResultList();
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
