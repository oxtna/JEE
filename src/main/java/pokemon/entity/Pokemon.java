package pokemon.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pokemon")
public class Pokemon implements Serializable {
    @Id
    private UUID id;
    private String name;
    private List<PokemonType> types;
    @ManyToOne
    private Region region;
    @ManyToOne
    private Trainer trainer;
    private Integer level;
    private Integer hitPoints;
    private Integer attack;
    private Integer defence;
    private Integer specialAttack;
    private Integer specialDefence;
    private Integer speed;

    public Pokemon() {}

    public Pokemon(UUID id, String name, List<PokemonType> types, Region region, Trainer trainer, Integer level,
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

    public Pokemon(Pokemon other) {
        this.id = other.id;
        this.name = other.name;
        this.types = other.types;
        this.region = other.region;
        this.trainer = other.trainer;
        this.level = other.level;
        this.hitPoints = other.hitPoints;
        this.attack = other.attack;
        this.defence = other.defence;
        this.specialAttack = other.specialAttack;
        this.specialDefence = other.specialDefence;
        this.speed = other.speed;
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

    public List<PokemonType> getTypes() {
        return types;
    }

    public void setTypes(List<PokemonType> types) {
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
