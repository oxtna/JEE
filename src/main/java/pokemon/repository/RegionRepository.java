package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pokemon.entity.Region;

@Dependent
public class RegionRepository implements Repository<Region, UUID> {
    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    @Override
    public Optional<Region> find(UUID id) {
        return Optional.ofNullable(em.find(Region.class, id));
    }

    @Override
    public Collection<Region> findAll() {
        return em.createQuery("select r from Region r", Region.class).getResultList();
    }

    @Override
    public void create(Region region) {
        em.persist(region);
    }

    @Override
    public void update(Region region) {
        em.merge(region);
    }

    @Override
    public void delete(Region region) {
        em.remove(region);
    }
}
