package pokemon.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PokemonLevelValidator implements ConstraintValidator<ValidPokemonLevel, Integer> {

    @Override
    public void initialize(ValidPokemonLevel constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer level, ConstraintValidatorContext context) {
        if (level == null) {
            return true;
        }

        return level >= 1 && level <= 99;
    }
}
