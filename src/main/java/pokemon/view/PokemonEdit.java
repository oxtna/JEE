package pokemon.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import pokemon.entity.Pokemon;
import pokemon.entity.PokemonType;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class PokemonEdit implements Serializable {
    @Inject
    private PokemonService pokemonService;
    @Inject
    private RegionService regionService;
    private String id;
    private Pokemon pokemon;

    public void init() throws IOException {
        pokemon = pokemonService.find(UUID.fromString(id)).orElse(null);
        if (pokemon == null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Pokemon not found");
        }
    }

    public String saveAction() {
        pokemonService.update(pokemon);
        return "pokemon-edit.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public List<PokemonType> getAllPokemonTypes() {
        return List.of(PokemonType.values());
    }
}
