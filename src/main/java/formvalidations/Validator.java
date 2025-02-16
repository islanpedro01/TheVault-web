package formvalidations;

import formvalidations.strategies.EmailValidator;
import formvalidations.strategies.NotNullValidator;

import java.lang.reflect.Field;
import java.util.*;

public class Validator {
    private static final Map<Class<?>, ValidationStrategy> strategies = new HashMap<>();

    static {
        strategies.put(NotNull.class, new NotNullValidator());
        strategies.put(Email.class, new EmailValidator());
    }

    public static List<String> validate(Object dto) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = dto.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(dto);

                // Verifica cada anotação do campo
                for (var annotation : field.getAnnotations()) {
                    ValidationStrategy strategy = strategies.get(annotation.annotationType());
                    if (strategy != null && !strategy.isValid(value)) {
                        errors.add(field.getName() + ": " + strategy.getErrorMessage());
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Erro ao acessar campo: " + field.getName());
            }
        }

        return errors;
    }
}
