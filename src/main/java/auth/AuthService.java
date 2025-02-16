package auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthService {
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, String> sessions = new HashMap<>();
    private static final Map<String, Map<String, String>> sessionData = new HashMap<>();

    static{
// Simulacao
        users.put("admin", "1234");
        users.put("user", "abcd");

    }

    public static String login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            String token = UUID.randomUUID().toString();
            sessions.put(token, username);
            return token;
        }
        return null;
    }

    public static boolean isAuthenticated(String token) {
        return sessions.containsKey(token);
    }

    public static Map<String, String> getSessionData(String token) {
        return sessionData.get(token);
    }

    public static void setSessionData(String token, String key, String value) {
        sessionData.computeIfAbsent(token, k -> new HashMap<>()).put(key, value);
    }

    public static String getUser(String token) {
        return sessions.get(token);
    }

    public static void logout(String token) {
        sessions.remove(token);
        sessionData.remove(token);
    }
}

