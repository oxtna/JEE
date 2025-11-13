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

        trainerService.create(new Trainer(
                UUID.fromString("c337216a-0808-4984-bbd0-ced1fb68eb6a"), "Max",
                LocalDate.now(), 10, new ArrayList<>()
        ));
        trainerService.create(new Trainer(
                UUID.fromString("b9e880d8-959f-499d-85f9-f8a294f7471d"), "Tom",
                LocalDate.now(), 20, new ArrayList<>()
        ));
        trainerService.create(new Trainer(
                UUID.fromString("0332fd47-058e-4100-8a26-a2c839398188"), "Hugh",
                LocalDate.now(), 15, new ArrayList<>()
        ));
        Trainer brendan = new Trainer(
                UUID.fromString("8ace8e64-8b1f-4f39-9236-05bfa6f65355"), "Brendan",
                LocalDate.now(), 5, new ArrayList<>()
        );
        Region hoenn = new Region(UUID.randomUUID(), "Hoenn", 3, "Birch", new ArrayList<>());
        Pokemon mudkip = new Pokemon(
                UUID.randomUUID(), "Mudkip", List.of(PokemonType.WATER, PokemonType.GROUND), hoenn, brendan,
                5, 10, 10, 10, 10, 10, 10
        );
        trainerService.create(brendan);
        regionService.create(hoenn);
        pokemonService.create(mudkip);

        requestContextController.deactivate();
    }
}
