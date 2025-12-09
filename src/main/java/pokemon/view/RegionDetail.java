package pokemon.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletResponse;
import pokemon.entity.Pokemon;
import pokemon.entity.Region;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;

@Named
@ViewScoped
public class RegionDetail implements Serializable {
    @Inject
    private RegionService regionService;
    @Inject
    private PokemonService pokemonService;
    @Inject
    private HttpServletRequest request;
    private Region region;
    private String id;

    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        region = regionService.find(uuid).orElse(null);
        if (region == null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Region not found");
        }
    }

    public Region getRegion() {
        return region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Pokemon> getFilteredPokemon() {
        if (region == null || region.getPokemon() == null) {
            return List.of();
        }

        if (isAdmin()) {
            return region.getPokemon();
        }

        String currentUsername = request.getUserPrincipal().getName();
        return region.getPokemon().stream()
                .filter(p -> p.getTrainer() != null &&
                           currentUsername.equals(p.getTrainer().getLogin()))
                .toList();
    }

    public boolean isAdmin() {
        return request.isUserInRole("ADMIN");
    }

    public boolean canManagePokemon(Pokemon pokemon) {
        if (isAdmin()) {
            return true;
        }
        if (pokemon.getTrainer() == null) {
            return false;
        }
        String currentUsername = request.getUserPrincipal().getName();
        return currentUsername.equals(pokemon.getTrainer().getLogin());
    }

    public void removePokemon(String pokemonId) {
        UUID uuid = UUID.fromString(pokemonId);
        Pokemon pokemon = pokemonService.find(uuid).orElse(null);
        if (pokemon == null) {
            return;
        }
        pokemonService.delete(uuid);

        region = regionService.find(UUID.fromString(id)).orElse(region);
    }
}
