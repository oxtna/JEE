package pokemon.controller.simple;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pokemon.dto.GetTrainer;
import pokemon.dto.GetTrainers;
import pokemon.dto.PutTrainer;
import pokemon.entity.Trainer;
import pokemon.entity.TrainerRole;
import pokemon.service.TrainerService;

@Stateless
public class TrainerController {
    private TrainerService trainerService;

    public TrainerController() {}

    @Inject
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    public GetTrainer getTrainer(UUID id) {
        Trainer trainer = trainerService.find(id).orElseThrow();
        return new GetTrainer(
                id,
                trainer.getName(),
                trainer.getRegistrationDate(),
                trainer.getMoney(),
                trainer.getTeam() != null ?
                        trainer.getTeam()
                                .stream()
                                .map(pokemon -> new GetTrainer.Pokemon(pokemon.getId(), pokemon.getName()))
                                .toList() : List.of()
        );
    }

    public GetTrainers getTrainers() {
        Collection<Trainer> trainers = trainerService.findAll();
        return new GetTrainers(
                trainers.stream()
                        .map(trainer -> new GetTrainers.Trainer(trainer.getId(), trainer.getName()))
                        .toList());
    }

    public void putTrainer(UUID uuid, PutTrainer putTrainer) {
        trainerService.create(new Trainer(
                uuid, putTrainer.getLogin(), putTrainer.getPassword(), TrainerRole.NORMAL, putTrainer.getName(),
                LocalDate.now(), 0, List.of()
        ));
    }
}