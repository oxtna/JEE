package pokemon.bean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleBean implements Serializable {
    private Locale locale;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && context.getExternalContext() != null) {
            Locale browserLocale = context.getExternalContext().getRequestLocale();
            if (browserLocale != null && browserLocale.getLanguage().contains("pl")) {
                locale = new Locale("pl");
            } else {
                locale = Locale.ENGLISH;
            }
        } else {
            locale = Locale.ENGLISH;
        }
    }

    public Locale getLocale() {
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLanguage() {
        return getLocale().getLanguage();
    }
}
