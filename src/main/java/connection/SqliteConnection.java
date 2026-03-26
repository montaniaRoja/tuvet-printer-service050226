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
                    sucursal TEXT,
                    impresora TEXT,
                    copias INT
                )
                """;
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);

            
            String selectSQL = "SELECT sucursal FROM computer_info LIMIT 1";
            ResultSet rs = stmt.executeQuery(selectSQL);

            if (!rs.next()) {                
               
                String insertSQL = "INSERT INTO computer_info(sucursal) VALUES(?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                pstmt.setString(1, "sin sucursal");
                pstmt.executeUpdate();
                
            } 

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void guardarSucursal(String sucursal) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Class.forName("org.sqlite.JDBC");

            // Crear tabla si no existe
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS computer_info (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,                    
                    sucursal TEXT,
                    impresora TEXT,
                    copias INT
                )
                """;
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);

            
            String selectSQL = "SELECT sucursal FROM computer_info LIMIT 1";
            ResultSet rs = stmt.executeQuery(selectSQL);

            if (!rs.next()) {
                
               
                String insertSQL = "INSERT INTO computer_info(sucursal) VALUES(?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                pstmt.setString(1, sucursal);
                pstmt.executeUpdate();
                System.out.println("UUID generado: " + sucursal);
            } else {
            	String updateSQL = "UPDATE computer_info set sucursal=?";
                PreparedStatement pstmt = conn.prepareStatement(updateSQL);
                pstmt.setString(1,sucursal);
                pstmt.executeUpdate();
                System.out.println("UUID generado: " + sucursal);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void guardarImpresora(String impresora) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE computer_info SET impresora=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, impresora);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void guardarCopias(int copias) {
    	 try (Connection conn = DriverManager.getConnection(DB_URL)) {
             String sql = "UPDATE computer_info SET copias=?";
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, copias);
             pstmt.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
    	
    }
    
    public static String getImpresora() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT impresora FROM computer_info LIMIT 1");
            if (rs.next()) {
                return rs.getString("impresora");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUUID() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sucursal FROM computer_info LIMIT 1");
            if (rs.next()) {
                return rs.getString("sucursal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static int getCopias() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT copias FROM computer_info LIMIT 1");

            if (rs.next()) {
                return rs.getInt("copias");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1; // valor por defecto
    }
    
    
}