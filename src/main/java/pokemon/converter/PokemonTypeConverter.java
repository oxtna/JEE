package pokemon.converter;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import pokemon.entity.PokemonType;
import java.io.Serializable;

@Dependent
@FacesConverter(value = "pokemonTypeConverter", managed = true)
public class PokemonTypeConverter implements Converter<PokemonType>, Serializable {
    @Override
    public PokemonType getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return PokemonType.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, PokemonType type) {
        return type == null ? "" : type.name();
    }
}
