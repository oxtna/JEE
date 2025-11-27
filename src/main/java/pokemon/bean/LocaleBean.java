package pokemon.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleBean implements Serializable {
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
