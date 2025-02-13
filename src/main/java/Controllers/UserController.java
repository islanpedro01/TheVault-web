package Controllers;

import Annotations.Controller;
import Annotations.Route;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Route(path = "/users", method = "GET")
    public void listUsers(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().println("Lista de usuários");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Route(path = "/users", method = "POST")
    public void createUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().println("Usuário criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Route(path = "/users", method = "DELETE")
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().println("Usuário excluído!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

