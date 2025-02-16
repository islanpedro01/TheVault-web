package formvalidations.strategies;

import formvalidations.ValidationStrategy;

public class NotNullValidator implements ValidationStrategy {
    @Override
    public boolean isValid(Object value) {
        return value != null;
    }

    @Override
    public String getErrorMessage() {
        return "O campo não pode ser nulo.";
    }
}

