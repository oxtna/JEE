package pokemon.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pokemon.entity.Region;

@Dependent
public class RegionRepository implements Repository<Region, UUID> {
    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager em;

    @Override
    public Optional<Region> find(UUID id) {
        return Optional.ofNullable(em.find(Region.class, id));
    }

    public Optional<Region> findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Region> query = cb.createQuery(Region.class);
        Root<Region> region = query.from(Region.class);

        query.select(region)
             .where(cb.equal(region.get("name"), name));

        try {
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Region> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Region> query = cb.createQuery(Region.class);
        Root<Region> region = query.from(Region.class);
        query.select(region);
        return em.createQuery(query).getResultList();
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
