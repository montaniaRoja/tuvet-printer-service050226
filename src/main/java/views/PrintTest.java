package views;


import java.awt.*;
import java.awt.print.*;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import connection.SqliteConnection;

public class PrintTest {

    public static boolean printTestPage() {

        try {
            String nombreImpresora = SqliteConnection.getImpresora();

            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService selectedService = null;

            for (PrintService ps : services) {
                if (ps.getName().equalsIgnoreCase(nombreImpresora)) {
                    selectedService = ps;
                    break;
                }
            }

            PrinterJob job = PrinterJob.getPrinterJob();

            if (selectedService != null) {
                job.setPrintService(selectedService);
            } else {
                System.out.println("⚠️ No se encontró la impresora, usando predeterminada");
            }
            
         // Crear formato de página
            PageFormat pf = job.defaultPage();

            // Crear papel personalizado (80mm)
            Paper paper = new Paper();

            // 80 mm ≈ 226 puntos
            double width = 226;
            double height = 600; // largo libre (puede ser grande)

            paper.setSize(width, height);

            // Área imprimible (SIN márgenes)
            paper.setImageableArea(0, 0, width, height);
            paper.setSize(width, height);

            pf.setPaper(paper);
            pf.setOrientation(PageFormat.PORTRAIT);

            // aplicar formato
          

            job.setPrintable(new Printable() {
                @Override
                public int print(Graphics g, PageFormat pf, int pageIndex) {
                    if (pageIndex > 0) return NO_SUCH_PAGE;

                    Graphics2D g2d = (Graphics2D) g;
                    
                    // Empujamos 10 puntos a la derecha (X) y 10 hacia abajo (Y)
                    g2d.translate(pf.getImageableX() + 10, pf.getImageableY() + 10);

                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    int y = 0; 

                    // Ahora dibujamos en x=0 porque el translate ya movió el "origen"
                    g2d.drawString("********************************", 0, y);
                    y += 15;
                    g2d.drawString("PRUEBA DE IMPRESORA", 0, y);
                    y += 15;
                    g2d.drawString("********************************", 0, y);
                    y += 20;
                    g2d.drawString("Sucursal: " + SqliteConnection.getUUID(), 0, y);
                    y += 20;
                    g2d.drawString("Fecha: " + new java.util.Date().toString(), 0, y);
                    y += 20;
                    g2d.drawString("-------------------------------", 0, y);
                    y += 15;
                    g2d.drawString("ESTADO: OK", 0, y);

                    return PAGE_EXISTS;
                }
            }, pf);

            job.print();

            System.out.println("✅ Prueba enviada a impresora");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error imprimiendo prueba");
            e.printStackTrace();
            return false;
        }
    }
}