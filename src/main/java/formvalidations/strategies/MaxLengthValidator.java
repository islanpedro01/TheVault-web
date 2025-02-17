package formvalidations.strategies;

import formvalidations.ValidationStrategy;

public class MaxLengthValidator implements ValidationStrategy {
    private final int max;


    public MaxLengthValidator(int max) {
        this.max = max;
    }

    @Override
    public boolean isValid(Object value) {
        return value instanceof String str && str.length() <= max;
    }

    @Override
    public String getErrorMessage() {
        return "O campo deve ter menos de " + this.max + " caracteres.";
    }
}
