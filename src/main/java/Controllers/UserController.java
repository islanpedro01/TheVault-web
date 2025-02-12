package Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserController {
    public static void listUsers(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json");
            resp.getWriter().println("[{\"id\":1, \"name\":\"Ash Ketchum\"}, {\"id\":2, \"name\":\"Misty\"}]");
        } catch (IOException e) {
            e.printStackTrace();
        }
}}
