package pokemon.listener;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import pokemon.entity.Pokemon;
import pokemon.entity.PokemonType;
import pokemon.entity.Region;
import pokemon.service.AvatarService;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;
import pokemon.service.TrainerService;

@ApplicationScoped
public class StartupListener {
    private TrainerService trainerService;
    private AvatarService avatarService;
    private PokemonService pokemonService;
    private RegionService regionService;
    private RequestContextController requestContextController;

    @Inject
    public StartupListener(TrainerService trainerService, AvatarService avatarService, PokemonService pokemonService,
                           RegionService regionService, RequestContextController requestContextController) {
        this.trainerService = trainerService;
        this.avatarService = avatarService;
        this.pokemonService = pokemonService;
        this.regionService = regionService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    public void init() {
        requestContextController.activate();

        Region hoenn = new Region(UUID.fromString("bdbb750b-4d78-4da2-945d-c90029afb9e0"), "Hoenn",
                3, "Birch", new ArrayList<>());
        Region unova = new Region(UUID.fromString("bdbb750b-4d78-4da2-945d-000000000000"), "Unova",
                5, "Juniper", new ArrayList<>());
        Pokemon p1 = new Pokemon(
                UUID.fromString("e6ec3642-a42d-4496-a7ec-80b7c7401c23"), "Mudkip", List.of(PokemonType.WATER, PokemonType.GROUND), hoenn, null,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p2 = new Pokemon(
                UUID.fromString("bbebb10e-0e06-4ec5-8a01-ee10b65b570e"), "Seedot", List.of(PokemonType.GRASS), hoenn, null,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p3 = new Pokemon(
                UUID.fromString("ec229cf7-193e-4754-89e5-6820e0b8f640"), "Torchic", List.of(PokemonType.FIRE), hoenn, null,
                5, 10, 10, 10, 10, 10, 10
                );
        Pokemon p4 = new Pokemon(
                UUID.fromString("f55f3b21-f99a-4f2f-8a25-b5d575c9854e"), "Snivy", List.of(PokemonType.GRASS), unova, null,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p5 = new Pokemon(
                UUID.fromString("f55f3b21-f99a-4f2f-8a25-000000000000"), "Victini", List.of(PokemonType.PSYCHIC, PokemonType.FIRE), unova, null,
                5, 10, 10, 10, 10, 10, 10
        );
        hoenn.setPokemon(List.of(p1, p2, p3));
        unova.setPokemon(List.of(p4, p5));
        regionService.create(hoenn);
        regionService.create(unova);
        pokemonService.create(p1);
        pokemonService.create(p2);
        pokemonService.create(p3);
        pokemonService.create(p4);
        pokemonService.create(p5);

        requestContextController.deactivate();
    }
}
