package pokemon.dto;

public class PatchTrainer {
    private String name;
    private Integer money;

    public PatchTrainer() {}

    public PatchTrainer(String name, Integer money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}