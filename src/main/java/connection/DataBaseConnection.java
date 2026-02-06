package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DataBaseConnection {
    private final static String ruta = "jdbc:postgresql://34.29.157.119/tuvetnewdb";
    private final static String usuario = "tuvet-user";
    private final static String contrasenia = "sanofi2025";

    public static Connection connection() throws SQLException{
        Connection dbConecction = DriverManager.getConnection(ruta, usuario, contrasenia);

        return dbConecction;

    }

    public static boolean connectionTesting() {
        try {
            Connection connection=connection();
            JOptionPane.showMessageDialog(null,"Conexion exitosa");
            return true;
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null,"Error en la conexion");
            return false;
        }

    }
}
