package pokemon.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import jakarta.servlet.http.HttpServletResponse;
import pokemon.model.Pokemon;
import pokemon.model.Region;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;

@Named
@ViewScoped
public class RegionDetail implements Serializable {
    @Inject
    private RegionService regionService;
    @Inject
    private PokemonService pokemonService;
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

    public String removePokemon(String pokemonId) throws IOException {
        UUID uuid = UUID.fromString(pokemonId);
        Pokemon pokemon = pokemonService.find(uuid).orElse(null);
        if (pokemon == null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Pokemon not found");
            return null;
        }
        region.setPokemon(region.getPokemon().stream().filter(p -> p != pokemon).toList());
        regionService.update(region);
        pokemonService.delete(pokemon);
        return "region-detail.xhtml?faces-redirect=true&includeViewParams=true";
    }
}
