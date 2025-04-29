package Vista;

import AccionesTablas.AccionesTablaProductos;
import BaseDeDatos.ConexionBD;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;    //Establecer y manejar la comunicación con la base de datos
import javax.swing.table.DefaultTableModel;// proporciona una estructura básica para almacenar y gestionar datos de tablitas
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;


public class JFDatosTablaProductos extends javax.swing.JFrame {


ConexionBD con = new ConexionBD();
String usuario = "root"; 
String contrasena = "grupo4hlm"; 
      
    Connection cn = con.conexion(usuario, contrasena);    
    DefaultTableModel modelo;
    AccionesTablaProductos accionesTabla;
    private TableRowSorter trsfiltro,trsfiltro1, trsfiltro2,trsfiltro3, trsfiltro4,trsfiltro5 ;
    String filtro, filtro1, filtro2;
   
    public JFDatosTablaProductos() {
        initComponents();
        setIconImage(getIconImage());
            this.setLocationRelativeTo(this);
        modelo = new DefaultTableModel();//Declara un modelo de tabla por defecto para manejar los datos que se mostrarán en la tabla.
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio Unitario");
        modelo.addColumn("Cantidad");
        jTable1.setModel(modelo);
        accionesTabla = new AccionesTablaProductos(cn, modelo); 
        accionesTabla.ocultarComponentes(jTBuscarCodigo, jTBuscarPrecio, jTBuscarNombre, jLabel3, jLPrecio, jLNombre, panelEditar);

            

        accionesTabla.cargarDatos2();
    }
 
    private void actualizarTabla() {
        accionesTabla.cargarDatos2();
    }
    

@Override
    public Image getIconImage(){
    Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/Polituti.png"));
    return retValue;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        salir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        JTFNProducto = new javax.swing.JTextField();
        JTFPPorUnidad = new javax.swing.JTextField();
        JBInsertar = new javax.swing.JButton();
        Cantidad = new javax.swing.JLabel();
        JTFCantidad = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLNombre = new javax.swing.JLabel();
        jLPrecio = new javax.swing.JLabel();
        jTBuscarCodigo = new javax.swing.JTextField();
        jTBuscarNombre = new javax.swing.JTextField();
        jTBuscarPrecio = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jBBuscarNombre = new javax.swing.JButton();
        jBBuscarPrecio = new javax.swing.JButton();
        jBBuscarCodigo = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        panelEditar = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jFNombreEditar = new javax.swing.JTextField();
        jFPrecioEditar = new javax.swing.JTextField();
        jFCantidadEditar = new javax.swing.JTextField();
        jBActualizarDatos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jBRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PRODUCTOS");

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Insertar Datos Productos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Nombre del Producto");

        jLabel2.setText("Precio por Unidad");

        JTFNProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFNProductoActionPerformed(evt);
            }
        });
        JTFNProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFNProductoKeyTyped(evt);
            }
        });

        JTFPPorUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFPPorUnidadActionPerformed(evt);
            }
        });
        JTFPPorUnidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFPPorUnidadKeyTyped(evt);
            }
        });

        JBInsertar.setText("Insertar");
        JBInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBInsertarActionPerformed(evt);
            }
        });

        Cantidad.setText("Cantidad");

        JTFCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFCantidadActionPerformed(evt);
            }
        });
        JTFCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTFCantidadKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(Cantidad))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTFPPorUnidad, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(JTFNProducto)
                    .addComponent(JTFCantidad))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(JBInsertar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(JTFNProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(JTFPPorUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cantidad)
                    .addComponent(JTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(JBInsertar)
                .addContainerGap(260, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Insertar", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ingrese el dato por el que quiera buscar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel3.setText("Buscar Por Codigo");

        jLNombre.setText("Buscar Por Nombre");

        jLPrecio.setText("Buscar Por Precio");

        jTBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCodigoActionPerformed(evt);
            }
        });
        jTBuscarCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTBuscarCodigoKeyTyped(evt);
            }
        });

        jTBuscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarNombreActionPerformed(evt);
            }
        });
        jTBuscarNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTBuscarNombreKeyTyped(evt);
            }
        });

        jTBuscarPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTBuscarPrecioKeyTyped(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Opciones de Busqueda"));

        jBBuscarNombre.setText("Buscar Por Nombre");
        jBBuscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarNombreActionPerformed(evt);
            }
        });

        jBBuscarPrecio.setText("Buscar Por Precio");
        jBBuscarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarPrecioActionPerformed(evt);
            }
        });

        jBBuscarCodigo.setText("Buscar por Codigo");
        jBBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBBuscarNombre))
                    .addComponent(jBBuscarCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBBuscarPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBBuscarNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBBuscarCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jBBuscarPrecio)
                .addContainerGap())
        );

        jBEliminar.setText("Eliminar");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });

        jBModificar.setText("Modificar");
        jBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarActionPerformed(evt);
            }
        });

        panelEditar.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Edite los Datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel4.setText("Nombre del Producto");

        jLabel5.setText("Precio del Producto");

        jLabel6.setText("Cantidad del Producto");

        jFNombreEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFNombreEditarActionPerformed(evt);
            }
        });

        jFPrecioEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFPrecioEditarActionPerformed(evt);
            }
        });
        jFPrecioEditar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFPrecioEditarKeyTyped(evt);
            }
        });

        jFCantidadEditar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFCantidadEditarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelEditarLayout = new javax.swing.GroupLayout(panelEditar);
        panelEditar.setLayout(panelEditarLayout);
        panelEditarLayout.setHorizontalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditarLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFCantidadEditar))
                    .addGroup(panelEditarLayout.createSequentialGroup()
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFNombreEditar)
                            .addComponent(jFPrecioEditar))))
                .addContainerGap())
        );
        panelEditarLayout.setVerticalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jFNombreEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jFPrecioEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jFCantidadEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jBActualizarDatos.setText("Actualizar Datos");
        jBActualizarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActualizarDatosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTBuscarCodigo)
                            .addComponent(jTBuscarNombre)
                            .addComponent(jTBuscarPrecio)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLPrecio)
                                    .addComponent(jLabel3)
                                    .addComponent(jLNombre))
                                .addGap(0, 130, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jBEliminar)
                        .addGap(88, 88, 88)
                        .addComponent(jBModificar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jBActualizarDatos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTBuscarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLPrecio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTBuscarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBEliminar)
                    .addComponent(jBModificar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jBActualizarDatos)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Buscar", jPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Precio Unitario", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jBRegresar.setText("Regresar");
        jBRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(salir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBRegresar)
                        .addGap(49, 49, 49)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salir)
                    .addComponent(jBRegresar))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //Boton Salir (Sirve Para todo el JF)
    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
       System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
      int filaSeleccionada = jTable1.getSelectedRow();
      accionesTabla.eliminarProducto(filaSeleccionada, jTable1, modelo, cn);
    }//GEN-LAST:event_jBEliminarActionPerformed

//GEN-FIRST:event_jBBuscarCodigoActionPerformed
 private void jBBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {                                               
        this.jTBuscarCodigo.setVisible(true);
        this.jLabel3.setVisible(true);
        this.jTBuscarPrecio.setVisible(false);
        this.jTBuscarNombre.setVisible(false);
        this.jLPrecio.setVisible(false);
        this.jLNombre.setVisible(false);
        jTBuscarCodigo.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(final KeyEvent e){
                String cadena = jTBuscarCodigo.getText();
                jTBuscarCodigo.setText(cadena);
                repaint();
                filtro();
            }
        });
 } 
//GEN-LAST:event_jBBuscarCodigoActionPerformed
private void jBBuscarNombreActionPerformed(java.awt.event.ActionEvent evt) {                                               
        this.jTBuscarCodigo.setVisible(false);
        this.jLabel3.setVisible(false);
        this.jTBuscarPrecio.setVisible(false);
        this.jTBuscarNombre.setVisible(true);
        this.jLPrecio.setVisible(false);
        this.jLNombre.setVisible(true);
        jTBuscarNombre.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(final KeyEvent e){
                String cadena = jTBuscarNombre.getText();
                jTBuscarNombre.setText(cadena);
                repaint();
                filtro1();
            }
        });
    }

    private void jBBuscarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarPrecioActionPerformed
        this.jTBuscarCodigo.setVisible(false);
        this.jLabel3.setVisible(false);
        this.jTBuscarPrecio.setVisible(true);
        this.jTBuscarNombre.setVisible(false);
        this.jLPrecio.setVisible(true);
        this.jLNombre.setVisible(false);
        jTBuscarPrecio.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(final KeyEvent e){
                String cadena = jTBuscarPrecio.getText();
                jTBuscarPrecio.setText(cadena);
                repaint();
                filtro2();
            }
        });
    }//GEN-LAST:event_jBBuscarPrecioActionPerformed

//GEN-FIRST:event_jBBuscarNombreActionPerformed

//GEN-LAST:event_jBBuscarNombreActionPerformed

    private void jTBuscarPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPrecioKeyTyped
        trsfiltro2 = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro2);
               AccionesTablaProductos.validarNumerosYDecimal(evt);

    }//GEN-LAST:event_jTBuscarPrecioKeyTyped

    private void jTBuscarNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarNombreKeyTyped
        trsfiltro1 = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro1);
    }//GEN-LAST:event_jTBuscarNombreKeyTyped

    private void jTBuscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarNombreActionPerformed

    }//GEN-LAST:event_jTBuscarNombreActionPerformed

    private void jTBuscarCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCodigoKeyTyped
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);
        AccionesTablaProductos.validarNumeros(evt);


    }//GEN-LAST:event_jTBuscarCodigoKeyTyped

//GEN-FIRST:event_JBInsertarActionPerformed
private void JBInsertarActionPerformed(java.awt.event.ActionEvent evt) {                                           
    accionesTabla.manejarInsercionProducto(JTFNProducto, JTFPPorUnidad, JTFCantidad);
    this.JTFNProducto.setText("");
        this.JTFPPorUnidad.setText("");
        this.JTFCantidad.setText("");
    actualizarTabla();   
    }                                          
  
//GEN-LAST:event_JBInsertarActionPerformed

    private void JTFNProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFNProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFNProductoActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed
     this.panelEditar.setVisible(true);
    accionesTabla.manejarModificacionProducto(jTable1, jFNombreEditar, jFPrecioEditar, jFCantidadEditar, panelEditar);
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jTBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCodigoActionPerformed

    private void jFNombreEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFNombreEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFNombreEditarActionPerformed

    private void jBActualizarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActualizarDatosActionPerformed
    String nuevoNombre = jFNombreEditar.getText();
    String nuevoPrecio = jFPrecioEditar.getText();
    String nuevaCantidad = jFCantidadEditar.getText();
    
    int filaSeleccionada = jTable1.getSelectedRow();
    accionesTabla.manejarActualizacionProducto(filaSeleccionada, nuevoNombre, nuevoPrecio, nuevaCantidad, jTable1, panelEditar);
    }//GEN-LAST:event_jBActualizarDatosActionPerformed

    private void JTFPPorUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFPPorUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFPPorUnidadActionPerformed

    private void JTFPPorUnidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFPPorUnidadKeyTyped
       AccionesTablaProductos.validarNumerosYDecimal(evt);
    
    }//GEN-LAST:event_JTFPPorUnidadKeyTyped

    private void JTFCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFCantidadKeyTyped
             AccionesTablaProductos.validarNumeros(evt);
    }//GEN-LAST:event_JTFCantidadKeyTyped

    private void JTFCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFCantidadActionPerformed

    private void jFPrecioEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFPrecioEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFPrecioEditarActionPerformed

    private void jFPrecioEditarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFPrecioEditarKeyTyped
        AccionesTablaProductos.validarNumerosYDecimal(evt);
    }//GEN-LAST:event_jFPrecioEditarKeyTyped

    private void jFCantidadEditarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFCantidadEditarKeyTyped
        AccionesTablaProductos.validarNumeros(evt);

    }//GEN-LAST:event_jFCantidadEditarKeyTyped

    private void JTFNProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTFNProductoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_JTFNProductoKeyTyped

    private void jBRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRegresarActionPerformed
        new JFMenuOpciones().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jBRegresarActionPerformed
    

//Filtro para ir Buscando por codigo parte1
    
 public void filtro() {
    filtro = jTBuscarCodigo.getText();
    trsfiltro.setRowFilter(RowFilter.regexFilter("(?i)" + filtro, 0));
}

public void filtro1() {
    filtro1 = jTBuscarNombre.getText();
    trsfiltro1.setRowFilter(RowFilter.regexFilter("(?i)" + filtro1, 1));
}

public void filtro2() {
    filtro2 = jTBuscarPrecio.getText();
    trsfiltro2.setRowFilter(RowFilter.regexFilter("(?i)" + filtro2, 2));
}

    
   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Cantidad;
    private javax.swing.JButton JBInsertar;
    private javax.swing.JTextField JTFCantidad;
    private javax.swing.JTextField JTFNProducto;
    private javax.swing.JTextField JTFPPorUnidad;
    private javax.swing.JButton jBActualizarDatos;
    private javax.swing.JButton jBBuscarCodigo;
    private javax.swing.JButton jBBuscarNombre;
    private javax.swing.JButton jBBuscarPrecio;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBRegresar;
    private javax.swing.JTextField jFCantidadEditar;
    private javax.swing.JTextField jFNombreEditar;
    private javax.swing.JTextField jFPrecioEditar;
    private javax.swing.JLabel jLNombre;
    private javax.swing.JLabel jLPrecio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTBuscarCodigo;
    private javax.swing.JTextField jTBuscarNombre;
    private javax.swing.JTextField jTBuscarPrecio;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelEditar;
    private javax.swing.JButton salir;
    // End of variables declaration//GEN-END:variables
}
