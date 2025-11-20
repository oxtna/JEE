package pokemon.repository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import pokemon.entity.Region;

@ApplicationScoped
public class RegionRepository implements Repository<Region, UUID> {
    private final Map<UUID, Region> regions = new HashMap<>();

    @Override
    public Optional<Region> find(UUID id) {
        return Optional.ofNullable(regions.get(id));
    }

    @Override
    public Collection<Region> findAll() {
        return regions.values();
    }

    @Override
    public void create(Region region) {
        if (regions.containsKey(region.getId())) {
            throw new IllegalArgumentException("Region with this ID already exists");
        }
        regions.put(region.getId(), region);
    }

    @Override
    public void update(Region region) {
        regions.put(region.getId(), region);
    }

    @Override
    public void delete(Region region) {
        regions.remove(region.getId());
    }
}
