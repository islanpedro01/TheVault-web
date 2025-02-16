package FormValidations.Strategies;

import FormValidations.ValidationStrategy;

import java.util.regex.Pattern;

public class EmailValidator implements ValidationStrategy {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public boolean isValid(Object value) {
        if (value instanceof String email) {
            return EMAIL_PATTERN.matcher(email).matches();
        }
        return false;
    }

    @Override
    public String getErrorMessage() {
        return "O e-mail informado é inválido.";
    }
}

