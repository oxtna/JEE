package pokemon.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Stream;

import jakarta.ws.rs.NotFoundException;
import pokemon.entity.Pokemon;
import pokemon.entity.Region;
import pokemon.repository.PokemonRepository;
import pokemon.repository.RegionRepository;

@ApplicationScoped
public class PokemonService {
    private PokemonRepository pokemonRepository;
    private RegionRepository regionRepository;

    public PokemonService() {}

    @Inject
    public PokemonService(PokemonRepository pokemonRepository, RegionRepository regionRepository) {
        this.pokemonRepository = pokemonRepository;
        this.regionRepository = regionRepository;
    }

    public Collection<Pokemon> findAll() {
        return pokemonRepository.findAll();
    }

    public Optional<Pokemon> find(UUID id) {
        return pokemonRepository.find(id);
    }

    public void create(Pokemon pokemon) {
        pokemonRepository.create(pokemon);
    }

    public void update(Pokemon pokemon) {
        pokemonRepository.update(pokemon);
    }

    public void delete(UUID id) {
        pokemonRepository.delete(pokemonRepository.find(id).orElseThrow());
    }

    public Optional<Pokemon> findInRegion(UUID regionId, UUID pokemonId) {
        try {
            Region region = regionRepository.find(regionId).orElseThrow();
            Pokemon pokemon = pokemonRepository.find(pokemonId).orElseThrow();
            if (!pokemon.getRegion().getId().equals(region.getId())) {
                return Optional.empty();
            }
            return Optional.of(pokemon);
        } catch (NoSuchElementException exc) {
            return Optional.empty();
        }
    }

    public Collection<Pokemon> findAllInRegion(UUID regionId) {
        Region region = regionRepository.find(regionId).orElseThrow();
        return region.getPokemon();
    }

    public void createInRegion(UUID regionId, Pokemon pokemon) {
        Region region = regionRepository.find(regionId).orElseThrow();
        region.setPokemon(Stream.concat(region.getPokemon().stream(), Stream.of(pokemon)).toList());
        pokemon.setRegion(region);
        pokemonRepository.create(pokemon);
        regionRepository.update(region);
    }

    public void updateInRegion(UUID regionId, Pokemon pokemon) {
        Region region = regionRepository.find(regionId).orElseThrow();
        if (!pokemon.getRegion().getId().equals(region.getId())) {
            throw new NoSuchElementException("Region does not contain this pokemon");
        }
        pokemonRepository.update(pokemon);
    }

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
