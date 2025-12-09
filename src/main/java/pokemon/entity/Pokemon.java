package pokemon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import pokemon.validation.ValidPokemonLevel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pokemon")
public class Pokemon implements Serializable {
    @Id
    private UUID id;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @NotBlank(message = "{pokemon.name.notBlank}")
    @Size(min = 1, max = 50, message = "{pokemon.name.size}")
    private String name;

    @NotNull(message = "{pokemon.type.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PokemonType type;

    @ManyToOne
    private Region region;

    @ManyToOne
    private Trainer trainer;

    @NotNull(message = "{pokemon.level.notNull}")
    @ValidPokemonLevel
    private Integer level;

    @NotNull(message = "{pokemon.hitPoints.notNull}")
    @Min(value = 1, message = "{pokemon.hitPoints.min}")
    private Integer hitPoints;

    @NotNull(message = "{pokemon.attack.notNull}")
    @Min(value = 1, message = "{pokemon.attack.min}")
    private Integer attack;

    @NotNull(message = "{pokemon.defence.notNull}")
    @Min(value = 1, message = "{pokemon.defence.min}")
    private Integer defence;

    @NotNull(message = "{pokemon.specialAttack.notNull}")
    @Min(value = 1, message = "{pokemon.specialAttack.min}")
    private Integer specialAttack;

    @NotNull(message = "{pokemon.specialDefence.notNull}")
    @Min(value = 1, message = "{pokemon.specialDefence.min}")
    private Integer specialDefence;

    @NotNull(message = "{pokemon.speed.notNull}")
    @Min(value = 1, message = "{pokemon.speed.min}")
    private Integer speed;

    public Pokemon() {}

    public Pokemon(UUID id, String name, PokemonType type, Region region, Trainer trainer, Integer level,
                   Integer hitPoints, Integer attack, Integer defence, Integer specialAttack, Integer specialDefence,
                   Integer speed) {
        this.id = id;
        this.name = name;
        this.type = type;
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
        this.version = other.version;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
        this.name = other.name;
        this.type = other.type;
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Pokemon other)) return false;
        return other.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
