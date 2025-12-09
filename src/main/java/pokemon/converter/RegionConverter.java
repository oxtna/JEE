package pokemon.converter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pokemon.entity.Region;
import pokemon.service.RegionService;
import java.io.Serializable;
import java.util.Optional;
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
        try {
            UUID uuid = UUID.fromString(value);
            Optional<Region> region = regionService.find(uuid);
            if (region.isEmpty()) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Region not found", "Region with ID " + uuid + " does not exist"));
            }
            return region.get();
        } catch (IllegalArgumentException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid UUID", "The provided value is not a valid UUID"));
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Region region) {
        return region == null ? "" : region.getId().toString();
    }
}
