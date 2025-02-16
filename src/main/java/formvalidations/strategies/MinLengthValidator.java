package formvalidations.strategies;

import formvalidations.ValidationStrategy;

public class MinLengthValidator implements ValidationStrategy {
    private final int minLength;

    public MinLengthValidator(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean isValid(Object value) {
        return value instanceof String str && str.length() >= minLength;
    }

    @Override
    public String getErrorMessage() {
        return "O campo deve ter pelo menos " + minLength + " caracteres.";
    }
}

