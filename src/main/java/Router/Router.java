package Router;

import Annotations.Controller;
import Annotations.Route;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class Router {
    private static final Map<String, Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>> routes = new HashMap<>();

    public static void addRoute(String path, String method, BiConsumer<HttpServletRequest, HttpServletResponse> handler) {
        routes.computeIfAbsent(path, k -> new HashMap<>()).put(method.toUpperCase(), handler);
    }

    public static BiConsumer<HttpServletRequest, HttpServletResponse> getHandler(String path, String method) {
        return routes.getOrDefault(path, new HashMap<>()).get(method.toUpperCase());
    }

    public static void registerControllers(String basePackage) {

        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

            // Registra as rotas automaticamente
            for (Class<?> controller : controllers) {
                for (Method method : controller.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Route.class)) {
                        Route route = method.getAnnotation(Route.class);
                        String path = route.path();
                        String httpMethod = route.method();

                        // Criamos um handler que invoca o método correto do controlador
                        BiConsumer<HttpServletRequest, HttpServletResponse> handler = (req, resp) -> {
                            try {
                                method.invoke(controller.getDeclaredConstructor().newInstance(), req, resp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        };

                        addRoute(path, httpMethod, handler);
                        System.out.println("✅ Rota registrada: " + httpMethod + " " + path);
                    }
                }
            }

    }


}
