package FormValidations;

public interface ValidationStrategy {
    boolean isValid(Object value);
    String getErrorMessage();
}