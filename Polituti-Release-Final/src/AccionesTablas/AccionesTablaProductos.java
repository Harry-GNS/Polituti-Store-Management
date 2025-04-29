package AccionesTablas;

import Vista.JFDatosTablaProductos;
import java.awt.event.KeyEvent;
import java.sql.Connection; //Permite crear una conexion con el MySQL   
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AccionesTablaProductos {
    private Connection cn;
    private DefaultTableModel modelo2;

    public AccionesTablaProductos(Connection cn, DefaultTableModel modelo2) {
        this.cn = cn;
        this.modelo2 = modelo2;
    }
  public void ocultarComponentes(javax.swing.JTextField jTBuscarCodigo, 
                                   javax.swing.JTextField jTBuscarPrecio, 
                                   javax.swing.JTextField jTBuscarNombre, 
                                   javax.swing.JLabel jLabel3, 
                                   javax.swing.JLabel jLPrecio, 
                                   javax.swing.JLabel jLNombre, 
                                   javax.swing.JPanel panelEditar) {
        jTBuscarCodigo.setVisible(false);
        jTBuscarPrecio.setVisible(false);
        jTBuscarNombre.setVisible(false);
        jLabel3.setVisible(false);
        jLPrecio.setVisible(false);
        jLNombre.setVisible(false);
        panelEditar.setVisible(false);
    }
  
    public void actualizarCantidadProducto(int idProducto, int nuevaCantidad) throws SQLException {
    PreparedStatement pps = cn.prepareStatement(
            "UPDATE tablaproducto SET Cantidad = ? WHERE codigoproducto = ?"
    );
    pps.setInt(1, nuevaCantidad);
    pps.setInt(2, idProducto);
    pps.executeUpdate();
}
    
    
public void actualizarProducto(int idProducto, String nuevoNombre, float nuevoPrecio, int nuevaCantidad) throws SQLException {
    PreparedStatement pps = cn.prepareStatement(
            "UPDATE tablaproducto SET Nombre_Producto = ?, Precio_Unitario = ?, Cantidad = ? WHERE codigoproducto = ?"
    );
    pps.setString(1, nuevoNombre);
    pps.setFloat(2, nuevoPrecio);
    pps.setInt(3, nuevaCantidad);
    pps.setInt(4, idProducto);
    pps.executeUpdate();
}
    //Para  insertar en el mysql

         public void eliminarProducto(int filaSeleccionada, JTable table, DefaultTableModel modelo, Connection cn) {
         if (filaSeleccionada < 0) {
             JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
             return;
    }
         String nombreProducto = (String) table.getValueAt(filaSeleccionada, 1);
         int codigoProducto = (int) table.getValueAt(filaSeleccionada, 0);
         int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el producto '"+ nombreProducto + "'?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

         if (confirmacion == JOptionPane.YES_OPTION) {  
             try {
                 String query = "DELETE FROM tablaproducto WHERE codigoproducto = ?";
                 PreparedStatement pstmt = cn.prepareStatement(query);
                 pstmt.setInt(1, codigoProducto);
                 int resultado = pstmt.executeUpdate();
                 if (resultado > 0) {
                     
                     modelo.removeRow(filaSeleccionada);
                     JOptionPane.showMessageDialog(null, "Producto '" + nombreProducto + "' eliminado correctamente");
                     cargarDatos2();
                     
                 }else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto");
            }
                 
                 pstmt.close();
             }
             
             catch (SQLException ex) {
                 Logger.getLogger(AccionesTablaProductos.class.getName()).log(Level.SEVERE, null, ex);
                 JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

public void insertarProducto(String nombre, float precio, int cantidad) throws SQLException {
    PreparedStatement pps = cn.prepareStatement(
        "INSERT INTO tablaproducto (Nombre_Producto, Precio_Unitario, Cantidad) VALUES (?, ?, ?)"
    );
    pps.setString(1, nombre);
    pps.setFloat(2, precio);
    pps.setInt(3, cantidad);
    pps.executeUpdate();
}
private boolean productoExiste(String nombre) throws SQLException {
    String query = "SELECT COUNT(*) FROM tablaproducto WHERE Nombre_Producto = ?";
    try (PreparedStatement pst = cn.prepareStatement(query)) {
        pst.setString(1, nombre);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }
    return false;
}

public void manejarInsercionProducto(javax.swing.JTextField JTFNProducto, javax.swing.JTextField JTFPPorUnidad, javax.swing.JTextField JTFCantidad) {
    String nombreProducto = capitalizarNombre(JTFNProducto.getText().trim());
    String precioProducto = JTFPPorUnidad.getText().trim();
    String cantidadProducto = JTFCantidad.getText().trim();
    
    if (nombreProducto.isEmpty() || precioProducto.isEmpty() || cantidadProducto.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        if (productoExiste(nombreProducto)) {
            JOptionPane.showMessageDialog(null, "El producto ya existe. No se puede agregar otro con el mismo nombre.", "Producto Existente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        float precio = Float.parseFloat(precioProducto);
        int cantidad = Integer.parseInt(cantidadProducto);
        
        insertarProducto(nombreProducto, precio, cantidad);
        JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para el precio y la cantidad.", "Error de formato", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaProductos.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
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

//Para  insertar en el netbeans
    public void cargarDatos2() {
        try {
            modelo2.setRowCount(0); 

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tablaproducto");
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("codigoproducto");
                fila[1] = rs.getString("Nombre_Producto");
                fila[2] = rs.getFloat("Precio_Unitario");
                fila[3] = rs.getInt("Cantidad");
                modelo2.addRow(fila);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccionesTablaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }  
    
    public void manejarModificacionProducto(JTable table, javax.swing.JTextField jFNombreEditar, javax.swing.JTextField jFPrecioEditar, javax.swing.JTextField jFCantidadEditar, javax.swing.JPanel panelEditar) {
    
    int filaSeleccionada = table.getSelectedRow();
    
    if (filaSeleccionada >= 0) {
        panelEditar.setVisible(true);
        String nombreProducto = (String) table.getValueAt(filaSeleccionada, 1);
        float precioProducto = Float.parseFloat(table.getValueAt(filaSeleccionada, 2).toString());
        int cantidadProducto = Integer.parseInt(table.getValueAt(filaSeleccionada, 3).toString());
        
        jFNombreEditar.setText(nombreProducto);
        jFPrecioEditar.setText(String.valueOf(precioProducto));
        jFCantidadEditar.setText(String.valueOf(cantidadProducto));
    } else {
         panelEditar.setVisible(false);
        JOptionPane.showMessageDialog(null, "Por favor, seleccione un producto para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
    
    
     public void manejarActualizacionProducto(int filaSeleccionada, String nuevoNombre, String nuevoPrecio, String nuevaCantidad, JTable table, javax.swing.JPanel panelEditar) {
        
        if (filaSeleccionada >= 0) {
                    panelEditar.setVisible(true);

            String nombreProducto = (String) table.getValueAt(filaSeleccionada, 1);
            float precioProducto = Float.parseFloat(table.getValueAt(filaSeleccionada, 2).toString());
            int cantidadProducto = Integer.parseInt(table.getValueAt(filaSeleccionada, 3).toString());
            
            
            
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de actualizar el producto '" 
                    + nombreProducto + "'?", "Confirmar actualización", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    int idProducto = Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());
                    float nuevoPrecioFloat = Float.parseFloat(nuevoPrecio);
                    int nuevaCantidadInt = Integer.parseInt(nuevaCantidad);
                    
                    actualizarProducto(idProducto, nuevoNombre, nuevoPrecioFloat, nuevaCantidadInt);
                    
                    JOptionPane.showMessageDialog(null, "Producto '" + nombreProducto + "' actualizado correctamente.");
                    panelEditar.setVisible(false);
                    cargarDatos2(); // Actualizar la tabla después de la modificación
                    
                } catch (SQLException ex) {
                    Logger.getLogger(AccionesTablaProductos.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
     
     public static void validarNumerosYDecimal(KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == '.')) {
        evt.consume();
        JOptionPane.showMessageDialog(null, "Ingrese solo números y el caracter '.', por favor", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
     public static void validarNumeros(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE )) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Ingrese solo números por favor", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
}

