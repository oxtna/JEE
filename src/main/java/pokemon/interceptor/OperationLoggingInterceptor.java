package pokemon.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

import java.util.UUID;
import java.util.logging.Logger;

@OperationLogged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class OperationLoggingInterceptor {

    private static final Logger LOGGER = Logger.getLogger(OperationLoggingInterceptor.class.getName());

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object logOperation(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String username = getUsername();
        Object[] parameters = context.getParameters();

        if (methodName.equals("create") || methodName.equals("save") || methodName.equals("update")) {
            UUID resourceId = extractResourceId(parameters);
            String operation = methodName.equals("create") || methodName.equals("save") ? "CREATE" : "UPDATE";
            LOGGER.info(String.format("User '%s' is performing operation: %s on resource ID: %s",
                    username, operation, resourceId));
        } else if (methodName.equals("delete")) {
            UUID resourceId = extractResourceId(parameters);
            LOGGER.info(String.format("User '%s' is performing operation: DELETE on resource ID: %s",
                    username, resourceId));
        }

        Object result = context.proceed();

        if (methodName.equals("create") || methodName.equals("save") || methodName.equals("update")) {
            UUID resourceId = extractResourceId(parameters);
            String operation = methodName.equals("create") || methodName.equals("save") ? "CREATE" : "UPDATE";
            LOGGER.info(String.format("User '%s' successfully completed operation: %s on resource ID: %s",
                    username, operation, resourceId));
        } else if (methodName.equals("delete")) {
            UUID resourceId = extractResourceId(parameters);
            LOGGER.info(String.format("User '%s' successfully completed operation: DELETE on resource ID: %s",
                    username, resourceId));
        }

        return result;
    }

    private String getUsername() {
        try {
            if (securityContext != null && securityContext.getCallerPrincipal() != null) {
                return securityContext.getCallerPrincipal().getName();
            }
        } catch (Exception e) {
            LOGGER.warning("Could not retrieve username: " + e.getMessage());
        }
        return "UNKNOWN";
    }

    private UUID extractResourceId(Object[] parameters) {
        if (parameters != null && parameters.length > 0) {
            Object param = parameters[0];

            if (param instanceof UUID) {
                return (UUID) param;
            }

            try {
                java.lang.reflect.Method getIdMethod = param.getClass().getMethod("getId");
                Object id = getIdMethod.invoke(param);
                if (id instanceof UUID) {
                    return (UUID) id;
                }
            } catch (Exception e) {
                // Ignore
            }
        }
        return null;
    }
}
