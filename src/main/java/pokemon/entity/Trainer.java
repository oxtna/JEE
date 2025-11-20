package pokemon.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trainers")
public class Trainer implements Serializable {
    @Id
    private UUID id;
    private String name;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    private int money;
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<Pokemon> team;

    public Trainer() {}

    public Trainer(UUID id, String name, LocalDate registrationDate, int money, List<Pokemon> team) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.money = money;
        this.team = team;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public int getMoney() {
        return money;
    }

    public List<Pokemon> getTeam() {
        return this.team;
    }
}
