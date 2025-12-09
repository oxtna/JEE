package pokemon.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import pokemon.entity.Region;
import pokemon.interceptor.OperationLogged;
import pokemon.repository.RegionRepository;

@ApplicationScoped
@Transactional
public class RegionService implements Serializable {
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

    public Optional<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    @OperationLogged
    public void create(Region region) {
        regionRepository.create(region);
    }

    @OperationLogged
    public void update(Region region) {
        regionRepository.update(region);
    }

    @OperationLogged
    public void delete(UUID id) {
        regionRepository.delete(regionRepository.find(id).orElseThrow());
    }
}
