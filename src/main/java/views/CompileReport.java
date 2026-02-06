package views;

import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileReport {

	 public static void main(String[] args) throws Exception {
	        JasperCompileManager.compileReportToFile(
	            "src/main/resources/reports/factura_tuvet.jrxml",
	            "src/main/resources/reports/factura_tuvet.jasper"
	        );
	        System.out.println("Reporte compilado a .jasper ✅");
	    }

}
