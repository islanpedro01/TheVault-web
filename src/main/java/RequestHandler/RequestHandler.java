package RequestHandler;

import Router.Router;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.function.BiConsumer;

public class RequestHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        String method = req.getMethod(); // Pega o metodo HTTP da requisicao

        BiConsumer<HttpServletRequest, HttpServletResponse> handler = Router.getHandler(path, method);

        if (handler != null) {
            handler.accept(req, resp);
        } else{
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("404 - Rota não encontrada");
        }

    }

}
