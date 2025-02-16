package formvalidations;

public interface ValidationStrategy {
    boolean isValid(Object value);
    String getErrorMessage();
}