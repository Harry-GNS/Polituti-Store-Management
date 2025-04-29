package Vista;


import AccionesTablas.AccionesTablaClientes;
import AccionesTablas.AccionesTablaFacturas;
import AccionesTablas.AccionesTablaProductos;    
import AccionesTablas.GenerarPDF;

import BaseDeDatos.ConexionBD;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Desktop;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPRow;

import java.sql.ResultSet;
//import java.awt.Image;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;



public class JFDatosTablaFactura extends javax.swing.JFrame {
    private JFDatosTablaClientes ventanaClientes;
     private String clienteSeleccionado;
    private String productoSeleccionado;
    private AccionesTablaFacturas accionesFacturas;
    ConexionBD con = new ConexionBD();
     private String nombreCliente;
    private String cedulaCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String fechaActual = "";
    private String nombreArchivoPDFVenta = "";
     private int codigoFactura = 0;
    String usuario = "root"; 
    String contrasena = "grupo4hlm"; 
    Connection cn = con.conexion(usuario, contrasena);  
    DefaultTableModel modeloCliente;
    DefaultTableModel modeloProducto;
    DefaultTableModel modeloDetalleFactura;
    AccionesTablaClientes accionesTablaClientes;
    AccionesTablaProductos accionesTablaProductos;
    AccionesTablaFacturas accionesTablaFacturas;
    private TableRowSorter trsfiltro, trsfiltro1, trsfiltro2, trsfiltro3, trsfiltro4, trsfiltro5, trsfiltroclnombre, trsfiltroclcodigo, trsfiltroclcedula;
    String filtro, filtro1, filtro2, filtro3, filtro4, filtro5, filtroclnombre, filtroclcodigo, filtroclcedula;      
    
    
    public JFDatosTablaFactura() {
       initComponents();
        ventanaClientes = new JFDatosTablaClientes();
       this.setLocationRelativeTo(this);
        setIconImage(getIconImage());
        this.jPanelEscogerOtroCliente.setVisible(false);
        this.jPanelSalir9.setVisible(false);

        // Configuración de Acciones y Tablas
        accionesFacturas = new AccionesTablaFacturas(cn, modeloDetalleFactura);
        AccionesTablaFacturas accionesFacturas = new AccionesTablaFacturas(JTDetalleFactura, JTableCliente);

        // Modelo de Cliente
        modeloCliente = new DefaultTableModel();
        modeloCliente.addColumn("Codigo");
        modeloCliente.addColumn("Nombre");
        modeloCliente.addColumn("Cédula");
        modeloCliente.addColumn("Telefono");
        JTableCliente.setModel(modeloCliente);
        accionesTablaClientes = new AccionesTablaClientes(cn, modeloCliente);
        accionesTablaClientes.cargarDatostablafactura();
        
        // Modelo de Producto
        modeloProducto = new DefaultTableModel();
        modeloProducto.addColumn("Codigo");
        modeloProducto.addColumn("Nombre");
        modeloProducto.addColumn("P/U");
        modeloProducto.addColumn("Cantidad");
        JTableProducto.setModel(modeloProducto);
        accionesTablaProductos = new AccionesTablaProductos(cn, modeloProducto);
        accionesTablaProductos.cargarDatos2();
       
        // Modelo de Detalle Factura
        modeloDetalleFactura = new DefaultTableModel();
        modeloDetalleFactura.addColumn("C.Detalle");
        modeloDetalleFactura.addColumn("C.Factura");
        modeloDetalleFactura.addColumn("C.Producto");
        modeloDetalleFactura.addColumn("Cantidad");
        modeloDetalleFactura.addColumn("Total");
        modeloDetalleFactura.addColumn("idcliente");
        JTDetalleFactura.setModel(modeloDetalleFactura);
            
         
        JTDetalleFactura.getColumnModel().getColumn(5).setMinWidth(0);
        JTDetalleFactura.getColumnModel().getColumn(5).setMaxWidth(0);
        JTDetalleFactura.getColumnModel().getColumn(5).setWidth(0);
        JTDetalleFactura.getColumnModel().getColumn(5).setPreferredWidth(0);
        JTDetalleFactura.getColumnModel().getColumn(5).setResizable(false);
          accionesTablaClientes = new AccionesTablaClientes(cn, modeloCliente);
        accionesTablaProductos = new AccionesTablaProductos(cn, modeloProducto);
        accionesTablaFacturas = new AccionesTablaFacturas(cn, modeloDetalleFactura);
        
        // Cargar datos
        accionesTablaClientes.cargarDatostablafactura();
        accionesTablaProductos.cargarDatos2();
        accionesTablaFacturas.cargarDatosFacturas();
            JTDetalleFactura.setVisible(false);
        
        trsfiltro = new TableRowSorter<>(JTableCliente.getModel());
        trsfiltro1 = new TableRowSorter<>(JTableProducto.getModel());
        trsfiltro2 = new TableRowSorter<>(JTDetalleFactura.getModel());
    
    }
    
      public javax.swing.JTable getJTableCliente() {
        return JTableCliente;
    }
    
    public javax.swing.JTable getJTableProducto() {
        return JTableProducto;
    }
    
    public javax.swing.JTable getJTDetalleFactura() {
        return JTDetalleFactura;
    }
    
    private void actualizarTablaClientes() {
        accionesTablaClientes.cargarDatos();
    }
    
    private void actualizarTablaProductos() {
        accionesTablaProductos.cargarDatos2();
    }
    
    private void actualizarTablaDetalleFactura() {
        accionesTablaFacturas.cargarDatosFacturas();
    } 
    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Backgroun = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableCliente = new javax.swing.JTable();
        JCbOpcionesBusquedaCliente = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        JTFBuscarCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanelEscogerCliente = new javax.swing.JPanel();
        JLEscogerCliente = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        JTFNombreDatoSacado = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        JTFCedulaDatoSacado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        JTFTelefonoDatoSacado = new javax.swing.JTextField();
        jPanelEscogerOtroCliente = new javax.swing.JPanel();
        jLabelEscogerOtroCliente = new javax.swing.JLabel();
        jPanelEscogerCliente2 = new javax.swing.JPanel();
        JLEscogerCliente2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTableProducto = new javax.swing.JTable();
        JCOpcionesBusquedaProducto = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        JTFBuscarProducto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanelEscogerCliente3 = new javax.swing.JPanel();
        JLEscogerProducto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        JTFNombreProductoDatoSacado = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        JTFPrecioDatoSacado = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        JTFCantidadDatoSacado = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        JTFCantidadaComprar = new javax.swing.JTextField();
        jPanelSalir9 = new javax.swing.JPanel();
        JLabelComprar = new javax.swing.JLabel();
        jPanelNuevaFactura = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabelFactura = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaEncabezado = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        JTDetalleFactura = new javax.swing.JTable();
        jPanelRegresarMenu = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanelExportarPDF = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        JPSalirProgramita = new javax.swing.JPanel();
        JLSalir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FACTURAS");

        Backgroun.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 42, 92));

        JTableCliente.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        JTableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Cedula", "Telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(JTableCliente);

        JCbOpcionesBusquedaCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Nombre", "Por Cedula" }));
        JCbOpcionesBusquedaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCbOpcionesBusquedaClienteActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BUSCAR CLIENTE");

        JTFBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFBuscarClienteActionPerformed(evt);
            }
        });
        JTFBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFBuscarClienteKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ingrese los Datos");

        jPanelEscogerCliente.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEscogerCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelEscogerCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelEscogerClienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelEscogerClienteMouseEntered(evt);
            }
        });

        JLEscogerCliente.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        JLEscogerCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLEscogerCliente.setText("ESCOGER");
        JLEscogerCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JLEscogerCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLEscogerClienteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelEscogerClienteLayout = new javax.swing.GroupLayout(jPanelEscogerCliente);
        jPanelEscogerCliente.setLayout(jPanelEscogerClienteLayout);
        jPanelEscogerClienteLayout.setHorizontalGroup(
            jPanelEscogerClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLEscogerCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        jPanelEscogerClienteLayout.setVerticalGroup(
            jPanelEscogerClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLEscogerCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nombre");

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Datos del Cliente");

        JTFNombreDatoSacado.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Cédula");

        JTFCedulaDatoSacado.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Teléfono");

        JTFTelefonoDatoSacado.setEditable(false);

        jPanelEscogerOtroCliente.setBackground(new java.awt.Color(0, 42, 92));
        jPanelEscogerOtroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelEscogerOtroCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelEscogerOtroClienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelEscogerOtroClienteMouseEntered(evt);
            }
        });

        jLabelEscogerOtroCliente.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabelEscogerOtroCliente.setForeground(new java.awt.Color(243, 243, 243));
        jLabelEscogerOtroCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelEscogerOtroCliente.setText("Escoger Otro Cliente");
        jLabelEscogerOtroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelEscogerOtroCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEscogerOtroClienteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelEscogerOtroClienteLayout = new javax.swing.GroupLayout(jPanelEscogerOtroCliente);
        jPanelEscogerOtroCliente.setLayout(jPanelEscogerOtroClienteLayout);
        jPanelEscogerOtroClienteLayout.setHorizontalGroup(
            jPanelEscogerOtroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelEscogerOtroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        jPanelEscogerOtroClienteLayout.setVerticalGroup(
            jPanelEscogerOtroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEscogerOtroClienteLayout.createSequentialGroup()
                .addComponent(jLabelEscogerOtroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JTFNombreDatoSacado)
                    .addComponent(JTFCedulaDatoSacado)
                    .addComponent(JTFTelefonoDatoSacado)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jPanelEscogerOtroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFNombreDatoSacado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFCedulaDatoSacado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFTelefonoDatoSacado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanelEscogerOtroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelEscogerCliente2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEscogerCliente2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelEscogerCliente2.setPreferredSize(new java.awt.Dimension(120, 23));
        jPanelEscogerCliente2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelEscogerCliente2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelEscogerCliente2MouseEntered(evt);
            }
        });

        JLEscogerCliente2.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        JLEscogerCliente2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLEscogerCliente2.setText("AGREGAR CLIENTE");
        JLEscogerCliente2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JLEscogerCliente2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLEscogerCliente2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelEscogerCliente2Layout = new javax.swing.GroupLayout(jPanelEscogerCliente2);
        jPanelEscogerCliente2.setLayout(jPanelEscogerCliente2Layout);
        jPanelEscogerCliente2Layout.setHorizontalGroup(
            jPanelEscogerCliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLEscogerCliente2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );
        jPanelEscogerCliente2Layout.setVerticalGroup(
            jPanelEscogerCliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLEscogerCliente2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTFBuscarCliente)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JCbOpcionesBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanelEscogerCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelEscogerCliente2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JCbOpcionesBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanelEscogerCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanelEscogerCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 42, 92));

        JTableProducto.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        JTableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Precio Unitario", "Cantidad "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(JTableProducto);

        JCOpcionesBusquedaProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Nombre", "Por Precio", "Por Cantidad" }));
        JCOpcionesBusquedaProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCOpcionesBusquedaProductoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("BUSCAR PRODUCTO");

        JTFBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFBuscarProductoActionPerformed(evt);
            }
        });
        JTFBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFBuscarProductoKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ingrese los Datos");

        jPanelEscogerCliente3.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEscogerCliente3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelEscogerCliente3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelEscogerCliente3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelEscogerCliente3MouseEntered(evt);
            }
        });

        JLEscogerProducto.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        JLEscogerProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLEscogerProducto.setText("ESCOGER");
        JLEscogerProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JLEscogerProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLEscogerProductoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelEscogerCliente3Layout = new javax.swing.GroupLayout(jPanelEscogerCliente3);
        jPanelEscogerCliente3.setLayout(jPanelEscogerCliente3Layout);
        jPanelEscogerCliente3Layout.setHorizontalGroup(
            jPanelEscogerCliente3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLEscogerProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        jPanelEscogerCliente3Layout.setVerticalGroup(
            jPanelEscogerCliente3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLEscogerProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Nombre");

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Datos del Producto");

        JTFNombreProductoDatoSacado.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Precio Unitario");

        JTFPrecioDatoSacado.setEditable(false);

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Cantidad Disponible");

        JTFCantidadDatoSacado.setEditable(false);
        JTFCantidadDatoSacado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFCantidadDatoSacadoActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Ingrese Cantidad a Comprar");

        JTFCantidadaComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFCantidadaComprarActionPerformed(evt);
            }
        });
        JTFCantidadaComprar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFCantidadaComprarKeyTyped(evt);
            }
        });

        jPanelSalir9.setBackground(new java.awt.Color(0, 42, 92));
        jPanelSalir9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelSalir9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelSalir9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelSalir9MouseEntered(evt);
            }
        });

        JLabelComprar.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        JLabelComprar.setForeground(new java.awt.Color(243, 243, 243));
        JLabelComprar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabelComprar.setText("Comprar");
        JLabelComprar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JLabelComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLabelComprarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelSalir9Layout = new javax.swing.GroupLayout(jPanelSalir9);
        jPanelSalir9.setLayout(jPanelSalir9Layout);
        jPanelSalir9Layout.setHorizontalGroup(
            jPanelSalir9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLabelComprar, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
        );
        jPanelSalir9Layout.setVerticalGroup(
            jPanelSalir9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSalir9Layout.createSequentialGroup()
                .addComponent(JLabelComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanelNuevaFactura.setBackground(new java.awt.Color(0, 42, 92));
        jPanelNuevaFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelNuevaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelNuevaFacturaMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Nueva Factura");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelNuevaFacturaLayout = new javax.swing.GroupLayout(jPanelNuevaFactura);
        jPanelNuevaFactura.setLayout(jPanelNuevaFacturaLayout);
        jPanelNuevaFacturaLayout.setHorizontalGroup(
            jPanelNuevaFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNuevaFacturaLayout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanelNuevaFacturaLayout.setVerticalGroup(
            jPanelNuevaFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNuevaFacturaLayout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JTFNombreProductoDatoSacado)
                            .addComponent(JTFPrecioDatoSacado)
                            .addComponent(JTFCantidadDatoSacado)
                            .addComponent(JTFCantidadaComprar)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelNuevaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jPanelSalir9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFNombreProductoDatoSacado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFPrecioDatoSacado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFCantidadDatoSacado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFCantidadaComprar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelSalir9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelNuevaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTFBuscarProducto)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JCOpcionesBusquedaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jPanelEscogerCliente3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JCOpcionesBusquedaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTFBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelEscogerCliente3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 42, 92));

        jLabelFactura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabelFactura.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFactura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFactura.setText("DATOS DE LA FACTURA");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFactura)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jTextAreaEncabezado.setEditable(false);
        jTextAreaEncabezado.setColumns(20);
        jTextAreaEncabezado.setRows(5);
        jTextAreaEncabezado.setText("\n");
        jScrollPane3.setViewportView(jTextAreaEncabezado);

        JTDetalleFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "idFactura", "idProducto", "Cantidad", "Total"
            }
        ));
        jScrollPane4.setViewportView(JTDetalleFactura);

        jPanelRegresarMenu.setBackground(new java.awt.Color(0, 42, 92));
        jPanelRegresarMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelRegresarMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelRegresarMenuMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(243, 243, 243));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Regresar al Menu Principal");

        javax.swing.GroupLayout jPanelRegresarMenuLayout = new javax.swing.GroupLayout(jPanelRegresarMenu);
        jPanelRegresarMenu.setLayout(jPanelRegresarMenuLayout);
        jPanelRegresarMenuLayout.setHorizontalGroup(
            jPanelRegresarMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelRegresarMenuLayout.setVerticalGroup(
            jPanelRegresarMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanelExportarPDF.setBackground(new java.awt.Color(0, 42, 92));
        jPanelExportarPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelExportarPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelExportarPDFMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Exportar Factura");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelExportarPDFLayout = new javax.swing.GroupLayout(jPanelExportarPDF);
        jPanelExportarPDF.setLayout(jPanelExportarPDFLayout);
        jPanelExportarPDFLayout.setHorizontalGroup(
            jPanelExportarPDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
        jPanelExportarPDFLayout.setVerticalGroup(
            jPanelExportarPDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        JPSalirProgramita.setBackground(new java.awt.Color(0, 42, 92));
        JPSalirProgramita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JPSalirProgramita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPSalirProgramitaMouseClicked(evt);
            }
        });

        JLSalir.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        JLSalir.setForeground(new java.awt.Color(243, 243, 243));
        JLSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLSalir.setText("Salir");

        javax.swing.GroupLayout JPSalirProgramitaLayout = new javax.swing.GroupLayout(JPSalirProgramita);
        JPSalirProgramita.setLayout(JPSalirProgramitaLayout);
        JPSalirProgramitaLayout.setHorizontalGroup(
            JPSalirProgramitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPSalirProgramitaLayout.createSequentialGroup()
                .addComponent(JLSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JPSalirProgramitaLayout.setVerticalGroup(
            JPSalirProgramitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BackgrounLayout = new javax.swing.GroupLayout(Backgroun);
        Backgroun.setLayout(BackgrounLayout);
        BackgrounLayout.setHorizontalGroup(
            BackgrounLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgrounLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(BackgrounLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BackgrounLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(BackgrounLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 355, Short.MAX_VALUE))
                        .addGap(387, 387, 387))
                    .addGroup(BackgrounLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(BackgrounLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(JPSalirProgramita, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelRegresarMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelExportarPDF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        BackgrounLayout.setVerticalGroup(
            BackgrounLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BackgrounLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelExportarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelRegresarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(JPSalirProgramita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Backgroun, javax.swing.GroupLayout.PREFERRED_SIZE, 943, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Backgroun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JCbOpcionesBusquedaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCbOpcionesBusquedaClienteActionPerformed
    String opcionSeleccionada = (String) JCbOpcionesBusquedaCliente.getSelectedItem();

    switch (opcionSeleccionada) {
        case "Por Nombre":
            JTFBuscarCliente.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(final KeyEvent e) {
                    filtro1();
                }
            });
            break;

        case "Por Cedula":
            JTFBuscarCliente.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(final KeyEvent e) {
                    filtro();
                }
            });
            break;

        default:
            JOptionPane.showMessageDialog(this, "Opción de búsqueda no válida", "Error", JOptionPane.ERROR_MESSAGE);
            break;
    }
    }//GEN-LAST:event_JCbOpcionesBusquedaClienteActionPerformed

    private void JLEscogerClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLEscogerClienteMouseClicked
      int filaJTableCliente = JTableCliente.getSelectedRow();

    if (filaJTableCliente >= 0) {
        JTDetalleFactura.setVisible(true);
        String nombreCliente = JTableCliente.getValueAt(filaJTableCliente, 1).toString();
        JTFNombreDatoSacado.setText(nombreCliente);
        String cedulaCliente = JTableCliente.getValueAt(filaJTableCliente, 2).toString();
        JTFCedulaDatoSacado.setText(cedulaCliente);
        String telefonoCliente = JTableCliente.getValueAt(filaJTableCliente, 3).toString();
        JTFTelefonoDatoSacado.setText(telefonoCliente);

        int codigocliente = Integer.parseInt(JTableCliente.getValueAt(filaJTableCliente, 0).toString());

        // Aplicar filtro para productos del cliente
        aplicarFiltroCliente(codigocliente);

        this.jPanelEscogerOtroCliente.setVisible(true);

        String fecha = new java.sql.Date(System.currentTimeMillis()).toString();

        // Obtener el último código de factura para el cliente seleccionado
        int codigofactura = obtenerCodigoFactura(codigocliente);

        // Actualizar el encabezado con la información del cliente y la última factura
        actualizarTextoEncabezado(nombreCliente, cedulaCliente, fecha, codigofactura);

        // Limpiar el modelo de JTDetalleFactura y agregar solo los detalles de la última factura
        DefaultTableModel modeloDetalleFactura = (DefaultTableModel) JTDetalleFactura.getModel();
        modeloDetalleFactura.setRowCount(0); // Limpiar el modelo

        try (PreparedStatement ppsObtenerDetalles = cn.prepareStatement(
                "SELECT * FROM tabladetallesf WHERE codigofactura = ?")) {
            ppsObtenerDetalles.setInt(1, codigofactura);
            ResultSet rsDetalles = ppsObtenerDetalles.executeQuery();

            while (rsDetalles.next()) {
                Object[] fila = new Object[4];
                fila[0] = rsDetalles.getInt("codigofactura");
                fila[1] = rsDetalles.getInt("codigoproducto");
                fila[2] = rsDetalles.getInt("cantidad");
                fila[3] = rsDetalles.getFloat("total");
               
                modeloDetalleFactura.addRow(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione un cliente", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}   
private void actualizarTextoEncabezado(String nombreCliente, String cedulaCliente, String fecha, int codigofactura) {
    StringBuilder encabezado = new StringBuilder();
    encabezado.append("         POLITUTI\n");
    encabezado.append("Nombre del Cliente: ").append(nombreCliente).append("\n");
    encabezado.append("Numero de Cedula: ").append(cedulaCliente).append("\n");
    encabezado.append("Fecha de Emision: ").append(fecha).append("\n");
    encabezado.append("Factura N: ").append(codigofactura);
    jTextAreaEncabezado.setText(encabezado.toString());
}

// Método para obtener el código de la factura basado en el cliente
private int obtenerCodigoFactura(int codigocliente) {
    int codigofactura = 0;
    try (PreparedStatement ppsObtenerUltimaFactura = cn.prepareStatement(
            "SELECT MAX(codigofactura) FROM tablaencabezadof WHERE codigocliente = ?")) {
        ppsObtenerUltimaFactura.setInt(1, codigocliente);
        ResultSet rs = ppsObtenerUltimaFactura.executeQuery();

        if (rs.next()) {
            codigofactura = rs.getInt(1);
        }
        rs.close();
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
    }
    return codigofactura;
    }//GEN-LAST:event_JLEscogerClienteMouseClicked

    private void jPanelEscogerClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerClienteMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jPanelEscogerClienteMouseClicked

    private void jPanelEscogerClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerClienteMouseEntered
      
    }//GEN-LAST:event_jPanelEscogerClienteMouseEntered

    private void JLEscogerCliente2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLEscogerCliente2MouseClicked
        this.setVisible(false);
        new JFDatosTablaClientes().setVisible(true);
    }//GEN-LAST:event_JLEscogerCliente2MouseClicked

    private void jPanelEscogerCliente2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerCliente2MouseClicked
        
    }//GEN-LAST:event_jPanelEscogerCliente2MouseClicked

    private void jPanelEscogerCliente2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerCliente2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelEscogerCliente2MouseEntered

    private void jLabelEscogerOtroClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEscogerOtroClienteMouseClicked
    this.JTFBuscarCliente.setText("");
    this.JTFCedulaDatoSacado.setText("");
    this.JTFNombreDatoSacado.setText("");
    this.JTFTelefonoDatoSacado.setText("");
    this.jTextAreaEncabezado.setText(filtro);
    JTDetalleFactura.setVisible(false);
    JTableCliente.clearSelection();
    trsfiltro.setRowFilter(null);
    trsfiltro1.setRowFilter(null);
    jPanelNuevaFactura.setVisible(true);
    jPanelSalir9.setVisible(false);
    }//GEN-LAST:event_jLabelEscogerOtroClienteMouseClicked

    private void jPanelEscogerOtroClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerOtroClienteMouseClicked
      
    }//GEN-LAST:event_jPanelEscogerOtroClienteMouseClicked

    private void jPanelEscogerOtroClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerOtroClienteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelEscogerOtroClienteMouseEntered

    private void JCOpcionesBusquedaProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCOpcionesBusquedaProductoActionPerformed
          String opcionSeleccionada = (String) JCOpcionesBusquedaProducto.getSelectedItem();
    
    switch (opcionSeleccionada) {
        case "Por Nombre":
            JTFBuscarProducto.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(final KeyEvent e) {
                    filtro3();
                }
            });
            break;
            
        case "Por Precio":
            JTFBuscarProducto.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(final KeyEvent e) {
                    filtro4();
                }
            });
            break;
            
        case "Por Cantidad":
            JTFBuscarProducto.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(final KeyEvent e) {
                    filtro5();
                }
            });
            break;
        
        default:
            JOptionPane.showMessageDialog(this, "Opción de búsqueda no válida", "Error", JOptionPane.ERROR_MESSAGE);
            break;
    }
    }//GEN-LAST:event_JCOpcionesBusquedaProductoActionPerformed

    private void JLEscogerProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLEscogerProductoMouseClicked
   int filaJTableProducto = JTableProducto.getSelectedRow();

    if (filaJTableProducto >= 0) {
        String nombreproducto = JTableProducto.getValueAt(filaJTableProducto, 1).toString();
        JTFNombreProductoDatoSacado.setText(nombreproducto);
        String precioproducto = JTableProducto.getValueAt(filaJTableProducto, 2).toString();
        JTFPrecioDatoSacado.setText(precioproducto);
        String cantidaddisponible = JTableProducto.getValueAt(filaJTableProducto, 3).toString();
        JTFCantidadDatoSacado.setText(cantidaddisponible);
        
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione un Producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_JLEscogerProductoMouseClicked

    private void jPanelEscogerCliente3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerCliente3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelEscogerCliente3MouseClicked

    private void jPanelEscogerCliente3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEscogerCliente3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelEscogerCliente3MouseEntered

    private void JLabelComprarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLabelComprarMouseClicked
  int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de realizar la compra?", "Confirmar compra", JOptionPane.YES_NO_OPTION);

    if (respuesta == JOptionPane.NO_OPTION) {
        return;
    }

    int filaJTableCliente = JTableCliente.getSelectedRow();
    int filaJTableProducto = JTableProducto.getSelectedRow();

    if (filaJTableCliente == -1 || filaJTableProducto == -1) {
        JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente y un producto.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int cantidadProductoActual = Integer.parseInt(JTableProducto.getValueAt(filaJTableProducto, 3).toString());
    float precioProducto = Float.parseFloat(JTableProducto.getValueAt(filaJTableProducto, 2).toString());
    int codigoCliente = Integer.parseInt(JTableCliente.getValueAt(filaJTableCliente, 0).toString());
    String cedulaCliente = JTableCliente.getValueAt(filaJTableCliente, 2).toString();
    String nombreCliente = JTableCliente.getValueAt(filaJTableCliente, 1).toString();

    int cantidadIngresada;
    try {
        cantidadIngresada = Integer.parseInt(JTFCantidadaComprar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (cantidadIngresada > cantidadProductoActual) {
        JOptionPane.showMessageDialog(null, "La cantidad ingresada es mayor que la disponible.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    float total = precioProducto * cantidadIngresada;
    int codigofactura = 0;
    String fecha = new java.sql.Date(System.currentTimeMillis()).toString();

    try (PreparedStatement ppsObtenerUltimaFactura = cn.prepareStatement(
            "SELECT MAX(codigofactura) FROM tablaencabezadof WHERE codigocliente = ?")) {
        ppsObtenerUltimaFactura.setInt(1, codigoCliente);
        ResultSet rs = ppsObtenerUltimaFactura.executeQuery();

        
        if (rs.next()) {
            codigofactura = rs.getInt(1);
        }
        rs.close();

        if (codigofactura == 0) {
            try (PreparedStatement ppsInsertarEncabezado = cn.prepareStatement(
                    "INSERT INTO tablaencabezadof (codigocliente, fecha, pagoTotal) VALUES (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                ppsInsertarEncabezado.setInt(1, codigoCliente);
                ppsInsertarEncabezado.setString(2, fecha);
                ppsInsertarEncabezado.setFloat(3, total);
                ppsInsertarEncabezado.executeUpdate();

                ResultSet rsInsertar = ppsInsertarEncabezado.getGeneratedKeys();
                if (rsInsertar.next()) {
                    codigofactura = rsInsertar.getInt(1);
                }
                rsInsertar.close();
            }
        } else {
            try (PreparedStatement ppsActualizarEncabezado = cn.prepareStatement(
                    "UPDATE tablaencabezadof SET fecha = ?, pagoTotal = ? WHERE codigofactura = ? AND codigocliente = ?")) {
                ppsActualizarEncabezado.setString(1, fecha);
                ppsActualizarEncabezado.setFloat(2, total);
                ppsActualizarEncabezado.setInt(3, codigofactura);
                ppsActualizarEncabezado.setInt(4, codigoCliente);
                ppsActualizarEncabezado.executeUpdate();
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    int codigoproducto = Integer.parseInt(JTableProducto.getValueAt(filaJTableProducto, 0).toString());
    try (PreparedStatement ppsDetalles = cn.prepareStatement(
            "INSERT INTO tabladetallesf (codigofactura, codigocliente, codigoproducto, cantidad, total) VALUES (?, ?, ?, ?, ?)")) {
        ppsDetalles.setInt(1, codigofactura);
        ppsDetalles.setInt(2, codigoCliente);
        ppsDetalles.setInt(3, codigoproducto);
        ppsDetalles.setInt(4, cantidadIngresada);
        ppsDetalles.setFloat(5, total);
        ppsDetalles.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    int nuevaCantidad = cantidadProductoActual - cantidadIngresada;
    try (PreparedStatement ppsActualizarProducto = cn.prepareStatement(
            "UPDATE tablaproducto SET cantidad = ? WHERE codigoproducto = ?")) {
        ppsActualizarProducto.setInt(1, nuevaCantidad);
        ppsActualizarProducto.setInt(2, codigoproducto);
        ppsActualizarProducto.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    DefaultTableModel modeloProducto = (DefaultTableModel) JTableProducto.getModel();
    modeloProducto.setValueAt(nuevaCantidad, filaJTableProducto, 3);

    DefaultTableModel modeloDetalleFactura = (DefaultTableModel) JTDetalleFactura.getModel();
    modeloDetalleFactura.setRowCount(0); // Limpiar el modelo

    // Mostrar solo las filas con el codigofactura más reciente
    try (PreparedStatement ppsObtenerDetalles = cn.prepareStatement(
        "SELECT MAX(codigofactura) FROM tabladetallesf WHERE codigofactura = ?")) {
        ppsObtenerDetalles.setInt(1, codigofactura);
        ResultSet rsDetalles = ppsObtenerDetalles.executeQuery();
//   "SELECT MAX(codigofactura) FROM tablaencabezadof WHERE codigocliente = ?")) {
//        ppsObtenerUltimaFactura.setInt(1, codigoCliente);
        while (rsDetalles.next()) {
            Object[] fila = new Object[5];
            fila[0] = rsDetalles.getInt("codigodetalle");
            fila[1] = rsDetalles.getInt("codigofactura");
            fila[2] = rsDetalles.getInt("codigoproducto");
            fila[3] = rsDetalles.getInt("cantidad");
            fila[4] = rsDetalles.getFloat("total");
            modeloDetalleFactura.addRow(fila);
        }
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    actualizarTextoEncabezado(nombreCliente, cedulaCliente, fecha, codigofactura);

    JOptionPane.showMessageDialog(null, "Factura y detalles insertados correctamente.");
    accionesTablaFacturas.cargarDatosFacturas();
    this.JTFBuscarProducto.setText("");
    this.JTFNombreProductoDatoSacado.setText("");
    this.JTFPrecioDatoSacado.setText("");
    this.JTFCantidadDatoSacado.setText("");
    this.JTFCantidadaComprar.setText("");
    JTableProducto.clearSelection();
    trsfiltro3.setRowFilter(null);
    trsfiltro4.setRowFilter(null);
    trsfiltro5.setRowFilter(null);
    }//GEN-LAST:event_JLabelComprarMouseClicked

    private void jPanelSalir9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSalir9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelSalir9MouseClicked

    private void jPanelSalir9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSalir9MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelSalir9MouseEntered

    private void jPanelRegresarMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelRegresarMenuMouseClicked
    new JFMenuOpciones().setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_jPanelRegresarMenuMouseClicked

    private void jPanelExportarPDFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelExportarPDFMouseClicked
    this.setVisible(false);
    new JFDatosTablaFactura().setVisible(true);
    }//GEN-LAST:event_jPanelExportarPDFMouseClicked

    private void JPSalirProgramitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPSalirProgramitaMouseClicked
         System.exit(0);
    }//GEN-LAST:event_JPSalirProgramitaMouseClicked

    private void JTFBuscarClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFBuscarClienteKeyTyped
    String opcionSeleccionada = (String) JCbOpcionesBusquedaCliente.getSelectedItem();

    switch (opcionSeleccionada) {
        case "Por Nombre":
            trsfiltro1 = new TableRowSorter<>(JTableCliente.getModel());
            JTableCliente.setRowSorter(trsfiltro1);
            AccionesTablaClientes.validarSoloLetras(evt);
            filtro1();
            break;
            
        case "Por Cedula":
            trsfiltro = new TableRowSorter<>(JTableCliente.getModel());
            JTableCliente.setRowSorter(trsfiltro);
            AccionesTablaClientes.validarNumeros(evt);
            if (JTFBuscarCliente.getText().length() >= 10) {
                evt.consume();
                Toolkit.getDefaultToolkit().beep();
            }
            filtro();
            break;
        
        default:
            JOptionPane.showMessageDialog(this, "Opción de búsqueda no válida", "Error", JOptionPane.ERROR_MESSAGE);
            break;
    }   
    }//GEN-LAST:event_JTFBuscarClienteKeyTyped

    private void JTFBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFBuscarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFBuscarProductoActionPerformed
    private void actualizarTablaFacturas(int codigocliente) {
       modeloDetalleFactura.setRowCount(0);
    
    String query = "SELECT * FROM tabladetallesf WHERE codigocliente = ?";
    
    try (PreparedStatement pst = cn.prepareStatement(query)) {
        pst.setInt(1, codigocliente);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            Object[] fila = new Object[modeloDetalleFactura.getColumnCount()];
            for (int i = 0; i < fila.length; i++) {
                fila[i] = rs.getObject(i + 1);
            }
            modeloDetalleFactura.addRow(fila);
        }
    } catch (SQLException e) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, e);
        JOptionPane.showMessageDialog(this, "Error al cargar los datos de las facturas.");
    }
    }
    
private void aplicarFiltroCliente(int codigocliente) {
    DefaultTableModel modeloDetalleFactura = (DefaultTableModel) JTDetalleFactura.getModel();
    TableRowSorter<TableModel> sorter = new TableRowSorter<>(modeloDetalleFactura);
    JTDetalleFactura.setRowSorter(sorter);

    RowFilter<TableModel, Object> rf = new RowFilter<TableModel, Object>() {
        @Override
        public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
            String codigo = entry.getStringValue(5);
            return codigo.equals(String.valueOf(codigocliente));
        }
    };

    sorter.setRowFilter(rf);
}

    private void JTFBuscarProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFBuscarProductoKeyTyped
   String opcionSeleccionada = (String) JCOpcionesBusquedaProducto.getSelectedItem();
    
    switch (opcionSeleccionada) {
        case "Por Nombre":
            trsfiltro3 = new TableRowSorter<>(JTableProducto.getModel());
            AccionesTablaClientes.validarSoloLetras(evt);
            JTableProducto.setRowSorter(trsfiltro3);
            filtro3();
            break;
            
        case "Por Precio":
            trsfiltro4 = new TableRowSorter<>(JTableProducto.getModel());
            AccionesTablaClientes.validarNumeros(evt);
            JTableProducto.setRowSorter(trsfiltro4);
            filtro4();
            break;
            
        case "Por Cantidad":
            trsfiltro5 = new TableRowSorter<>(JTableProducto.getModel());
            AccionesTablaClientes.validarNumeros(evt);
            JTableProducto.setRowSorter(trsfiltro5);
            filtro5();
            break;
            
        default:
            JOptionPane.showMessageDialog(this, "Opción de búsqueda no válida", "Error", JOptionPane.ERROR_MESSAGE);
            break;
    }   
    }//GEN-LAST:event_JTFBuscarProductoKeyTyped

    private void JTFCantidadDatoSacadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFCantidadDatoSacadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFCantidadDatoSacadoActionPerformed


  
    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
    // Obtén el código del cliente seleccionado
        int codigoCliente = obtenerCodigoClienteSeleccionado();
        // Genera el PDF de la factura
        generarFacturaPDF(codigoCliente);
  this.JTFBuscarCliente.setText("");
    this.JTFCedulaDatoSacado.setText("");
    this.JTFNombreDatoSacado.setText("");
    this.JTFTelefonoDatoSacado.setText("");
    this.jTextAreaEncabezado.setText(filtro);
    JTDetalleFactura.setVisible(false);
    JTableCliente.clearSelection();
    trsfiltro.setRowFilter(null);
    trsfiltro1.setRowFilter(null);
    jPanelSalir9.setVisible(false);
    jPanelNuevaFactura.setVisible(true);
    
    }//GEN-LAST:event_jLabel15MouseClicked

    private void JTFBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFBuscarClienteActionPerformed

    private void JTFCantidadaComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFCantidadaComprarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFCantidadaComprarActionPerformed

    private void JTFCantidadaComprarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFCantidadaComprarKeyTyped
                    AccionesTablaClientes.validarNumeros(evt);

    }//GEN-LAST:event_JTFCantidadaComprarKeyTyped

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
   int filaJTableCliente = JTableCliente.getSelectedRow();
    int filaJTableProducto = JTableProducto.getSelectedRow();

    if (filaJTableCliente == -1 || filaJTableProducto == -1) {
        JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente y un producto.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int cantidadProductoActual = Integer.parseInt(JTableProducto.getValueAt(filaJTableProducto, 3).toString());
    float precioProducto = Float.parseFloat(JTableProducto.getValueAt(filaJTableProducto, 2).toString());
    int codigoCliente = Integer.parseInt(JTableCliente.getValueAt(filaJTableCliente, 0).toString());
    String cedulaCliente = JTableCliente.getValueAt(filaJTableCliente, 2).toString();
    String nombreCliente = JTableCliente.getValueAt(filaJTableCliente, 1).toString();

    int cantidadIngresada;
    try {
        cantidadIngresada = Integer.parseInt(JTFCantidadaComprar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (cantidadIngresada > cantidadProductoActual) {
        JOptionPane.showMessageDialog(null, "La cantidad ingresada es mayor que la disponible.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    float total = precioProducto * cantidadIngresada;
    int nuevoCodigoFactura = 0;
    String fecha = new java.sql.Date(System.currentTimeMillis()).toString();

    try (PreparedStatement ppsObtenerUltimoCodigoFactura = cn.prepareStatement(
            "SELECT MAX(codigofactura) FROM tablaencabezadof")) {
        ResultSet rs = ppsObtenerUltimoCodigoFactura.executeQuery();

        if (rs.next()) {
            nuevoCodigoFactura = rs.getInt(1) + 1; // Nuevo código de factura es el siguiente en la secuencia
        }
        rs.close();

        try (PreparedStatement ppsInsertarEncabezado = cn.prepareStatement(
                "INSERT INTO tablaencabezadof (codigofactura, codigocliente, fecha, pagoTotal) VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ppsInsertarEncabezado.setInt(1, nuevoCodigoFactura);
            ppsInsertarEncabezado.setInt(2, codigoCliente);
            ppsInsertarEncabezado.setString(3, fecha);
            ppsInsertarEncabezado.setFloat(4, total);
            ppsInsertarEncabezado.executeUpdate();
        }
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    int codigoproducto = Integer.parseInt(JTableProducto.getValueAt(filaJTableProducto, 0).toString());
    try (PreparedStatement ppsDetalles = cn.prepareStatement(
            "INSERT INTO tabladetallesf (codigofactura, codigocliente, codigoproducto, cantidad, total) VALUES (?, ?, ?, ?, ?)")) {
        ppsDetalles.setInt(1, nuevoCodigoFactura);
        ppsDetalles.setInt(2, codigoCliente);
        ppsDetalles.setInt(3, codigoproducto);
        ppsDetalles.setInt(4, cantidadIngresada);
        ppsDetalles.setFloat(5, total);
        ppsDetalles.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    int nuevaCantidad = cantidadProductoActual - cantidadIngresada;
    try (PreparedStatement ppsActualizarProducto = cn.prepareStatement(
            "UPDATE tablaproducto SET cantidad = ? WHERE codigoproducto = ?")) {
        ppsActualizarProducto.setInt(1, nuevaCantidad);
        ppsActualizarProducto.setInt(2, codigoproducto);
        ppsActualizarProducto.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    DefaultTableModel modeloProducto = (DefaultTableModel) JTableProducto.getModel();
    modeloProducto.setValueAt(nuevaCantidad, filaJTableProducto, 3);

    // Actualizar el modelo de JTDetalleFactura para mostrar solo los productos de la última factura
    DefaultTableModel modeloDetalleFactura = (DefaultTableModel) JTDetalleFactura.getModel();
    modeloDetalleFactura.setRowCount(0); // Limpiar el modelo

    try (PreparedStatement ppsObtenerDetalles = cn.prepareStatement(
            "SELECT * FROM tabladetallesf WHERE codigofactura = ?")) {
        ppsObtenerDetalles.setInt(1, nuevoCodigoFactura);
        ResultSet rsDetalles = ppsObtenerDetalles.executeQuery();

        while (rsDetalles.next()) {
            Object[] fila = new Object[4];
            fila[0] = rsDetalles.getInt("codigofactura");
            fila[1] = rsDetalles.getInt("codigoproducto");
            fila[2] = rsDetalles.getInt("cantidad");
            fila[3] = rsDetalles.getFloat("total");
            modeloDetalleFactura.addRow(fila);
        }
    } catch (SQLException ex) {
        Logger.getLogger(JFDatosTablaFactura.class.getName()).log(Level.SEVERE, null, ex);
        return;
    }

    actualizarTextoEncabezado(nombreCliente, cedulaCliente, fecha, nuevoCodigoFactura);

    JOptionPane.showMessageDialog(null, "Factura y detalles insertados correctamente.");
    jPanelNuevaFactura.setVisible(false);
    jPanelSalir9.setVisible(true);
    accionesTablaFacturas.cargarDatosFacturas();
    this.JTFBuscarProducto.setText("");
    this.JTFNombreProductoDatoSacado.setText("");
    this.JTFPrecioDatoSacado.setText("");
    this.JTFCantidadDatoSacado.setText("");
    this.JTFCantidadaComprar.setText("");
    JTableProducto.clearSelection();
    trsfiltro3.setRowFilter(null);
    trsfiltro4.setRowFilter(null);
    trsfiltro5.setRowFilter(null);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jPanelNuevaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelNuevaFacturaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelNuevaFacturaMouseClicked

  private int obtenerCodigoClienteSeleccionado() {
         // Supón que tienes un JTable llamado 'JLEscogerCliente' que muestra los clientes
    int filaSeleccionada = JTableCliente.getSelectedRow();
    if (filaSeleccionada != -1) {
        // Obtener el valor como String
        String valor = (String) JTableCliente.getValueAt(filaSeleccionada, 0);
        try {
            // Convertir el String a Integer
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el código del cliente: " + e);
        }
    }
    return -1; 
    }

    // Método para obtener el código de la venta actual
    private int obtenerCodigoVenta() {
        // Supón que tienes un JTable llamado 'JTDetalleFactura' y usas la fila seleccionada para obtener el código de la venta
        int filaSeleccionada = JTDetalleFactura.getSelectedRow();
        if (filaSeleccionada != -1) {
            return (int) JTDetalleFactura.getValueAt(filaSeleccionada, 1); // Suponiendo que la primera columna es el código de la venta
        }
        return -1; // Valor predeterminado en caso de que no se seleccione ninguna venta
    }

    // Método para obtener datos del cliente
    public void DatosCliente(int codigocliente) {
        String usuario = "root";
        String contrasena = "grupo4hlm";

        cn = new ConexionBD().conexion(usuario, contrasena);
        String sql = "SELECT * FROM tablacliente WHERE codigocliente = ?";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, codigocliente);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombreCliente = rs.getString("Nombre_Cliente");
                cedulaCliente = rs.getString("N_cedula");
                telefonoCliente = rs.getString("N_telefono");
               
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos del cliente: " + e);
        }
    }

  

    // Método para obtener productos de la base de datos y agregarlos a la tabla del PDF
    private double agregarProductosPDF(int codigoVenta, PdfPTable tablaProducto) {
    String usuario = "root";
    String contrasena = "grupo4hlm";
    double totalPagar = 0; // Variable para almacenar el total
    
    cn = new ConexionBD().conexion(usuario, contrasena);
    String sql = "SELECT p.Nombre_Producto, d.Cantidad, p.Precio_Unitario, (d.Cantidad * p.Precio_Unitario) AS Total " +
                 "FROM tabladetallesf d " +
                 "JOIN tablaproducto p ON d.codigoproducto = p.codigoproducto " +
                 "WHERE d.codigofactura = ?";
    try (PreparedStatement pst = cn.prepareStatement(sql)) {
        pst.setInt(1, codigoVenta);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String cantidad = rs.getString("Cantidad");
            String descripcion = rs.getString("Nombre_Producto");
            String precioUnitario = rs.getString("Precio_Unitario");
            String precioTotalStr = String.format("%.2f", rs.getFloat("Total"));
            
            tablaProducto.addCell(cantidad);
            tablaProducto.addCell(descripcion);
            tablaProducto.addCell(precioUnitario);
            tablaProducto.addCell(precioTotalStr);

            // Sumar el total al totalPagar
            totalPagar += rs.getFloat("Total");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener productos: " + e);
    }
    
    return totalPagar; // Retornar el total calculado
}
    
    // Método para generar la factura de venta
public void generarFacturaPDF(int codigocliente) {
    try {
        // Obtener el código de la factura
        int codigofactura = obtenerCodigoFactura(codigocliente);

        // Obtener datos del cliente
        DatosCliente(codigocliente);

        // Cargar la fecha actual
        Date date = new Date();
        fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
        String fechaNueva = fechaActual.replace("/", "_");

        nombreArchivoPDFVenta = "Venta_" + nombreCliente + "_" + fechaNueva + ".pdf";

        // Crear la carpeta si no existe
        File directorio = new File("C:\\Users\\harry\\Desktop\\Facturas Generadas");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("No se pudo crear el directorio");
            }
        }

        // Crear el archivo dentro de la carpeta
        File file = new File(directorio, nombreArchivoPDFVenta);
        try (FileOutputStream archivo = new FileOutputStream(file)) {
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Image img = Image.getInstance("src/Imagenes/Polituti.png");
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            fecha.add(Chunk.NEWLINE); // agregar nueva línea
            fecha.add("Factura: " + codigofactura + "\nFecha: " + fechaActual + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0); // quitar el borde de la tabla
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            Encabezado.addCell(img);

            String ruc = "172931712812";
            String nombre = "POLITUTI";
            String telefono = "0983557252";
            String direccion = "Av.Botellita E22-345";
            String razon = "!Caldad en cada rincon del Pais!";

            Encabezado.addCell(""); // celda vacía
            Encabezado.addCell("RUC: " + ruc + "\nNOMBRE: " + nombre + "\nTELEFONO: " + telefono + "\nDIRECCION: " + direccion + "\n" + razon);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            // CUERPO
            Paragraph cliente = new Paragraph();
            cliente.add(Chunk.NEWLINE); // nueva línea
            cliente.add("Datos del cliente: " + "\n\n");
            doc.add(cliente);

            // DATOS DEL CLIENTE
            PdfPTable tablaCliente = new PdfPTable(3);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0); // quitar bordes
            float[] ColumnaCliente = new float[]{25f, 45f, 30f};
            tablaCliente.setWidths(ColumnaCliente);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliente1 = new PdfPCell(new Phrase("Cedula/RUC: ", negrita));
            PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre: ", negrita));
            PdfPCell cliente3 = new PdfPCell(new Phrase("Telefono: ", negrita));
            
            cliente1.setBorder(0);
            cliente2.setBorder(0);
            cliente3.setBorder(0);
            
            tablaCliente.addCell(cliente1);
            tablaCliente.addCell(cliente2);
            tablaCliente.addCell(cliente3);
            
            tablaCliente.addCell(cedulaCliente);
            tablaCliente.addCell(nombreCliente);
            tablaCliente.addCell(telefonoCliente);
            
            doc.add(tablaCliente);

            // ESPACIO EN BLANCO
            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            espacio.add("");
            doc.add(espacio);

            // TÍTULO PARA PRODUCTOS
            Paragraph productosTitulo = new Paragraph();
            productosTitulo.add("Detalle de Productos: "+ "\n\n");
            productosTitulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(productosTitulo);

            // TABLA DE PRODUCTOS
            PdfPTable tablaProducto = new PdfPTable(4);
            tablaProducto.setWidthPercentage(100);
            tablaProducto.setHorizontalAlignment(Element.ALIGN_LEFT);
            float[] ColumnasProductos = new float[]{15f, 50f, 20f, 15f}; // Aumentar el tamaño de la primera columna
            tablaProducto.setWidths(ColumnasProductos);
            
            // Configurar la primera fila
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            PdfPCell cantidadHeader = new PdfPCell(new Phrase("Cantidad", headerFont));
            PdfPCell descripcionHeader = new PdfPCell(new Phrase("Descripción", headerFont));
            PdfPCell precioHeader = new PdfPCell(new Phrase("Precio Unitario", headerFont));
            PdfPCell totalHeader = new PdfPCell(new Phrase("Total", headerFont));
            
            cantidadHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            descripcionHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            precioHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            totalHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            
            // Agregar bordes a la primera fila
            cantidadHeader.setBorder(Rectangle.BOX);
            descripcionHeader.setBorder(Rectangle.BOX);
            precioHeader.setBorder(Rectangle.BOX);
            totalHeader.setBorder(Rectangle.BOX);
            
            tablaProducto.addCell(cantidadHeader);
            tablaProducto.addCell(descripcionHeader);
            tablaProducto.addCell(precioHeader);
            tablaProducto.addCell(totalHeader);
            
            // Agregar productos al PDF y obtener el total
            double totalPagar = agregarProductosPDF(codigofactura, tablaProducto);

            // Eliminar bordes de las celdas en filas siguientes
            for (int i = 1; i < tablaProducto.getRows().size(); i++) {
                PdfPRow row = tablaProducto.getRows().get(i);
                for (PdfPCell cell : row.getCells()) {
                    cell.setBorder(Rectangle.NO_BORDER);
                }
            }

            doc.add(tablaProducto);

            // Agregar total a pagar
            // Agregar total a pagar
    Paragraph totalPagarParagraph = new Paragraph();
    totalPagarParagraph.add("Total a pagar: " + String.format("%.2f", totalPagar));
    totalPagarParagraph.setAlignment(Element.ALIGN_RIGHT); // Alinear a la derecha
    doc.add(totalPagarParagraph);
    double iva = 0.12; // 12% IVA
    double totalConIVA = totalPagar * (1 + iva);
    // Agregar total a pagar con IVA
    Paragraph totalPagarConIVAParagraph = new Paragraph();
    totalPagarConIVAParagraph.add("Total a pagar con IVA (12%): " + String.format("%.2f", totalConIVA));
    totalPagarConIVAParagraph.setAlignment(Element.ALIGN_RIGHT); // Alinear a la derecha
    doc.add(totalPagarConIVAParagraph);

            doc.close();
            Desktop.getDesktop().open(file);
        }
    } catch (Exception e) {
        System.out.println("Error al generar el PDF: " + e);
    }
}

public void filtro() {
    filtro = JTFBuscarCliente.getText();
    // Agregar (?i) al inicio de la expresión regular para hacerla insensible al caso
    trsfiltro.setRowFilter(RowFilter.regexFilter("(?i)" + filtro, 2));
}

public void filtro1() {
    filtro1 = JTFBuscarCliente.getText();
    // Agregar (?i) al inicio de la expresión regular para hacerla insensible al caso
    trsfiltro1.setRowFilter(RowFilter.regexFilter("(?i)" + filtro1, 1));
}

public void filtro3() {
    filtro3 = JTFBuscarProducto.getText();
    // Agregar (?i) al inicio de la expresión regular para hacerla insensible al caso
    trsfiltro3.setRowFilter(RowFilter.regexFilter("(?i)" + filtro3, 1));
}

public void filtro4() {
    filtro4 = JTFBuscarProducto.getText();
    // Agregar (?i) al inicio de la expresión regular para hacerla insensible al caso
    trsfiltro4.setRowFilter(RowFilter.regexFilter("(?i)" + filtro4, 2));
}

public void filtro5() {
    filtro5 = JTFBuscarProducto.getText();
    // Agregar (?i) al inicio de la expresión regular para hacerla insensible al caso
    trsfiltro5.setRowFilter(RowFilter.regexFilter("(?i)" + filtro5, 3));
}


    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFDatosTablaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFDatosTablaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFDatosTablaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFDatosTablaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFDatosTablaFactura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Backgroun;
    private javax.swing.JComboBox<String> JCOpcionesBusquedaProducto;
    private javax.swing.JComboBox<String> JCbOpcionesBusquedaCliente;
    private javax.swing.JLabel JLEscogerCliente;
    private javax.swing.JLabel JLEscogerCliente2;
    private javax.swing.JLabel JLEscogerProducto;
    private javax.swing.JLabel JLSalir;
    private javax.swing.JLabel JLabelComprar;
    private javax.swing.JLabel JLabelComprar1;
    private javax.swing.JLabel JLabelComprar2;
    private javax.swing.JPanel JPSalirProgramita;
    private javax.swing.JTable JTDetalleFactura;
    private javax.swing.JTextField JTFBuscarCliente;
    private javax.swing.JTextField JTFBuscarProducto;
    private javax.swing.JTextField JTFCantidadDatoSacado;
    private javax.swing.JTextField JTFCantidadaComprar;
    private javax.swing.JTextField JTFCedulaDatoSacado;
    private javax.swing.JTextField JTFNombreDatoSacado;
    private javax.swing.JTextField JTFNombreProductoDatoSacado;
    private javax.swing.JTextField JTFPrecioDatoSacado;
    private javax.swing.JTextField JTFTelefonoDatoSacado;
    private javax.swing.JTable JTableCliente;
    public javax.swing.JTable JTableProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelEscogerOtroCliente;
    private javax.swing.JLabel jLabelFactura;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelEscogerCliente;
    private javax.swing.JPanel jPanelEscogerCliente2;
    private javax.swing.JPanel jPanelEscogerCliente3;
    private javax.swing.JPanel jPanelEscogerOtroCliente;
    private javax.swing.JPanel jPanelExportarPDF;
    private javax.swing.JPanel jPanelNuevaFactura;
    private javax.swing.JPanel jPanelRegresarMenu;
    private javax.swing.JPanel jPanelSalir10;
    private javax.swing.JPanel jPanelSalir11;
    private javax.swing.JPanel jPanelSalir9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextAreaEncabezado;
    // End of variables declaration//GEN-END:variables
}
