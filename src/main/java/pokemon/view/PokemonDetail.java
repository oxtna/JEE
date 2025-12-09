package pokemon.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
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
    @Inject
    private HttpServletRequest request;
    private Pokemon pokemon;
    private String id;

    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        pokemon = pokemonService.find(uuid).orElse(null);
        if (pokemon == null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Pokemon not found");
            return;
        }

        if (!canAccessPokemon()) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        }
    }

    private boolean canAccessPokemon() {
        if (request.isUserInRole("ADMIN")) {
            return true;
        }
        if (pokemon.getTrainer() == null) {
            return false;
        }
        String currentUsername = request.getUserPrincipal().getName();
        return currentUsername.equals(pokemon.getTrainer().getLogin());
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

    public String getPokemonType() {
        if (pokemon == null || pokemon.getType() == null) {
            return null;
        }
        return pokemon.getType().toString();
    }
}
