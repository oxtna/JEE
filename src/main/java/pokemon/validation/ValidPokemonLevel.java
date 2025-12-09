package pokemon.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PokemonLevelValidator.class)
@Documented
public @interface ValidPokemonLevel {
    String message() default "{pokemon.level.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
