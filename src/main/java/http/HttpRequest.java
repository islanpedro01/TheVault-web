package http;

import auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> pathParams = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private String body;
    private String sessionToken;
    private HttpServletRequest request;

    public HttpRequest(){}

    public HttpRequest(HttpServletRequest request) {
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.request = request;

        // Pegando os parâmetros da URL (Query String)
        request.getParameterMap().forEach((key, values) -> queryParams.put(key, values[0]));

        // Pegando os cabeçalhos
        request.getHeaderNames().asIterator().forEachRemaining(header -> headers.put(header, request.getHeader(header)));

        // Pegando o body da requisição
//        try (BufferedReader reader = request.getReader()) {
//            StringBuilder bodyBuilder = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                bodyBuilder.append(line).append("\n");
//            }
//            this.body = bodyBuilder.toString().trim();
//        } catch (IOException e) {
//            System.err.println("Erro ao ler o corpo da requisição: " + e.getMessage());
//        }

        // Recuperando o token da sessão a partir do cabeçalho Authorization
        this.sessionToken = request.getHeader("Authorization");
    }
    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getQueryParam(String key) {
        return queryParams.get(key);
    }
    public InputStream getInputStream() throws IOException {
        return request.getInputStream();
    }
    public String getHeader(String name) {
        System.out.println(headers);
        return headers.get(name);
    }
    public String getBody() { return body; }
    public void addPathParam(String key, String value) {
        pathParams.put(key, value);
    }
    public String getPathParam(String key) {
        return pathParams.get(key);
    }
    public String getSessionToken() {
        return sessionToken;
    }

    public Map<String, String> getSessionData() {
        return sessionToken != null ? AuthService.getSessionData(sessionToken) : null;
    }
}
