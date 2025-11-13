package pokemon.model;

import java.util.List;
import java.util.UUID;

public class Region {
    private UUID id;
    private String name;
    private int generation;
    private String professor;
    private List<Pokemon> pokemon;

    public Region(UUID id, String name, int generation, String professor, List<Pokemon> pokemon) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.professor = professor;
        this.pokemon = pokemon;
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

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
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
