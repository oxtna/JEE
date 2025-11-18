package pokemon.listener;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import pokemon.model.Pokemon;
import pokemon.model.PokemonType;
import pokemon.model.Region;
import pokemon.model.Trainer;
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

        Region hoenn = new Region(UUID.randomUUID(), "Hoenn", 3, "Birch", new ArrayList<>());
        Region unova = new Region(UUID.randomUUID(), "Unova", 5, "Juniper", new ArrayList<>());
        Pokemon p1 = new Pokemon(
                UUID.randomUUID(), "Mudkip", List.of(PokemonType.WATER, PokemonType.GROUND), hoenn, null,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p2 = new Pokemon(
                UUID.randomUUID(), "Seedot", List.of(PokemonType.GRASS), hoenn, null,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p3 = new Pokemon(
                UUID.randomUUID(), "Torchic", List.of(PokemonType.FIRE), hoenn, null,
                5, 10, 10, 10, 10, 10, 10
                );
        Pokemon p4 = new Pokemon(
                UUID.randomUUID(), "Snivy", List.of(PokemonType.GRASS), unova, null,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p5 = new Pokemon(
                UUID.randomUUID(), "Victini", List.of(PokemonType.PSYCHIC, PokemonType.FIRE), unova, null,
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
