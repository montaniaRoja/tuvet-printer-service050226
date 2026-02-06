package connection;

import java.sql.*;
import java.util.UUID;

public class SqliteConnection {

    private static final String DB_URL = "jdbc:sqlite:computers.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Class.forName("org.sqlite.JDBC");

            // Crear tabla si no existe
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS computer_info (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    uuid TEXT NOT NULL
                )
                """;
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);

            // Revisar si ya hay UUID
            String selectSQL = "SELECT uuid FROM computer_info LIMIT 1";
            ResultSet rs = stmt.executeQuery(selectSQL);

            if (!rs.next()) {
                // No existe UUID, creamos uno
                String uuid = UUID.randomUUID().toString();
                String insertSQL = "INSERT INTO computer_info(uuid) VALUES(?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                pstmt.setString(1, uuid);
                pstmt.executeUpdate();
                System.out.println("UUID generado: " + uuid);
            } else {
                System.out.println("UUID existente: " + rs.getString("uuid"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUUID() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT uuid FROM computer_info LIMIT 1");
            if (rs.next()) {
                return rs.getString("uuid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}