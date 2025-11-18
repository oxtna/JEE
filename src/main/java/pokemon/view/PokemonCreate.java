package pokemon.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class PokemonCreate implements Serializable {
    @Inject
    private PokemonService pokemonService;
    @Inject
    private RegionService regionService;
    private String id;

    public void init() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
