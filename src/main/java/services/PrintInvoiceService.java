package services;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.CuerpoDocumento;
import models.Identificacion;
import models.Receptor;
import models.Resumen;
import models.SelloHacienda;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PrintInvoiceService {

    public static JasperPrint generateInvoiceReport(
        Identificacion identificacion,
        Receptor receptor,
        Resumen resumen,
        SelloHacienda sello,
        List<CuerpoDocumento> cuerpoDocumentoList) throws Exception {

        // 1. Crear el mapa de parámetros
        Map<String, Object> params = new HashMap<>();

        // Identificación
        params.put("numeroControl", identificacion.getNumeroControl());
        params.put("codigoGeneracion", identificacion.getCodigoGeneracion());
        params.put("sello", sello.getSello());
        params.put("fecEmi", identificacion.getFecEmi());
        params.put("horEmi", identificacion.getHorEmi());

        // Cliente (Receptor)
        params.put("nombre", receptor.getNombre());
        params.put("numDocumento", receptor.getNumDocumento());
        params.put("direccion", receptor.getDireccion());
        params.put("telefono", receptor.getTelefono());
        params.put("correo", receptor.getCorreo());

        // Resumen
        params.put("totalPagar", resumen.getTotalPagar());
        params.put("totalDescu", resumen.getTotalDescu());
        params.put("totalLetras", resumen.getTotalLetras());
        params.put("condicion", resumen.getCondicion());
        
        System.out.println("codigo de generacion "+identificacion.getCodigoGeneracion());
        

        // QR
        String qrUrl = "https://admin.factura.gob.sv/consultaPublica?" +
                "ambiente=01" +
                "&codGen=" + identificacion.getCodigoGeneracion().toUpperCase() +
                "&fechaEmi=" + identificacion.getFecEmi();
        
        System.out.println("sting QR "+qrUrl);

 // 3. Pasar la URL completa como parámetro al reporte
 params.put("qrCodeUrl", qrUrl);

        // 2. Crear el origen de datos (la lista de productos)
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cuerpoDocumentoList);

        // 3. Cargar y compilar el archivo JRXML desde el classpath
        // Asegúrate de que tu archivo factura_tuvet.jrxml está en la carpeta resources
        
        
        JasperReport jasperReport = null;

     // Intentamos compilar JRXML desde classpath (desarrollo)
     try (InputStream jrxmlStream = PrintInvoiceService.class.getResourceAsStream("/reports/factura_tuvet.jrxml")) {
         if (jrxmlStream != null) {
             jasperReport = JasperCompileManager.compileReport(jrxmlStream);
         } else {
             // fallback si está en la raíz del JAR
             try (InputStream jrxmlRoot = PrintInvoiceService.class.getResourceAsStream("/factura_tuvet.jrxml")) {
                 if (jrxmlRoot != null) {
                     jasperReport = JasperCompileManager.compileReport(jrxmlRoot);
                 } else {
                     throw new RuntimeException("No se encontró el JRXML en /reports ni en /");
                 }
             }
         }
     } catch (Exception e) {
         System.err.println("Error compilando JRXML, intentando cargar .jasper precompilado...");
         e.printStackTrace();

         // Intentamos cargar el .jasper precompilado
         try (InputStream jasperStream = PrintInvoiceService.class.getResourceAsStream("/reports/factura_tuvet.jasper")) {
             if (jasperStream != null) {
                 jasperReport = (JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObject(jasperStream);
             } else {
                 throw new RuntimeException("No se encontró el .jasper en /reports");
             }
         } catch (Exception ex) {
             System.err.println("Error cargando .jasper");
             ex.printStackTrace();
             throw new RuntimeException("No se pudo cargar ni compilar el reporte", ex);
         }
     }

     // Finalmente llenamos el reporte
     return JasperFillManager.fillReport(jasperReport, params, dataSource);


      
        


        // 4. Llenar el reporte y retornarlo
       
    }
}