package pokemon.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "/login.xhtml?error=true"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Pokemon",
        callerQuery = "select password from trainers where login = ?",
        groupsQuery = "select role from trainers where login = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthConfig {
}
