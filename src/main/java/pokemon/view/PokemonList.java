package pokemon.view;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import pokemon.entity.Pokemon;
import pokemon.entity.PokemonType;
import pokemon.entity.Region;
import pokemon.service.PokemonService;
import pokemon.service.RegionService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class PokemonList implements Serializable {
    @Inject
    private PokemonService pokemonService;
    @Inject
    private RegionService regionService;
    @Inject
    private HttpServletRequest request;

    // Filter fields
    private String nameFilter;
    private PokemonType typeFilter;
    private Region regionFilter;
    private Integer levelFilter;
    private Integer hitPointsFilter;
    private Integer attackFilter;
    private Integer defenceFilter;
    private Integer specialAttackFilter;
    private Integer specialDefenceFilter;
    private Integer speedFilter;

    private Collection<Pokemon> filteredPokemons;

    @PostConstruct
    public void init() {
        applyFilters();
    }

    public void applyFilters() {
        filteredPokemons = pokemonService.findByFilters(
            nameFilter, typeFilter, regionFilter, levelFilter,
            hitPointsFilter, attackFilter, defenceFilter,
            specialAttackFilter, specialDefenceFilter, speedFilter
        );
    }

    public void clearFilters() {
        nameFilter = null;
        typeFilter = null;
        regionFilter = null;
        levelFilter = null;
        hitPointsFilter = null;
        attackFilter = null;
        defenceFilter = null;
        specialAttackFilter = null;
        specialDefenceFilter = null;
        speedFilter = null;
        applyFilters();
    }

    public Collection<Pokemon> getPokemons() {
        return filteredPokemons != null ? filteredPokemons : pokemonService.findAll();
    }

    public List<Region> getAllRegions() {
        return regionService.findAll().stream().toList();
    }

    public List<PokemonType> getAllPokemonTypes() {
        return List.of(PokemonType.values());
    }

    public void remove(String id) {
        pokemonService.delete(UUID.fromString(id));
        applyFilters();
    }

    public boolean isAdmin() {
        return request.isUserInRole("ADMIN");
    }

    // Getters and setters
    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public PokemonType getTypeFilter() {
        return typeFilter;
    }

    public void setTypeFilter(PokemonType typeFilter) {
        this.typeFilter = typeFilter;
    }

    public Region getRegionFilter() {
        return regionFilter;
    }

    public void setRegionFilter(Region regionFilter) {
        this.regionFilter = regionFilter;
    }

    public Integer getLevelFilter() {
        return levelFilter;
    }

    public void setLevelFilter(Integer levelFilter) {
        this.levelFilter = levelFilter;
    }

    public Integer getHitPointsFilter() {
        return hitPointsFilter;
    }

    public void setHitPointsFilter(Integer hitPointsFilter) {
        this.hitPointsFilter = hitPointsFilter;
    }

    public Integer getAttackFilter() {
        return attackFilter;
    }

    public void setAttackFilter(Integer attackFilter) {
        this.attackFilter = attackFilter;
    }

    public Integer getDefenceFilter() {
        return defenceFilter;
    }

    public void setDefenceFilter(Integer defenceFilter) {
        this.defenceFilter = defenceFilter;
    }

    public Integer getSpecialAttackFilter() {
        return specialAttackFilter;
    }

    public void setSpecialAttackFilter(Integer specialAttackFilter) {
        this.specialAttackFilter = specialAttackFilter;
    }

    public Integer getSpecialDefenceFilter() {
        return specialDefenceFilter;
    }

    public void setSpecialDefenceFilter(Integer specialDefenceFilter) {
        this.specialDefenceFilter = specialDefenceFilter;
    }

    public Integer getSpeedFilter() {
        return speedFilter;
    }

    public void setSpeedFilter(Integer speedFilter) {
        this.speedFilter = speedFilter;
    }
}
