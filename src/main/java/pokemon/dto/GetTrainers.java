package pokemon.dto;

import java.util.List;
import java.util.UUID;

public class GetTrainers {
    public static class Trainer {
        private UUID id;
        private String name;

        public Trainer() {}

        public Trainer(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private List<Trainer> trainers;

    public GetTrainers() {}

    public GetTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
}
