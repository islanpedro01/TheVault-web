package controllers;

import annotations.Controller;
import annotations.Route;
import auth.AuthMiddleware;
import dto.UserDTO;
import formvalidations.Validator;
import http.HttpRequest;
import http.HttpResponse;
import request.HttpRequestMapper;

import java.util.List;

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
            // Mapeia o corpo da requisição para o UserDTO
            UserDTO userDTO = HttpRequestMapper.map(req, UserDTO.class);

            // Valida o DTO
            List<String> validationErrors = Validator.validate(userDTO);

            if (!validationErrors.isEmpty()) {
                // Se houver erros de validação, retorna uma resposta com status 400 (Bad Request)
                resp.setStatus(400);
                resp.setBody("Erros de validação: " + validationErrors);
                return;
            }

            // Se não houver erros, processa a criação do usuário
            resp.setStatus(201); // 201 Created
            resp.setBody("Usuário criado com sucesso: " + userDTO.getName() + ", " + userDTO.getEmail());
        } catch (Exception e) {
            resp.setStatus(500); // 500 Internal Server Error
            resp.setBody("Erro interno ao processar a requisição: " + e.getMessage());
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

