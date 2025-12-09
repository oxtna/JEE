package pokemon.view;

import jakarta.annotation.security.RolesAllowed;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
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
    @Inject
    private HttpServletRequest request;

    public List<Region> getRegions() {
        return regionService.findAll().stream().toList();
    }

    @RolesAllowed("ADMIN")
    public void remove(String id) {
        regionService.delete(UUID.fromString(id));
    }

    public boolean isAdmin() {
        return request.isUserInRole("ADMIN");
    }
}
