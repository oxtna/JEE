package pokemon.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import pokemon.entity.Region;
import pokemon.service.RegionService;

@Named
@ViewScoped
public class RegionList implements Serializable {
    @Inject
    private RegionService regionService;

    public List<Region> getRegions() {
        return regionService.findAll().stream().toList();
    }

    public String remove(String id) {
        regionService.delete(UUID.fromString(id));
        return "region-list.xhtml?faces-redirect=true";
    }
}
