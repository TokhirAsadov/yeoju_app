package uz.yeoju.yeoju_app.exceptions;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Getter
public class ValidationError extends RuntimeException {
    private final Set<ConstraintViolation<Object>> violations;

    public ValidationError(String message, Set<ConstraintViolation<Object>> violations) {
        super(message);
        this.violations = violations;
    }
}
