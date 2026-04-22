package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/sikad?useSSL=false&serverTimezone=UTC";
                String user = "root";
                String pass = "";

                Class.forName("com.mysql.cj.jdbc.Driver"); // driver baru
                conn = DriverManager.getConnection(url, user, pass);
            }
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}