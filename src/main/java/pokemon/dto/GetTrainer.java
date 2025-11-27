package pokemon.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GetTrainer {
    public static class Pokemon {
        private UUID id;
        private String name;

        public Pokemon() {}

        public Pokemon(UUID id, String name) {
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
    private LocalDate registrationDate;
    private Integer money;
    private List<Pokemon> team;

    public GetTrainer() {}

    public GetTrainer(UUID id, String name, LocalDate registrationDate, Integer money, List<Pokemon> team) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.money = money;
        this.team = team;
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public List<Pokemon> getTeam() {
        return team;
    }

    public void setTeam(List<Pokemon> team) {
        this.team = team;
    }
}
