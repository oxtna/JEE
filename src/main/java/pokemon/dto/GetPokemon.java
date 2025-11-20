package pokemon.dto;

import java.util.List;
import java.util.UUID;

public class GetPokemon {
    public static class Region {
        private UUID id;
        private String name;
        private Integer generation;

        public Region() {}

        public Region(UUID id, String name, Integer generation) {
            this.id = id;
            this.name = name;
            this.generation = generation;
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

        public Integer getGeneration() {
            return generation;
        }

        public void setGeneration(Integer generation) {
            this.generation = generation;
        }
    }

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

    private UUID id;
    private String name;
    private List<String> types;
    private Region region;
    private Trainer trainer;
    private Integer level;
    private Integer hitPoints;
    private Integer attack;
    private Integer defence;
    private Integer specialAttack;
    private Integer specialDefence;
    private Integer speed;

    public GetPokemon() {}

    public GetPokemon(UUID id, String name, List<String> types, Region region, Trainer trainer, Integer level,
                      Integer hitPoints, Integer attack, Integer defence, Integer specialAttack, Integer specialDefence,
                      Integer speed) {
        this.id = id;
        this.name = name;
        this.types = types;
        this.region = region;
        this.trainer = trainer;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defence = defence;
        this.specialAttack = specialAttack;
        this.specialDefence = specialDefence;
        this.speed = speed;
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

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(Integer hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefence() {
        return defence;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public Integer getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Integer specialAttack) {
        this.specialAttack = specialAttack;
    }

    public Integer getSpecialDefence() {
        return specialDefence;
    }

    public void setSpecialDefence(Integer specialDefence) {
        this.specialDefence = specialDefence;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
