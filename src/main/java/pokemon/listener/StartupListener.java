package pokemon.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import pokemon.controller.AvatarController;
import pokemon.controller.TrainerController;
import pokemon.model.Trainer;
import pokemon.repository.TrainerRepository;
import pokemon.service.AvatarService;
import pokemon.service.TrainerService;

public class StartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        TrainerRepository trainerRepository = new TrainerRepository();
        TrainerService trainerService = new TrainerService(trainerRepository);
        AvatarService avatarService = new AvatarService();
        TrainerController trainerController = new TrainerController(trainerService);
        AvatarController avatarController = new AvatarController(avatarService);
        sce.getServletContext().setAttribute("trainerController", trainerController);
        sce.getServletContext().setAttribute("avatarController", avatarController);

        trainerRepository.create(new Trainer(
                UUID.fromString("c337216a-0808-4984-bbd0-ced1fb68eb6a"), "Max",
                LocalDate.now(), 10, new ArrayList<>()
        ));
        trainerRepository.create(new Trainer(
                UUID.fromString("b9e880d8-959f-499d-85f9-f8a294f7471d"), "Tom",
                LocalDate.now(), 20, new ArrayList<>()
        ));
        trainerRepository.create(new Trainer(
                UUID.fromString("0332fd47-058e-4100-8a26-a2c839398188"), "Hugh",
                LocalDate.now(), 15, new ArrayList<>()
        ));
        trainerRepository.create(new Trainer(
                UUID.fromString("8ace8e64-8b1f-4f39-9236-05bfa6f65355"), "Brendan",
                LocalDate.now(), 5, new ArrayList<>()
        ));
    }
}
