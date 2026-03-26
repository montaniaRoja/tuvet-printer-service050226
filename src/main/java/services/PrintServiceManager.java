package services;


import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import connection.SqliteConnection;

public class PrintServiceManager {

    public static boolean print(JasperPrint jasperPrint) {

        try {
        	
        	
            String nombreImpresora = SqliteConnection.getImpresora();

            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService selectedService = null;
           

            for (PrintService ps : services) {
            	if (ps.getName().equalsIgnoreCase(nombreImpresora.trim())) {
                    selectedService = ps;
                    break;
                }
            }

            // Si no encuentra la impresora
            if (selectedService == null) {
                System.out.println("⚠️ Impresora no encontrada, usando predeterminada");
                net.sf.jasperreports.engine.JasperPrintManager.printReport(jasperPrint, false);
                return true;
            }

            int copias = SqliteConnection.getCopias();

            for (int i = 0; i < copias; i++) {

                System.out.println("Imprimiendo copia " + (i + 1));

                JRPrintServiceExporter exporter = new JRPrintServiceExporter();

                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

                SimplePrintServiceExporterConfiguration config =
                        new SimplePrintServiceExporterConfiguration();

                config.setPrintService(selectedService);
                config.setDisplayPageDialog(false);
                config.setDisplayPrintDialog(false);

                exporter.setConfiguration(config);

                exporter.exportReport();

                Thread.sleep(150); // opcional (evita saturar impresora)
            }

            System.out.println("✅ Impreso en: " + nombreImpresora);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error al imprimir");
            e.printStackTrace();
            return false;
        }
    }
}
