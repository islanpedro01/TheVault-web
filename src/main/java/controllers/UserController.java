package controllers;

import annotations.Controller;
import annotations.Route;
import auth.AuthMiddleware;
import http.HttpRequest;
import http.HttpResponse;

@Controller
public class UserController {

    @Route(path = "/users", method = "GET")
    public void listUsers(HttpRequest req, HttpResponse resp) {
        AuthMiddleware.requireAuth((request, response) -> {
            response.setStatus(200);
            response.setBody("[{\"id\": 1, \"name\": \"Usuário Exemplo\"}]");
        }).accept(req, resp);
    }

    @Route(path = "/users/{id}", method = "GET")
    public static void getUser(HttpRequest req, HttpResponse resp) {
        String userId = req.getPathParam("param1"); // Pega o primeiro parâmetro da URL
        resp.setStatus(200);
        resp.addHeader("Content-Type", "application/json");
        resp.setBody("{ \"userId\": \"" + userId + "\" }");
    }

    @Route(path = "/users", method = "POST")
    public void createUser(HttpRequest req, HttpResponse resp) {
        try {
            resp.setBody("Usuário criado com sucesso!");
            resp.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Route(path = "/users", method = "DELETE")
    public void deleteUser(HttpRequest req, HttpResponse resp) {
        try {
            resp.setBody("Usuário excluído!");
            resp.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

