package pokemon.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pokemon.api.GetTrainer;
import pokemon.api.GetTrainers;
import pokemon.api.PutTrainer;
import pokemon.model.Trainer;
import pokemon.service.TrainerService;

@ApplicationScoped
public class TrainerController {
    private TrainerService service;

    public TrainerController() {}

    @Inject
    public TrainerController(TrainerService service) {
        this.service = service;
    }

    public GetTrainer getTrainer(UUID id) {
        Optional<Trainer> trainer = service.find(id);
        if (trainer.isEmpty()) {
            return null;
        }
        GetTrainer response = new GetTrainer();
        response.setId(id);
        response.setName(trainer.get().getName());
        response.setMoney(trainer.get().getMoney());
        response.setRegistrationDate(trainer.get().getRegistrationDate());
        return response;
    }

    public GetTrainers getTrainers() {
        Collection<Trainer> trainers = service.findAll();
        GetTrainers response = new GetTrainers();
        response.setTrainers(trainers.stream().map(t -> {
            GetTrainer trainer = new GetTrainer();
            trainer.setId(t.getId());
            trainer.setName(t.getName());
            trainer.setMoney(t.getMoney());
            return trainer;
        }).toList());
        return response;
    }

    public void putTrainer(UUID uuid, PutTrainer trainer) {
        service.create(new Trainer(
                uuid, trainer.getName(), LocalDate.now(), 50, new ArrayList<>()
        ));
    }
}