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

    public static void registerControllers() {
        try {
            // Detecta o pacote base automaticamente a partir da classe principal
            String basePackage = getBasePackage();
            System.out.println("📌 Pacote base detectado: " + basePackage);

            if (basePackage == null) {
                throw new RuntimeException("Não foi possível detectar o pacote base.");
            }

            // Obtemos o caminho da pasta src/main/java
            String sourceDir = "src/main/java/";

            // Escaneia os controladores dentro da pasta src/main/java e seus subpacotes
            Set<Class<?>> controllers = findClasses(sourceDir, basePackage);

            if (controllers.isEmpty()) {
                System.out.println("⚠️ Nenhum controlador encontrado no pacote: " + basePackage);
            }

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

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao registrar controladores.", e);
        }
    }

    // Metodo para buscar automaticamente o pacote base a partir da classe principal
    private static String getBasePackage() {
        // Detecta a classe principal com o metodo main()
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        try {
            // Obtém a classe principal (aquela com o main())
            Class<?> mainClass = Class.forName(className);
            Package mainPackage = mainClass.getPackage();
            if (mainPackage != null) {
                // Retorna o nome do pacote principal
                return mainPackage.getName();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null; // Caso não consiga identificar o pacote
    }

    // Metodo para encontrar classes com a anotação Controller dentro de src/main/java
    private static Set<Class<?>> findClasses(String sourceDir, String basePackage) throws ClassNotFoundException {
        // Caminho completo da pasta, com base no diretório src/main/java
        File directory = new File(sourceDir);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new ClassNotFoundException("Não foi possível encontrar o diretório: " + sourceDir);
        }

        // Cria uma busca recursiva para encontrar as classes
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> allControllers = reflections.getTypesAnnotatedWith(Controller.class);

        return allControllers;
    }
}
