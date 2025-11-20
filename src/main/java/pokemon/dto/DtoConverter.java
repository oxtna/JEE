package pokemon.dto;

import pokemon.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DtoConverter {
    public static class GetPokemonFunction implements Function<Pokemon, GetPokemon> {
        @Override
        public GetPokemon apply(Pokemon pokemon) {
            return new GetPokemon(
                    pokemon.getId(),
                    pokemon.getName(),
                    pokemon.getTypes().stream().map(PokemonType::name).toList(),
                    new GetPokemon.Region(
                            pokemon.getRegion().getId(),
                            pokemon.getRegion().getName(),
                            pokemon.getRegion().getGeneration()
                    ),
                    pokemon.getTrainer() != null ? new GetPokemon.Trainer(
                            pokemon.getTrainer().getId(),
                            pokemon.getTrainer().getName()
                    ) : null,
                    pokemon.getLevel(),
                    pokemon.getHitPoints(),
                    pokemon.getAttack(),
                    pokemon.getDefence(),
                    pokemon.getSpecialAttack(),
                    pokemon.getSpecialDefence(),
                    pokemon.getSpeed()
            );
        }
    }
    public static class GetPokemonsFunction implements Function<Collection<Pokemon>, GetPokemons> {
        @Override
        public GetPokemons apply(Collection<Pokemon> pokemons) {
            return new GetPokemons(
                    pokemons.stream().map(p -> new GetPokemons.Pokemon(p.getId(), p.getName())).toList()
            );
        }
    }
    public static class PutPokemonFunction implements BiFunction<UUID, PutPokemon, Pokemon> {
        @Override
        public Pokemon apply(UUID id, PutPokemon putPokemon) {
            return new Pokemon(
                    id,
                    putPokemon.getName(),
                    putPokemon.getTypes().stream().map(PokemonType::valueOf).toList(),
                    null,
                    null,
                    putPokemon.getLevel(),
                    putPokemon.getHitPoints(),
                    putPokemon.getAttack(),
                    putPokemon.getDefence(),
                    putPokemon.getSpecialAttack(),
                    putPokemon.getSpecialDefence(),
                    putPokemon.getSpeed()
            );
        }
    }
    public static class PatchPokemonFunction implements BiFunction<Pokemon, PatchPokemon, Pokemon> {
        @Override
        public Pokemon apply(Pokemon pokemon, PatchPokemon patchPokemon) {
            Pokemon patchedPokemon = new Pokemon(pokemon);
            if (patchPokemon.getName() != null) {
                patchedPokemon.setName(patchPokemon.getName());
            }
            if (patchPokemon.getTypes() != null) {
                patchedPokemon.setTypes(
                        patchPokemon.getTypes().stream().map(PokemonType::valueOf).toList()
                );
            }
            if (patchPokemon.getLevel() != null) {
                patchedPokemon.setLevel(patchPokemon.getLevel());
            }
            if (patchPokemon.getHitPoints() != null) {
                patchedPokemon.setHitPoints(patchPokemon.getHitPoints());
            }
            if (patchPokemon.getAttack() != null) {
                patchedPokemon.setAttack(patchPokemon.getAttack());
            }
            if (patchPokemon.getDefence() != null) {
                patchedPokemon.setDefence(patchPokemon.getDefence());
            }
            if (patchPokemon.getSpecialAttack() != null) {
                patchedPokemon.setSpecialAttack(patchPokemon.getSpecialAttack());
            }
            if (patchPokemon.getSpecialDefence() != null) {
                patchedPokemon.setSpecialDefence(patchPokemon.getSpecialDefence());
            }
            if (patchPokemon.getSpeed() != null) {
                patchedPokemon.setSpeed(patchPokemon.getSpeed());
            }
            return patchedPokemon;
        }
    }
    public static class GetRegionFunction implements Function<Region, GetRegion> {
        @Override
        public GetRegion apply(Region region) {
            return new GetRegion(
                    region.getId(),
                    region.getName(),
                    region.getGeneration(),
                    region.getProfessor(),
                    region.getPokemon() != null ?
                            region.getPokemon()
                                    .stream()
                                    .map(p -> new GetRegion.Pokemon(p.getId(), p.getName()))
                                    .toList() : null
            );
        }
    }
    public static class GetRegionsFunction implements Function<Collection<Region>, GetRegions> {
        @Override
        public GetRegions apply(Collection<Region> regions) {
            return new GetRegions(
                    regions.stream().map(r -> new GetRegions.Region(r.getId(), r.getName())).toList()
            );
        }
    }
    public static class PutRegionFunction implements BiFunction<UUID, PutRegion, Region> {
        @Override
        public Region apply(UUID id, PutRegion putRegion) {
            return new Region(
                    id,
                    putRegion.getName(),
                    putRegion.getGeneration(),
                    putRegion.getProfessor(),
                    List.of()
            );
        }
    }
    public static class PatchRegionFunction implements BiFunction<Region, PatchRegion, Region> {
        @Override
        public Region apply(Region region, PatchRegion patchRegion) {
            Region patchedRegion = new Region(region);
            if (patchRegion.getName() != null) {
                patchedRegion.setName(patchRegion.getName());
            }
            if (patchRegion.getGeneration() != null) {
                patchedRegion.setGeneration(patchRegion.getGeneration());
            }
            if (patchRegion.getProfessor() != null) {
                patchedRegion.setProfessor(patchRegion.getProfessor());
            }
            return patchedRegion;
        }
    }
}
