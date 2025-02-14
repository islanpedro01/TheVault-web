package Http;

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

    public HttpResponse(HttpServletResponse response) {
        this.response = response;
        this.sessionToken = null;
    }

    public void setStatus(int status) {
        this.status = status;
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

