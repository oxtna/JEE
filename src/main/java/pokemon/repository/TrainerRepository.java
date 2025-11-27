package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        return em.createQuery("select t from Trainer t where t.login = :login", Trainer.class)
                .setParameter("login", login)
                .getResultStream()
                .findFirst();
    }

    public Optional<Trainer> findByName(String name) {
        return em.createQuery("select t from Trainer t where t.name = :name", Trainer.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Collection<Trainer> findAll() {
        return em.createQuery("select t from Trainer t", Trainer.class).getResultList();
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
