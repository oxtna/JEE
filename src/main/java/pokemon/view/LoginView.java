package pokemon.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;

@Named
@RequestScoped
public class LoginView implements Serializable {
    private String username;
    private String password;
    private boolean error;
    @Inject
    private SecurityContext securityContext;
    @Inject
    private FacesContext facesContext;

    public String login() {
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                (HttpServletRequest) facesContext.getExternalContext().getRequest(),
                (HttpServletResponse) facesContext.getExternalContext().getResponse(),
                jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams().credential(credential)
        );

        if (status == AuthenticationStatus.SEND_CONTINUE) {
            facesContext.responseComplete();
        } else if (status == AuthenticationStatus.SEND_FAILURE) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Błąd logowania", "Nieprawidłowa nazwa użytkownika lub hasło"));
            return null;
        }

        return "/index.xhtml?faces-redirect=true";
    }

    public String logout() {
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            request.logout();
            facesContext.getExternalContext().invalidateSession();
        } catch (ServletException e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Błąd", "Nie udało się wylogować"));
            e.printStackTrace();
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public String getTrainerName() {
        return securityContext.getCallerPrincipal().getName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
