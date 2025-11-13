package pokemon.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import pokemon.model.Region;
import pokemon.repository.RegionRepository;

@ApplicationScoped
public class RegionService {
    private RegionRepository repository;

    public RegionService() {}

    @Inject
    public RegionService(RegionRepository repository) {
        this.repository = repository;
    }

    public Collection<Region> findAll() {
        return repository.findAll();
    }

    public Optional<Region> find(UUID id) {
        return repository.find(id);
    }

    public void create(Region region) {
        repository.create(region);
    }

    public void update(Region region) {
        repository.update(region);
    }

    public void delete(Region region) {
        repository.delete(region);
    }
}
