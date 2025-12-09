package pokemon.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "regions")
public class Region implements Serializable {
    @Id
    private UUID id;
    private String name;
    private Integer generation;
    private String professor;
    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Pokemon> pokemon;

    public Region() {}

    public Region(UUID id, String name, Integer generation, String professor, List<Pokemon> pokemon) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.professor = professor;
        this.pokemon = pokemon;
    }

    public Region(Region other) {
        this.id = other.id;
        this.name = other.name;
        this.generation = other.generation;
        this.professor = other.professor;
        this.pokemon = other.pokemon;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Region other)) return false;
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

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
