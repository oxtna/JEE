package pokemon.dto;

public class PutRegion {
    private String name;
    private Integer generation;
    private String professor;

    public PutRegion() {}

    public PutRegion(String name, Integer generation, String professor) {
        this.name = name;
        this.generation = generation;
        this.professor = professor;
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
}
