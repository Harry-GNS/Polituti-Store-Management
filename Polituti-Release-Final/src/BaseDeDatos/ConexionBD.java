package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexionBD {
    private Connection cn;

    public Connection conexion(String usuario, String contrasena) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdpolituti", usuario, contrasena);
            System.out.println("Conexión Correcta");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cn;
    }

    public Statement createStatement() {
        throw new UnsupportedOperationException("No Soporto");
    }
}

