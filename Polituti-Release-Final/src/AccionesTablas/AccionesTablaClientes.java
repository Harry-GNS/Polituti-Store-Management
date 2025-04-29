package AccionesTablas;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;

public class AccionesTablaClientes {
    private Connection cn;
    private DefaultTableModel modelo;

    public AccionesTablaClientes(Connection cn, DefaultTableModel modelo) {
        this.cn = cn;
        this.modelo = modelo;
        
        
    }
    public void insertarCliente(String nombre, int telefono, int cedula) throws SQLException {
    PreparedStatement pps = cn.prepareStatement(
        "INSERT INTO tablaproducto (Nombre_Cliente, Numero_Telefono, Numero_Cedula) VALUES (?, ?, ?)"
    );
    pps.setString(1, nombre);
    pps.setFloat(2, telefono);
    pps.setInt(3, cedula);
    pps.executeUpdate();
}
    public void cargarDatos(DefaultTableModel modelo) {
        modelo.setRowCount(0); // Esto debe funcionar si el modelo no es null
        // Lógica para cargar datos en el modelo
    }

    public void eliminarCliente(int filaSeleccionada, JTable jTablaClienteCompleto, DefaultTableModel modelo, Connection cn) {
        if (filaSeleccionada >= 0) {
            String nombreCliente = (String) jTablaClienteCompleto.getValueAt(filaSeleccionada, 1);
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el cliente '"
                    + nombreCliente + "'?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                int codigoCliente = (int) jTablaClienteCompleto.getValueAt(filaSeleccionada, 0);
                try {
                    // Verificar si el cliente está relacionado en tabladetallesf o tablaencabezadof
                    String verificarQuery = "SELECT COUNT(*) FROM tabladetallesf WHERE codigocliente = ? "
                                          + "UNION ALL SELECT COUNT(*) FROM tablaencabezadof WHERE codigocliente = ?";
                    PreparedStatement verificarStmt = cn.prepareStatement(verificarQuery);
                    verificarStmt.setInt(1, codigoCliente);
                    verificarStmt.setInt(2, codigoCliente);
                    ResultSet rs = verificarStmt.executeQuery();
                    boolean relacionado = false;
                    while (rs.next()) {
                        if (rs.getInt(1) > 0) {
                            relacionado = true;
                            break;
                        }
                    }

                    if (relacionado) {
                        JOptionPane.showMessageDialog(null, "No se puede eliminar el cliente " + nombreCliente +
                                " porque pertenece a alguna Factura");
                    } else {
                        // Eliminar cliente
                        String query = "DELETE FROM tablacliente WHERE codigocliente = ?";
                        PreparedStatement pstmt = cn.prepareStatement(query);
                        pstmt.setInt(1, codigoCliente);
                        int resultado = pstmt.executeUpdate();

                        if (resultado > 0) {
                            modelo.removeRow(filaSeleccionada);
                            JOptionPane.showMessageDialog(null, "Cliente '" + nombreCliente + "' eliminado correctamente");

                            jTablaClienteCompleto.clearSelection();

                            // Llamar a actualizarTabla si es necesario
                            // actualizarTabla();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente");
                        }

                        pstmt.close();
                    }

                    rs.close();
                    verificarStmt.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
        }
    }

    
      public void ocultarComponentes(javax.swing.JTextField jTBuscarCodigo, 
                                   javax.swing.JTextField jTBuscarCedula, 
                                   javax.swing.JTextField jTBuscarNombre, 
                                   javax.swing.JLabel jLCedula, 
                                   javax.swing.JLabel jLCodigo, 
                                   javax.swing.JLabel jLNombre, 
                                   javax.swing.JPanel panelEditar, 
                                   javax.swing.JButton jBActualizarDatos) {
        jTBuscarCodigo.setVisible(false);
        jTBuscarCedula.setVisible(false);
        jTBuscarNombre.setVisible(false);
        jLCedula.setVisible(false);
        jLCodigo.setVisible(false);
        jLNombre.setVisible(false);
        panelEditar.setVisible(false);
        
        jBActualizarDatos.setVisible(false);
        
    }

    public void actualizarCliente(int idCliente, String nuevoNombre, String nuevoTelefono, String nuevaCedula) throws SQLException {
        PreparedStatement pps = cn.prepareStatement(
            "UPDATE tablacliente SET Nombre_Cliente = ?, N_telefono = ?, N_cedula = ? WHERE codigocliente = ?"
        );
        pps.setString(1, nuevoNombre);
        pps.setString(3, nuevoTelefono);
        pps.setString(2, nuevaCedula);
        pps.setInt(4, idCliente);
        pps.executeUpdate();
    }

    public void insertarCliente(String nombre, String telefono, String cedula) throws SQLException {
    PreparedStatement pps = cn.prepareStatement(
        "INSERT INTO tablacliente (Nombre_Cliente, N_telefono, N_cedula) VALUES (?, ?, ?)"
    );
    pps.setString(1, nombre);
    pps.setString(2, telefono);
    pps.setString(3, cedula);
    pps.executeUpdate();
}
    

public void manejarInsercionCliente(javax.swing.JTextField JTFNCliente, javax.swing.JTextField JTFNTelefono, javax.swing.JTextField JTFNCedula) {
    try {
        String nombre = capitalizarNombre(JTFNCliente.getText());
        String telefono = JTFNTelefono.getText();
        String cedula = JTFNCedula.getText();

        insertarCliente(nombre, telefono, cedula);
        cargarDatos(); 
        

    } catch (SQLException ex) {
        Logger.getLogger(AccionesTablaClientes.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error al insertar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private String capitalizarNombre(String nombre) {
    if (nombre == null || nombre.isEmpty()) {
        return nombre;
    }
    String[] partes = nombre.split(" ");
    StringBuilder nombreCapitalizado = new StringBuilder();
    for (String parte : partes) {
        if (!parte.isEmpty()) {
            nombreCapitalizado.append(Character.toUpperCase(parte.charAt(0)))
                .append(parte.substring(1).toLowerCase())
                .append(" ");
        }
    }
    return nombreCapitalizado.toString().trim();
}
public boolean validarCodigoProvincia(String codigo) {
    try {
        int codigoProvincia = Integer.parseInt(codigo);
        if (codigoProvincia >= 1 && codigoProvincia <= 30) {
            return true;
        } else {
            // Muestra el mensaje de error
            JOptionPane.showMessageDialog(null, "Código de provincia no válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException e) {
        // En caso de error en la conversión a entero
        JOptionPane.showMessageDialog(null, "Código de provincia no válido", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}



    public void cargarDatos() {
        try {
            modelo.setRowCount(0);

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tablacliente");
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("codigocliente");
                fila[1] = rs.getString("Nombre_Cliente");
                fila[2] = rs.getString("N_telefono");
                fila[3] = rs.getString("N_cedula");
                modelo.addRow(fila);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccionesTablaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarDatostablafactura() {
    try {
        modelo.setRowCount(0);

        // Cambiar la consulta SQL para seleccionar solo nombre y cédula
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery("SELECT codigocliente, Nombre_Cliente, N_cedula, N_telefono FROM tablacliente");
        
        while (rs.next()) {
            Object[] fila = new Object[4]; // Solo 2 columnas
            fila[0] = rs.getString("codigocliente");
            fila[1] = rs.getString("Nombre_Cliente");
            fila[2] = rs.getString("N_cedula");
            fila[3] = rs.getString("N_telefono");
            modelo.addRow(fila);
        }
        rs.close();
        st.close();
    } catch (SQLException ex) {
        Logger.getLogger(AccionesTablaClientes.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public void manejarModifiCliente(JTable table, javax.swing.JTextField jFNombreEditar, javax.swing.JTextField jFTelefonoEditar, javax.swing.JTextField jFCedulaEditar, javax.swing.JPanel panelEditar) {
        int filaSeleccionada = table.getSelectedRow();

        if (filaSeleccionada >= 0) {
            panelEditar.setVisible(true);
            String nombreCliente = (String) table.getValueAt(filaSeleccionada, 1);
            String telefonoCliente = (String) table.getValueAt(filaSeleccionada, 2);
            String cedulaCliente = (String) table.getValueAt(filaSeleccionada, 3);

            jFNombreEditar.setText(nombreCliente);
            jFTelefonoEditar.setText(telefonoCliente);
            jFCedulaEditar.setText(cedulaCliente);
        } else {
            panelEditar.setVisible(false);
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void manejarActualizacionCliente(int filaSeleccionada, String nuevoNombre, String nuevoTelefono, String nuevaCedula, JTable table, javax.swing.JPanel panelEditar) {
        if (filaSeleccionada >= 0) {
            panelEditar.setVisible(true);

            String nombreCliente = (String) table.getValueAt(filaSeleccionada, 1);
            
            
            String telefonoCliente = (String) table.getValueAt(filaSeleccionada, 2);
            String cedulaCliente = (String) table.getValueAt(filaSeleccionada, 3);

            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de actualizar el cliente '"
                    + nombreCliente + "'?", "Confirmar actualización", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    int idCliente = Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());

                    actualizarCliente(idCliente, nuevoNombre, nuevoTelefono, nuevaCedula);

                    JOptionPane.showMessageDialog(null, "Cliente '" + nombreCliente + "' actualizado correctamente.");
                    panelEditar.setVisible(false);
                    cargarDatos(); // Actualizar la tabla después de la modificación

                } catch (SQLException ex) {
                    Logger.getLogger(AccionesTablaClientes.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void validarNumeros(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE )) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo números por favor", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void validarSoloLetras(KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!(Character.isLetter(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
        evt.consume();
        JOptionPane.showMessageDialog(null, "Ingrese solo letras, por favor", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
 
    
}
