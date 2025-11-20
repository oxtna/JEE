package pokemon.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
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

    @Transactional
    public void create(Region region) {
        regionRepository.create(region);
    }

    @Transactional
    public void update(Region region) {
        regionRepository.update(region);
    }

    @Transactional
    public void delete(UUID id) {
        regionRepository.delete(regionRepository.find(id).orElseThrow());
    }
}
