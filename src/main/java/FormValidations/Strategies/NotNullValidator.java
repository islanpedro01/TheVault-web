package FormValidations.Strategies;

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

