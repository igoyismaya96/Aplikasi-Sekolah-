package session;

public class Session {
    public static String username;
    public static String role;

    public static void setSession(String user, String rl) {
        username = user;
        role = rl;
    }

    public static void clear() {
        username = null;
        role = null;
    }
}