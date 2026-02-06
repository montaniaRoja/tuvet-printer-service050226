package Invoice;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.CodigoQr;
import models.CuerpoDocumento;
import models.Emisor;
import models.Identificacion;
import models.Receptor;
import models.Resumen;
import models.SelloHacienda;
import net.sf.jasperreports.engine.JasperPrint;
import services.PrintInvoiceService;
import net.sf.jasperreports.engine.JasperPrintManager;

public class PrintInvoice {

    public static void printInvoice(String jsonFactura) throws Exception {
        System.out.println("factura recibida");

        ObjectMapper mapper = new ObjectMapper();

        Emisor emisor = new Emisor();
        SelloHacienda sello = new SelloHacienda();
        Identificacion identificacion = new Identificacion();
        Receptor receptor = new Receptor();
        Resumen resumen = new Resumen();
        CodigoQr codigoQr=new CodigoQr();
        List<CuerpoDocumento> cuerpoList = new ArrayList<>();

        // 1. Parsear el wrapper
        JsonNode wrapper = mapper.readTree(jsonFactura);

        // 2. Obtener sello recibido
        sello.setSello(wrapper.path("sello_recibido").asText());

        // 3. Parsear dte_json que viene como string
        String dteJsonStr = wrapper.path("dte_json").asText();
        JsonNode dteRoot = mapper.readTree(dteJsonStr);

        // 4. Emisor
        JsonNode emisorNode = dteRoot.path("emisor");
        emisor.setNombreComercial(emisorNode.path("nombreComercial").asText());
        emisor.setCodActividad(emisorNode.path("codActividad").asText());
        emisor.setDescActividad(emisorNode.path("descActividad").asText());
        emisor.setNit(emisorNode.path("nit").asText());
        emisor.setNrc(emisorNode.path("nrc").asText());
        emisor.setTelefono(emisorNode.path("telefono").asText());
        emisor.setCorreo(emisorNode.path("correo").asText());

        JsonNode dirEmisorNode = emisorNode.path("direccion");
        if (dirEmisorNode.isObject()) {
            String departamento = dirEmisorNode.path("departamento").asText();
            String municipio = dirEmisorNode.path("municipio").asText();
            String complemento = dirEmisorNode.path("complemento").asText();

            if (departamento.isEmpty() || municipio.isEmpty() || complemento.isEmpty()) {
                emisor.setDireccion(null);
            } else {
                emisor.setDireccion("departamento " + departamento + ", municipio " + municipio + ", " + complemento);
            }
        } else {
            emisor.setDireccion(dirEmisorNode.asText());
        }

        // 5. Identificación
        JsonNode identificacionNode = dteRoot.path("identificacion");
        identificacion.setCodigoGeneracion(identificacionNode.path("codigoGeneracion").asText());
        identificacion.setNumeroControl(identificacionNode.path("numeroControl").asText());
        identificacion.setFecEmi(identificacionNode.path("fecEmi").asText());
        identificacion.setHorEmi(identificacionNode.path("horEmi").asText());

        // 6. Receptor
        JsonNode receptorNode = dteRoot.path("receptor");
        receptor.setCodActividad(receptorNode.path("codActividad").asText());
        receptor.setNumDocumento(receptorNode.path("numDocumento").asText());
        receptor.setNombre(receptorNode.path("nombre").asText());
        receptor.setNrc(receptorNode.path("nrc").asText());
        receptor.setDescActividad(receptorNode.path("descActividad").asText());
        receptor.setTelefono(receptorNode.path("telefono").asText());
        receptor.setCorreo(receptorNode.path("correo").asText());

        JsonNode dirReceptorNode = receptorNode.path("direccion");
        if (dirReceptorNode.isObject()) {
            String departamento = dirReceptorNode.path("departamento").asText();
            String municipio = dirReceptorNode.path("municipio").asText();
            String complemento = dirReceptorNode.path("complemento").asText();

            if (departamento.isEmpty() || municipio.isEmpty() || complemento.isEmpty()) {
                receptor.setDireccion(null);
            } else {
                receptor.setDireccion("departamento " + departamento + ", municipio " + municipio + ", " + complemento);
            }
        } else {
            receptor.setDireccion(dirReceptorNode.asText());
        }

        // 7. Resumen
        JsonNode resumenNode = dteRoot.path("resumen");
        resumen.setCondicionOperacion(resumenNode.path("condicionOperacion").asInt());
        resumen.setMontoTotalOperacion(resumenNode.path("montoTotalOperacion").asDouble());
        resumen.setSubTotal(resumenNode.path("subTotal").asDouble());
        resumen.setSubTotalVentas(resumenNode.path("subTotalVentas").asDouble());
        resumen.setTotalDescu(resumenNode.path("totalDescu").asDouble());
        resumen.setTotalGravada(resumenNode.path("totalGravada").asDouble());
        resumen.setTotalIva(resumenNode.path("totalIva").asDouble());
        resumen.setTotalLetras(resumenNode.path("totalLetras").asText());
        resumen.setTotalPagar(resumenNode.path("totalPagar").asDouble());
        resumen.setCondicion("Credito");
        if(resumen.getCondicionOperacion()==1) {
        	resumen.condicion="Contado";
        }

        // 8. CuerpoDocumento
        JsonNode cuerpoArray = dteRoot.path("cuerpoDocumento");
        if (cuerpoArray.isArray()) {
            for (JsonNode item : cuerpoArray) {
                CuerpoDocumento cuerpo = new CuerpoDocumento();
                cuerpo.setCantidad(item.path("cantidad").asInt());
                cuerpo.setDescripcion(item.path("descripcion").asText());
                cuerpo.setPrecioUni(item.path("precioUni").asDouble());
                cuerpo.setMontoDescu(item.path("montoDescu").asDouble());
                cuerpo.setVentaNoSuj(item.path("ventaNoSuj").asDouble());
                cuerpo.setVentaExenta(item.path("ventaExenta").asDouble());
                cuerpo.setVentaGravada(item.path("ventaGravada").asDouble());
                cuerpo.setPrecioSugerido(item.path("psv").asDouble());
                cuerpo.setIvaItem(item.path("ivaItem").asDouble());
                cuerpoList.add(cuerpo);
            }
        }
        
        //generar QR
        
        codigoQr.setAmbiente("00");
        codigoQr.setCodGeneracion(identificacion.getCodigoGeneracion());
        codigoQr.setFechaEmi(identificacion.getFecEmi());
        codigoQr.setUrl("https://admin.factura.gob.sv/consultaPublica?");
        
        // 9. Llama al servicio para generar el reporte
        JasperPrint jasperPrint = PrintInvoiceService.generateInvoiceReport(
            identificacion,
            receptor,
            resumen,
            sello,
            cuerpoList
        );
        
        if(JasperPrintManager.printReport(jasperPrint, false)) {
        	System.out.println("Factura impresa exitosamente.");
        } else {
        	System.out.println("Error al imprimir.");
        }
         

/*
        // 10. Exportar a PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, "factura.pdf");
        System.out.println("✅ Reporte PDF generado exitosamente.");     
         
      */
    }
    
   
}

