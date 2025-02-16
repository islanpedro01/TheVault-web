package request;

import http.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class HttpRequestMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T map(HttpRequest request, Class<T> dtoClass) throws Exception {
        try {
            return objectMapper.readValue(request.getInputStream(), dtoClass);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter JSON para DTO: " + dtoClass.getSimpleName(), e);
        }
    }
}
