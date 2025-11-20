package pokemon.dto;

import java.util.List;
import java.util.UUID;

public class GetRegion {
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
    private Integer generation;
    private String professor;
    private List<Pokemon> pokemon;

    public GetRegion() {}

    public GetRegion(UUID id, String name, Integer generation, String professor, List<Pokemon> pokemon) {
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
