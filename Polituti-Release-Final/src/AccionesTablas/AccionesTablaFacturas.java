package AccionesTablas;

import BaseDeDatos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class AccionesTablaFacturas {
    private Connection cn;
    private DefaultTableModel modelo3;
    private JTable jTable1;
    private JTable JTableCliente;
    private JTextField jTextField5;
    private JTable jTable3;
    private String fechaActual = "";
    private String nombreArchivoPDFVenta = "";

    public AccionesTablaFacturas(Connection cn, DefaultTableModel modelo3) {
        this.cn = cn;
        this.modelo3 = modelo3;
    }

    public AccionesTablaFacturas(JTable jTable1, JTable JTableCliente) {
        this.jTable1 = jTable1;
        this.JTableCliente = JTableCliente;
    }

 






    public void cargarDatosFacturas() {
        try {
            modelo3.setRowCount(0);
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tabladetallesf");
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("codigodetalle");
                fila[1] = rs.getInt("codigofactura");
                fila[2] = rs.getInt("codigoproducto"); 
                fila[3] = rs.getFloat("Cantidad"); 
                fila[4] = rs.getFloat("total");
                fila[5] = rs.getInt("codigocliente");
                modelo3.addRow(fila);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccionesTablaFacturas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar datos de facturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 


}

