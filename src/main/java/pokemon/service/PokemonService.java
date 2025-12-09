package pokemon.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import pokemon.entity.Pokemon;
import pokemon.entity.PokemonType;
import pokemon.entity.Region;
import pokemon.entity.Trainer;
import pokemon.interceptor.OperationLogged;
import pokemon.repository.PokemonRepository;
import pokemon.repository.RegionRepository;
import pokemon.repository.TrainerRepository;

@ApplicationScoped
@Transactional
public class PokemonService implements Serializable {
    private PokemonRepository pokemonRepository;
    private RegionRepository regionRepository;
    private TrainerRepository trainerRepository;

    public PokemonService() {}

    @Inject
    public PokemonService(PokemonRepository pokemonRepository, RegionRepository regionRepository,
                          TrainerRepository trainerRepository) {
        this.pokemonRepository = pokemonRepository;
        this.regionRepository = regionRepository;
        this.trainerRepository = trainerRepository;
    }

    public Collection<Pokemon> findAll() {
        return pokemonRepository.findAll();
    }

    public Optional<Pokemon> find(UUID id) {
        return pokemonRepository.find(id);
    }

    public Collection<Pokemon> findAllByLogin(String trainerLogin) {
        return pokemonRepository.findAllByLogin(trainerLogin);
    }

    public Collection<Pokemon> findByFilters(String name, PokemonType type, Region region, Integer level,
                                             Integer hitPoints, Integer attack, Integer defence,
                                             Integer specialAttack, Integer specialDefence, Integer speed) {
        return pokemonRepository.findByFilters(name, type, region, level, hitPoints, attack, defence,
                specialAttack, specialDefence, speed);
    }

    @OperationLogged
    public void create(Pokemon pokemon) {
        pokemonRepository.create(pokemon);
    }

    @OperationLogged
    public void update(Pokemon pokemon) {
        pokemonRepository.update(pokemon);
    }

    @OperationLogged
    public void delete(UUID id) {
        pokemonRepository.delete(pokemonRepository.find(id).orElseThrow());
    }

    public Collection<Pokemon> findAllByRegion(UUID regionId) {
        Region region = regionRepository.find(regionId).orElseThrow();
        return region.getPokemon();
    }

    public Optional<Pokemon> findInRegion(UUID regionID, UUID pokemonId) {
        Region region = regionRepository.find(regionID).orElseThrow();
        Pokemon pokemon = pokemonRepository.find(pokemonId).orElseThrow();
        if (region.getPokemon().contains(pokemon)) {
            return Optional.of(pokemon);
        }
        return Optional.empty();
    }

    public Collection<Pokemon> findAllInRegionByLogin(UUID regionId, String trainerLogin) {
        Region region = regionRepository.find(regionId).orElseThrow();
        return region.getPokemon()
                .stream()
                .filter(pokemon -> pokemon.getTrainer() != null
                        && pokemon.getTrainer().getLogin().equals(trainerLogin))
                .toList();
    }

    @OperationLogged
    public void createInRegion(UUID regionId, Pokemon pokemon) {
        Region region = regionRepository.find(regionId).orElseThrow();
        region.setPokemon(Stream.concat(region.getPokemon().stream(), Stream.of(pokemon)).toList());
        pokemon.setRegion(region);
        pokemonRepository.create(pokemon);
        regionRepository.update(region);
    }

    @OperationLogged
    public void updateInRegion(UUID regionId, Pokemon pokemon) {
        Region region = regionRepository.find(regionId).orElseThrow();
        if (!pokemon.getRegion().getId().equals(region.getId())) {
            throw new NoSuchElementException("Region does not contain this pokemon");
        }
        pokemonRepository.update(pokemon);
    }

    @OperationLogged
    public void deleteFromRegion(UUID regionId, UUID pokemonId) {
        Region region = regionRepository.find(regionId).orElseThrow();
        Pokemon pokemon = pokemonRepository.find(pokemonId).orElseThrow();
        if (!pokemon.getRegion().getId().equals(region.getId())) {
            throw new NoSuchElementException("Region does not contain this pokemon");
        }
        region.setPokemon(region.getPokemon().stream().filter(p -> p.getId() != pokemonId).toList());
        regionRepository.update(region);
        pokemonRepository.delete(pokemon);
    }
}
