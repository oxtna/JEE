package pokemon.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Trainer {
    private UUID id;
    private String name;
    private LocalDate registrationDate;
    private int money;
    private List<Pokemon> team;

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
