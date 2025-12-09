package pokemon.entity;

import jakarta.json.bind.annotation.JsonbTransient;
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
    @Column(nullable = false, unique = true)
    private String login;
    @JsonbTransient
    @Column(nullable = false)
    private String password;
    @JsonbTransient
    @Enumerated(EnumType.STRING)
    private TrainerRole role;
    private String name;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    private Integer money;
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<Pokemon> team;

    public Trainer() {}

    public Trainer(UUID id, String login, String password, TrainerRole role, String name, LocalDate registrationDate,
                   Integer money, List<Pokemon> team) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
        this.registrationDate = registrationDate;
        this.money = money;
        this.team = team;
    }

    public Trainer(Trainer other) {
        this.id = other.id;
        this.login = other.login;
        this.password = other.password;
        this.name = other.name;
        this.registrationDate = other.registrationDate;
        this.money = other.money;
        this.team = other.team;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Trainer other)) return false;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TrainerRole getRole() {
        return role;
    }

    public void setRole(TrainerRole role) {
        this.role = role;
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
        return this.team;
    }

    public void setTeam(List<Pokemon> team) {
        this.team = team;
    }
}
