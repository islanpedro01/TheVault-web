package auth;

import http.HttpRequest;
import http.HttpResponse;

import java.util.function.BiConsumer;

public class AuthMiddleware {
    public static BiConsumer<HttpRequest, HttpResponse> requireAuth(BiConsumer<HttpRequest, HttpResponse> handler) {
        return (req, res) -> {
            String token = req.getHeader("authorization");
            if (token == null || !AuthService.isAuthenticated(token)) {
                res.setStatus(401);
                res.setBody("401 - Não autorizado");
            } else {
                handler.accept(req, res);
            }
        };
    }
}
