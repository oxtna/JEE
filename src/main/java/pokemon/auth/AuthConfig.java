package pokemon.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Pokemon")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Pokemon",
        callerQuery = "select password from trainers where login = ?",
        groupsQuery = "select role from trainers where login = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthConfig {
}
