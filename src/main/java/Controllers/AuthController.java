package Controllers;


import Annotations.Controller;
import Annotations.Route;
import Auth.AuthService;
import Http.HttpRequest;
import Http.HttpResponse;

@Controller
public class AuthController {
    @Route(path = "/login", method = "POST")
    public static void login(HttpRequest req, HttpResponse res) {
        String username = req.getQueryParam("username");
        String password = req.getQueryParam("password");

        String token = AuthService.login(username, password);
        if (token != null) {
            res.setStatus(200);
            res.setBody("{ \"token\": \"" + token + "\" }");
        } else {
            res.setStatus(401);
            res.setBody("401 - Credenciais inválidas");
        }
    }

    @Route(path = "/logout", method = "POST")
    public static void logout(HttpRequest req, HttpResponse res) {
        String token = req.getHeader("authorization");
        if (token != null) {
            AuthService.logout(token);
            res.setStatus(200);
            res.setBody("Logout realizado com sucesso");
        } else {
            res.setStatus(400);
            res.setBody("400 - Nenhum token fornecido");
        }
    }
}
