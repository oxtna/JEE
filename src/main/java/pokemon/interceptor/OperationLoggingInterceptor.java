package pokemon.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.logging.Logger;

@OperationLogged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class OperationLoggingInterceptor {
    private static final Logger logger = Logger.getLogger(OperationLoggingInterceptor.class.getName());

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        String methodName = ctx.getMethod().getName();
        String username = getUsername();
        UUID resourceId = getResourceId(ctx.getParameters());
        switch (methodName) {
            case "save" -> logger.info(String.format("User %s is saving resource %s", username, resourceId));
            case "update" -> logger.info(String.format("User %s is updating resource %s", username, resourceId));
            case "delete" -> logger.info(String.format("User %s is deleting resource %s", username, resourceId));
        }
        Object result = ctx.proceed();
        switch (methodName) {
            case "save" -> logger.info(String.format("User %s has saved resource %s", username, resourceId));
            case "update" -> logger.info(String.format("User %s has updated resource %s", username, resourceId));
            case "delete" -> logger.info(String.format("User %s has deleted resource %s", username, resourceId));
        }
        return result;
    }

    private String getUsername() {
        try {
            if (securityContext != null && securityContext.getCallerPrincipal() != null) {
                return securityContext.getCallerPrincipal().getName();
            }
        } catch (Exception e) {
            logger.warning("Failed to get username: " + e.getMessage());
        }
        return "[#]";
    }

    private UUID getResourceId(Object[] params) {
        if (params != null && params.length > 0) {
            if (params[0] instanceof UUID) {
                return (UUID) params[0];
            }
            return tryInvokeGetId(params[0]);
        }
        return null;
    }

    private UUID tryInvokeGetId(Object param) {
        try {
            Method getIdMethod = param.getClass().getMethod("getId");
            Object id = getIdMethod.invoke(param);
            if (id instanceof UUID) {
                return (UUID) id;
            }
        } catch (Exception ignored) {}
        return null;
    }
}
