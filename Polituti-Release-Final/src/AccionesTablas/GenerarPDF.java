package AccionesTablas;

import BaseDeDatos.ConexionBD;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerarPDF {

    private String nombreCliente;
    private String cedulaCliente;
    private String telefonoCliente;
    private String direccionCliente;

    private String fechaActual = "";
    private String nombreArchivoPDFVenta = "";

    // Método para obtener datos del cliente
    public void DatosCliente(int codigocliente) {
        // Coloca tus credenciales aquí
        String usuario = "root";
        String contrasena = "grupo4hlm";

        Connection cn = new ConexionBD().conexion(usuario, contrasena);
        String sql = "SELECT * FROM tablacliente WHERE codigocliente = ?";
        PreparedStatement pst;
        try {
            pst = cn.prepareStatement(sql);
            pst.setInt(0, codigocliente);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombreCliente = rs.getString("Nombre_Cliente");
                cedulaCliente = rs.getString("N_cedula");
                telefonoCliente = rs.getString("N_telefono");
               
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener datos del cliente: " + e);
        }
    }
// Método para obtener el total a pagar y agregarlo al PDF
private void agregarTotalAPagar(int codigoVenta, Paragraph info) {
    // Coloca tus credenciales aquí
    String usuario = "root";
    String contrasena = "grupo4hlm";

    Connection cn = new ConexionBD().conexion(usuario, contrasena);
    String sql = "SELECT precio_unitario, cantidad FROM tabladetallesf WHERE codigoVenta = ?";
    PreparedStatement pst;
    
    try {
        pst = cn.prepareStatement(sql);
        pst.setInt(1, codigoVenta);
        ResultSet rs = pst.executeQuery();
        
        double totalPagar = 0;
        
        while (rs.next()) {
            double precioUnitario = rs.getDouble("precio_unitario");
            int cantidad = rs.getInt("cantidad");
            totalPagar += precioUnitario * cantidad;
        }
        
        info.add("Total a pagar: " + String.format("%.2f", totalPagar));
        
        cn.close();
    } catch (SQLException e) {
        System.out.println("Error al obtener el total a pagar: " + e);
    }
}

// Método para obtener productos de la base de datos y agregarlos a la tabla del PDF
private void agregarProductosPDF(int codigoVenta, PdfPTable tablaProducto) {
    // Coloca tus credenciales aquí
    String usuario = "root";
    String contrasena = "grupo4hlm";

    Connection cn = new ConexionBD().conexion(usuario, contrasena);
    String sql = "SELECT p.Nombre_Producto, d.Cantidad, p.Precio_Unitario, (d.Cantidad * p.Precio_Unitario) AS Total " +
                 "FROM tabladetallesf d " +
                 "JOIN tablaproducto p ON d.codigoproducto = p.codigoproducto " +
                 "WHERE d.codigofactura = ?";

    try {
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, codigoVenta); // Set the specific factura code
        ResultSet rs = pst.executeQuery();

        // Agregar productos a la tabla del PDF
        while (rs.next()) {
            String cantidad = rs.getString("Cantidad");
            String descripcion = rs.getString("Nombre_Producto");
            String precioUnitario = rs.getString("Precio_Unitario");
            String precioTotalStr = String.format("%.2f", rs.getFloat("Total"));

            tablaProducto.addCell(cantidad);
            tablaProducto.addCell(descripcion);
            tablaProducto.addCell(precioUnitario);
            tablaProducto.addCell(precioTotalStr);
        }

        cn.close();
    } catch (SQLException e) {
        System.out.println("Error al obtener productos: " + e);
    }
}


    // Método para generar la factura de venta
    public void generarFacturaPDF(int codigoVenta) {
        try {
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
                Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
                fecha.add(Chunk.NEWLINE); // agregar nueva línea
                fecha.add("Factura: 001" + "\nFecha: " + fechaActual + "\n\n");

                PdfPTable Encabezado = new PdfPTable(4);
                Encabezado.setWidthPercentage(100);
                Encabezado.getDefaultCell().setBorder(0); // quitar el borde de la tabla
                // Tamaño de las celdas
                float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
                Encabezado.setWidths(ColumnaEncabezado);
                Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
                // Agregar celdas
                Encabezado.addCell(img);

                String ruc = "0987654321001";
                String nombre = "Fantasma Cooporation";
                String telefono = "0987654321";
                String direccion = "Tamarindo City";
                String razon = "La magia de la programacion, esta en el poder de tu imaginacion";

                Encabezado.addCell(""); // celda vacía
                Encabezado.addCell("RUC: " + ruc + "\nNOMBRE: " + nombre + "\nTELEFONO: " + telefono + "\nDIRECCION: " + direccion + "\nRAZON SOCIAL: " + razon);
                Encabezado.addCell(fecha);
                doc.add(Encabezado);

                // CUERPO
                Paragraph cliente = new Paragraph();
                cliente.add(Chunk.NEWLINE); // nueva línea
                cliente.add("Datos del cliente: " + "\n\n");
                doc.add(cliente);

                // DATOS DEL CLIENTE
                PdfPTable tablaCliente = new PdfPTable(4);
                tablaCliente.setWidthPercentage(100);
                tablaCliente.getDefaultCell().setBorder(0); // quitar bordes
                // Tamaño de las celdas
                float[] ColumnaCliente = new float[]{25f, 45f, 30f, 40f};
                tablaCliente.setWidths(ColumnaCliente);
                tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
                PdfPCell cliente1 = new PdfPCell(new Phrase("Cedula/RUC: ", negrita));
                PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre: ", negrita));
                PdfPCell cliente3 = new PdfPCell(new Phrase("Telefono: ", negrita));
                PdfPCell cliente4 = new PdfPCell(new Phrase("Direccion: ", negrita));
                // Quitar bordes 
                cliente1.setBorder(0);
                cliente2.setBorder(0);
                cliente3.setBorder(0);
                cliente4.setBorder(0);
                // Agregar celdas a la tabla
                tablaCliente.addCell(cliente1);
                tablaCliente.addCell(cliente2);
                tablaCliente.addCell(cliente3);
                tablaCliente.addCell(cliente4);
                tablaCliente.addCell(cedulaCliente);
                tablaCliente.addCell(nombreCliente);
                tablaCliente.addCell(telefonoCliente);
                tablaCliente.addCell(direccionCliente);
                // Agregar al documento
                doc.add(tablaCliente);

                // ESPACIO EN BLANCO
                Paragraph espacio = new Paragraph();
                espacio.add(Chunk.NEWLINE);
                espacio.add("");
                espacio.setAlignment(Element.ALIGN_CENTER);
                doc.add(espacio);

                // AGREGAR LOS PRODUCTOS
                PdfPTable tablaProducto = new PdfPTable(4);
                tablaProducto.setWidthPercentage(100);
                tablaProducto.getDefaultCell().setBorder(0);
                // Tamaño de celdas
                float[] ColumnaProducto = new float[]{15f, 50f, 15f, 20f};
                tablaProducto.setWidths(ColumnaProducto);
                tablaProducto.setHorizontalAlignment(Element.ALIGN_LEFT);
                PdfPCell producto1 = new PdfPCell(new Phrase("Cantidad: ", negrita));
                PdfPCell producto2 = new PdfPCell(new Phrase("Descripcion: ", negrita));
                PdfPCell producto3 = new PdfPCell(new Phrase("Precio Unitario: ", negrita));
                PdfPCell producto4 = new PdfPCell(new Phrase("Precio Total: ", negrita));
                // Quitar bordes
                producto1.setBorder(0);
                producto2.setBorder(0);
                producto3.setBorder(0);
                producto4.setBorder(0);
                // Agregar color al encabezado del producto
                producto1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                producto2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                producto3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                producto4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                // Agregar celdas a la tabla
                tablaProducto.addCell(producto1);
                tablaProducto.addCell(producto2);
                tablaProducto.addCell(producto3);
                tablaProducto.addCell(producto4);

                   agregarProductosPDF(codigoVenta, tablaProducto);
                // Agregar al documento
                doc.add(tablaProducto);

                // Total a pagar
                Paragraph info = new Paragraph();
                info.add(Chunk.NEWLINE);
                           agregarTotalAPagar(codigoVenta, info);

                info.setAlignment(Element.ALIGN_RIGHT);
                doc.add(info);

                // Firma
                Paragraph firma = new Paragraph();
                firma.add(Chunk.NEWLINE);
                firma.add("Cancelacion y firma\n\n");
                firma.add("_______________________");
                firma.setAlignment(Element.ALIGN_CENTER);
                doc.add(firma);

                // Mensaje
                Paragraph mensaje = new Paragraph();
                mensaje.add(Chunk.NEWLINE);
                mensaje.add("¡Gracias por su compra!");
                mensaje.setAlignment(Element.ALIGN_CENTER);
                doc.add(mensaje);

                // Cerrar el documento y el archivo
                doc.close();
                archivo.close();

                // Abrir el documento al ser generado automáticamente
                Desktop.getDesktop().open(file);

            } catch (DocumentException | IOException e) {
                System.out.println("Error en: " + e);
            }
        } catch (Exception e) {
            System.out.println("Error al generar el archivo PDF: " + e);
        }
    }

    // Puedes agregar otros métodos y lógica adicional aquí según tus necesidades
}

