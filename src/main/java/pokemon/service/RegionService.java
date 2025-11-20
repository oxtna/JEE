package pokemon.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import pokemon.entity.Pokemon;
import pokemon.entity.Region;
import pokemon.repository.RegionRepository;

@ApplicationScoped
public class RegionService {
    private RegionRepository regionRepository;

    public RegionService() {}

    @Inject
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Collection<Region> findAll() {
        return regionRepository.findAll();
    }

    public Optional<Region> find(UUID id) {
        return regionRepository.find(id);
    }

    public void create(Region region) {
        regionRepository.create(region);
    }

    public void update(Region region) {
        regionRepository.update(region);
    }

    public void delete(Region region) {
        regionRepository.delete(region);
    }
}
