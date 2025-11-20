package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pokemon.entity.Trainer;

@Stateless
public class TrainerRepository implements Repository<Trainer, UUID> {
    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    @Override
    public Optional<Trainer> find(UUID id) {
        return Optional.ofNullable(em.find(Trainer.class, id));
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
