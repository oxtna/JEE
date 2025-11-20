package pokemon.converter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pokemon.entity.Region;
import pokemon.service.RegionService;
import java.io.Serializable;
import java.util.UUID;

@ApplicationScoped
@FacesConverter(value = "regionConverter", managed = true)
public class RegionConverter implements Converter<Region>, Serializable {
    @Inject
    private RegionService regionService;

    @Override
    public Region getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return regionService.find(UUID.fromString(value)).orElse(null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Region region) {
        return region == null ? "" : region.getId().toString();
    }
}
