package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pokemon.entity.Trainer;

@Dependent
public class TrainerRepository implements Repository<Trainer, UUID> {
    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    @Override
    public Optional<Trainer> find(UUID id) {
        return Optional.ofNullable(em.find(Trainer.class, id));
    }

    public Optional<Trainer> findByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> query = cb.createQuery(Trainer.class);
        Root<Trainer> trainer = query.from(Trainer.class);

        query.select(trainer)
             .where(cb.equal(trainer.get("login"), login));

        return em.createQuery(query).getResultStream().findFirst();
    }

    public Optional<Trainer> findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> query = cb.createQuery(Trainer.class);
        Root<Trainer> trainer = query.from(Trainer.class);

        query.select(trainer)
             .where(cb.equal(trainer.get("name"), name));

        return em.createQuery(query).getResultStream().findFirst();
    }

    @Override
    public Collection<Trainer> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> query = cb.createQuery(Trainer.class);
        Root<Trainer> trainer = query.from(Trainer.class);
        query.select(trainer);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Trainer trainer) {
        em.persist(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        em.merge(trainer);
    }

    @Override
    public void delete(Trainer trainer) {
        em.remove(trainer);
    }
}
