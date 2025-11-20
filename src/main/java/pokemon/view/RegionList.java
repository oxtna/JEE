package pokemon.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import pokemon.entity.Pokemon;
import pokemon.entity.Region;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;

@Named
@ViewScoped
public class RegionList implements Serializable {
    @Inject
    private RegionService regionService;
    @Inject
    private PokemonService pokemonService;

    public List<Region> getRegions() {
        return regionService.findAll().stream().toList();
    }

    public String remove(Region region) {
        regionService.delete(region);
        for  (Pokemon pokemon : region.getPokemon()) {
            pokemonService.delete(pokemon.getId());
        }
        return null;
    }
}
