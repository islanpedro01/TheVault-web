package Router;

import Annotations.Controller;
import Annotations.Route;
import Http.HttpRequest;
import Http.HttpResponse;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {
    private static final Map<String, Map<String, BiConsumer<HttpRequest, HttpResponse>>> routes = new HashMap<>();
    private static final List<RoutePattern> dynamicRoutes = new ArrayList<>();


    public static void addRoute(String path, String method, BiConsumer<HttpRequest, HttpResponse> handler) {
        if (path.contains("{")) { // Se a rota tem parâmetros dinâmicos
            String regex = path.replaceAll("\\{\\w+}", "([^/]+)"); // Converte {id} para regex
            dynamicRoutes.add(new RoutePattern(Pattern.compile("^" + regex + "$"), method.toUpperCase(), handler));
        } else {
            routes.computeIfAbsent(path, k -> new HashMap<>()).put(method.toUpperCase(), handler);
        }
    }

    // Pega o handler com base na rota e metodo HTTP
    public static BiConsumer<HttpRequest, HttpResponse> getHandler(String path, String method) {

        // Verifica primeiro rotas estáticas
        if (routes.containsKey(path) && routes.get(path).containsKey(method.toUpperCase())) {
            return routes.get(path).get(method.toUpperCase());
        }
        // Verifica rotas dinâmicas
        for (RoutePattern route : dynamicRoutes) {
            Matcher matcher = route.pattern.matcher(path);
            if (matcher.matches() && route.method.equalsIgnoreCase(method)) {
                return (req, res) -> {
                    // Adiciona os parâmetros extraídos ao request
                    for (int i = 0; i < matcher.groupCount(); i++) {
                        req.addPathParam("param" + (i + 1), matcher.group(i + 1));
                    }
                    route.handler.accept(req, res);
                };
            }
        }
        return null;
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

                        // Criamos um handler que invoca o metodo correto do controlador
                        BiConsumer<HttpRequest, HttpResponse> handler = (req, resp) -> {
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
    // Classe interna para armazenar rotas dinâmicas
    private static class RoutePattern {
        Pattern pattern;
        String method;
        BiConsumer<HttpRequest, HttpResponse> handler;

        public RoutePattern(Pattern pattern, String method, BiConsumer<HttpRequest, HttpResponse> handler) {
            this.pattern = pattern;
            this.method = method;
            this.handler = handler;
        }
    }


}
