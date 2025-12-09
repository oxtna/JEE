package pokemon.view;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import pokemon.entity.Pokemon;
import pokemon.entity.PokemonType;
import pokemon.entity.Region;
import pokemon.entity.Trainer;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;
import pokemon.service.TrainerService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Named
@ViewScoped
public class PokemonCreate implements Serializable {
    @Inject
    private PokemonService pokemonService;
    @Inject
    private RegionService regionService;
    @Inject
    private TrainerService trainerService;
    @Inject
    private HttpServletRequest request;
    @Inject
    private Validator validator;
    private String regionId;
    private Pokemon pokemon;

    public void init() throws IOException {
        pokemon = new Pokemon();
        pokemon.setId(UUID.randomUUID());

        if (regionId != null && !regionId.isEmpty()) {
            Region region = regionService.find(UUID.fromString(regionId)).orElse(null);
            if (region == null) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Region not found");
                return;
            }
            pokemon.setRegion(region);
        }
    }

    public String createAction() {
        Set<ConstraintViolation<Pokemon>> violations = validator.validate(pokemon);
        if (!violations.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            for (ConstraintViolation<Pokemon> violation : violations) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        violation.getMessage(), null));
            }
            return null;
        }
        
        String currentUsername = request.getUserPrincipal().getName();
        Trainer trainer = trainerService.findByLogin(currentUsername).orElse(null);
        if (trainer != null) {
            pokemon.setTrainer(trainer);
        }
        pokemonService.create(pokemon);
        return "region-detail.xhtml?faces-redirect=true&id=" + pokemon.getRegion().getId();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public List<Region> getAllRegions() {
        return regionService.findAll().stream().toList();
    }

    public List<PokemonType> getAllPokemonTypes() {
        return List.of(PokemonType.values());
    }
}
