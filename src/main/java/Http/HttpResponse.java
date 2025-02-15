package Http;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HttpResponse {
    private HttpServletResponse response;
    private int status = 200;
    private Map<String, String> headers = new HashMap<>();
    private String body = "";
    private String sessionToken;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public HttpResponse(HttpServletResponse response) {
        this.response = response;
        this.sessionToken = null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Metodo para escrever JSON na resposta
    public void writeJson(Object data) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(data);
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSessionToken(String token) {
        this.sessionToken = token;
    }

    public void send() throws IOException {
        response.setStatus(status);
        headers.forEach(response::setHeader);
        response.getWriter().write(body);
        if (sessionToken != null) {
            response.setHeader("Authorization", sessionToken);
        }
    }
}

