package pokemon.dto;

public class PatchRegion {
    private String name;
    private Integer generation;
    private String professor;

    public PatchRegion() {}

    public PatchRegion(String name, Integer generation, String professor) {
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
