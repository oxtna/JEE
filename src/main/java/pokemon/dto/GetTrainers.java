package pokemon.dto;

import java.util.List;

public class GetTrainers {
    private List<GetTrainer> trainers;

    public List<GetTrainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<GetTrainer> trainers) {
        this.trainers = trainers;
    }
}
