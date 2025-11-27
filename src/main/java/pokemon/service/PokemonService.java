package pokemon.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import pokemon.entity.Pokemon;
import pokemon.entity.Region;
import pokemon.entity.Trainer;
import pokemon.repository.PokemonRepository;
import pokemon.repository.RegionRepository;
import pokemon.repository.TrainerRepository;

@LocalBean
@Stateless
public class PokemonService {
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

    public void create(Pokemon pokemon) {
        pokemonRepository.create(pokemon);
    }

    public void update(Pokemon pokemon) {
        pokemonRepository.update(pokemon);
    }

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
