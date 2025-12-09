package pokemon.view;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import pokemon.entity.Pokemon;
import pokemon.entity.PokemonType;
import pokemon.entity.Region;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

@Named
@ViewScoped
public class PokemonEdit implements Serializable {
    @Inject
    private PokemonService pokemonService;
    @Inject
    private RegionService regionService;
    @Inject
    private HttpServletRequest request;
    @Inject
    private Validator validator;
    private String id;
    private Pokemon pokemon;
    private Pokemon originalPokemon;
    private Pokemon userInputPokemon;

    public void init() throws IOException {
        pokemon = pokemonService.find(UUID.fromString(id)).orElse(null);
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

        originalPokemon = new Pokemon(pokemon);
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

    public String saveAction() {
        Set<ConstraintViolation<Pokemon>> violations = validator.validate(pokemon);
        if (!violations.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            for (ConstraintViolation<Pokemon> violation : violations) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        violation.getMessage(), null));
            }
            return null;
        }
        
        try {
            pokemonService.update(pokemon);
            return "pokemon-detail.xhtml?faces-redirect=true&id=" + pokemon.getId();
        } catch (OptimisticLockException e) {
            userInputPokemon = new Pokemon(pokemon);
            pokemon = pokemonService.find(pokemon.getId()).orElse(null);
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                bundle.getString("pokemon.optimisticLockError"), null));
            return null;
        }
    }

    public String restoreUserInputAction() {
        if (userInputPokemon != null) {
            pokemon.setName(userInputPokemon.getName());
            pokemon.setType(userInputPokemon.getType());
            pokemon.setRegion(userInputPokemon.getRegion());
            pokemon.setLevel(userInputPokemon.getLevel());
            pokemon.setHitPoints(userInputPokemon.getHitPoints());
            pokemon.setAttack(userInputPokemon.getAttack());
            pokemon.setDefence(userInputPokemon.getDefence());
            pokemon.setSpecialAttack(userInputPokemon.getSpecialAttack());
            pokemon.setSpecialDefence(userInputPokemon.getSpecialDefence());
            pokemon.setSpeed(userInputPokemon.getSpeed());
            userInputPokemon = null;
        }
        return null;
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

    public List<Region> getAllRegions() {
        return regionService.findAll().stream().toList();
    }

    public List<PokemonType> getAllPokemonTypes() {
        return List.of(PokemonType.values());
    }

    public Pokemon getUserInputPokemon() {
        return userInputPokemon;
    }

    public void setUserInputPokemon(Pokemon userInputPokemon) {
        this.userInputPokemon = userInputPokemon;
    }
}
