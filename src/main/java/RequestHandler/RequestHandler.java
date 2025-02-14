package RequestHandler;

import Http.HttpRequest;
import Http.HttpResponse;
import Router.Router;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.function.BiConsumer;

public class RequestHandler extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getRequestURI();
        String method = req.getMethod(); // Pega o metodo HTTP da requisicao

        // Obtém o handler com base na rota e no metodo HTTP
        BiConsumer<HttpRequest, HttpResponse> handler = Router.getHandler(path, method);

        if (handler != null) {
            // Criamos objetos HttpRequest e HttpResponse para encapsular a requisição e resposta
            HttpRequest request = new HttpRequest(req);
            HttpResponse response = new HttpResponse(resp);
            // Passamos os objetos para o handler correspondente
            handler.accept(request, response);
            // Envia a resposta ao cliente
            response.send();
        } else{
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("404 - Rota não encontrada");
        }

    }

}
