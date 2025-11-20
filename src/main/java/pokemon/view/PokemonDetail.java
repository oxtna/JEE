package pokemon.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import jakarta.servlet.http.HttpServletResponse;
import pokemon.entity.Pokemon;
import pokemon.service.PokemonService;

@Named
@ViewScoped
public class PokemonDetail implements Serializable {
    @Inject
    private PokemonService pokemonService;
    private Pokemon pokemon;
    private String id;

    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        pokemon = pokemonService.find(uuid).orElse(null);
        if (pokemon == null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Pokemon not found");
        }
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPokemonTypes() {
        if (pokemon == null) {
            return null;
        }
        return pokemon.getTypes().get(0).toString()
                + (pokemon.getTypes().size() > 1 ? ", " + pokemon.getTypes().get(1).toString() : "");
    }
}
