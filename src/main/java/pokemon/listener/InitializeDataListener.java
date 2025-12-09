package pokemon.listener;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ejb.DependsOn;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import pokemon.entity.*;
import pokemon.service.AvatarService;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;
import pokemon.service.TrainerService;

@Singleton
@Startup
@DeclareRoles("ADMIN")
public class InitializeDataListener {
    private TrainerService trainerService;
    private AvatarService avatarService;
    private PokemonService pokemonService;
    private RegionService regionService;
    private Pbkdf2PasswordHash passwordHash;
    private RequestContextController requestContextController;

    public InitializeDataListener() {}

    @Inject
    public InitializeDataListener(TrainerService trainerService, AvatarService avatarService, PokemonService pokemonService,
                                  RegionService regionService, Pbkdf2PasswordHash passwordHash,
                                  RequestContextController requestContextController) {
        this.trainerService = trainerService;
        this.avatarService = avatarService;
        this.pokemonService = pokemonService;
        this.regionService = regionService;
        this.passwordHash = passwordHash;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    public void init() {
        requestContextController.activate();

        Trainer t1 = new Trainer(UUID.fromString("12341234-1234-1234-1234-123412341234"), "t1", "t1",
                TrainerRole.NORMAL, "t1", LocalDate.now(), 0, List.of());
        Trainer t2 = new Trainer(UUID.fromString("12121212-1234-1234-1234-121212121212"), "t2", "t2",
                TrainerRole.NORMAL, "t2", LocalDate.now(), 0, List.of());

        Region hoenn = new Region(UUID.fromString("bdbb750b-4d78-4da2-945d-c90029afb9e0"), "Hoenn",
                3, "Birch", List.of());
        Region unova = new Region(UUID.fromString("bdbb750b-4d78-4da2-945d-000000000000"), "Unova",
                5, "Juniper", List.of());
        Pokemon p1 = new Pokemon(
                UUID.fromString("e6ec3642-a42d-4496-a7ec-80b7c7401c23"), "Mudkip",
                PokemonType.WATER, hoenn, t1,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p2 = new Pokemon(
                UUID.fromString("bbebb10e-0e06-4ec5-8a01-ee10b65b570e"), "Seedot",
                PokemonType.GRASS, hoenn, t2,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p3 = new Pokemon(
                UUID.fromString("ec229cf7-193e-4754-89e5-6820e0b8f640"), "Torchic",
                PokemonType.FIRE, hoenn, t1,
                5, 10, 10, 10, 10, 10, 10
                );
        Pokemon p4 = new Pokemon(
                UUID.fromString("f55f3b21-f99a-4f2f-8a25-b5d575c9854e"), "Snivy",
                PokemonType.GRASS, unova, t1,
                5, 10, 10, 10, 10, 10, 10
        );
        Pokemon p5 = new Pokemon(
                UUID.fromString("f55f3b21-f99a-4f2f-8a25-000000000000"), "Victini",
                PokemonType.PSYCHIC, unova, t2,
                5, 10, 10, 10, 10, 10, 10
        );

        t1.setTeam(List.of(p1, p2, p3));
        t2.setTeam(List.of(p4, p5));
        hoenn.setPokemon(List.of(p1, p2, p3));
        unova.setPokemon(List.of(p4, p5));

        Trainer admin = new Trainer(
                UUID.fromString("00000000-1111-2222-3333-444444444444"),
                "admin",
                "admin",
                TrainerRole.ADMIN,
                "admin",
                LocalDate.now(),
                Integer.MAX_VALUE,
                List.of()
        );
        trainerService.create(admin);
        trainerService.create(t1);
        trainerService.create(t2);
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
