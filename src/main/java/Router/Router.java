package Router;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Router {
    private static final Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> routes = new HashMap<>();

    public static void addRoute(String path, BiConsumer<HttpServletRequest, HttpServletResponse> handler) {
        routes.put(path, handler);
    }

    public static void handleRequest(String path, HttpServletRequest request, HttpServletResponse response){
        BiConsumer<HttpServletRequest, HttpServletResponse> handler = routes.get(path);

        if (handler != null) {
            handler.accept(request, response);
        }
        else {
            try{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("404 - Rota não encontrada: " + path);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }


}
