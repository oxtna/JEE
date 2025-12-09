package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pokemon.entity.Pokemon;
import pokemon.entity.PokemonType;
import pokemon.entity.Region;

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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokemon> query = cb.createQuery(Pokemon.class);
        Root<Pokemon> pokemon = query.from(Pokemon.class);
        query.select(pokemon);
        return em.createQuery(query).getResultList();
    }

    public Collection<Pokemon> findAllByLogin(String trainerLogin) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokemon> query = cb.createQuery(Pokemon.class);
        Root<Pokemon> pokemon = query.from(Pokemon.class);

        query.select(pokemon)
             .where(cb.equal(pokemon.get("trainer").get("login"), trainerLogin));

        return em.createQuery(query).getResultList();
    }

    public Collection<Pokemon> findByFilters(String name, PokemonType type, Region region, Integer level,
                                             Integer hitPoints, Integer attack, Integer defence,
                                             Integer specialAttack, Integer specialDefence, Integer speed) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokemon> query = cb.createQuery(Pokemon.class);
        Root<Pokemon> pokemon = query.from(Pokemon.class);

        Predicate predicate = cb.conjunction();

        if (name != null && !name.trim().isEmpty()) {
            predicate = cb.and(predicate, cb.like(cb.lower(pokemon.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (type != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("type"), type));
        }

        if (region != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("region"), region));
        }

        if (level != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("level"), level));
        }

        if (hitPoints != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("hitPoints"), hitPoints));
        }

        if (attack != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("attack"), attack));
        }

        if (defence != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("defence"), defence));
        }

        if (specialAttack != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("specialAttack"), specialAttack));
        }

        if (specialDefence != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("specialDefence"), specialDefence));
        }

        if (speed != null) {
            predicate = cb.and(predicate, cb.equal(pokemon.get("speed"), speed));
        }

        query.select(pokemon).where(predicate);
        return em.createQuery(query).getResultList();
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
