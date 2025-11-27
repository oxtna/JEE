package pokemon.listener;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import pokemon.entity.Trainer;
import pokemon.entity.TrainerRole;
import pokemon.repository.TrainerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
public class InitializeAdminListener {
    private TrainerRepository trainerRepository;
    private Pbkdf2PasswordHash passwordHash;

    public InitializeAdminListener() {}

    @Inject
    public InitializeAdminListener(TrainerRepository trainerRepository, Pbkdf2PasswordHash passwordHash) {
        this.trainerRepository = trainerRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    public void init() {
        if (trainerRepository.findByLogin("admin-service").isEmpty()) {
            Trainer trainer = new Trainer(
                    UUID.fromString("00000000-1111-2222-3333-444444444444"),
                    "admin-service",
                    passwordHash.generate("admin".toCharArray()),
                    TrainerRole.ADMIN,
                    "admin",
                    LocalDate.now(),
                    Integer.MAX_VALUE,
                    List.of()
            );
            trainerRepository.create(trainer);
        }
    }
}
