package formvalidations.strategies;

import formvalidations.ValidationStrategy;

public class MaxLengthValidator implements ValidationStrategy {
    private final int max;
    private final String message;

    public MaxLengthValidator(int max, String message) {
        this.max = max;
        this.message = message;
    }

    @Override
    public boolean isValid(Object value) {
        return value instanceof String str && str.length() <= max;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
