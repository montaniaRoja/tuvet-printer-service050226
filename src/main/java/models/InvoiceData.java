package models;

import java.util.ArrayList;
import java.util.List;

public class InvoiceData {
	
	public Emisor emisor;
    public Identificacion identificacion;
    public Receptor receptor;
    public SelloHacienda sello;
    public Resumen resumen;
    public List<CuerpoDocumento> cuerpoDocumentoList;
    public String qrCodeData;
    
    public InvoiceData() {
    	
    }

	public Emisor getEmisor() {
		return emisor;
	}

	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}

	public Identificacion getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(Identificacion identificacion) {
		this.identificacion = identificacion;
	}

	public Receptor getReceptor() {
		return receptor;
	}

	public void setReceptor(Receptor receptor) {
		this.receptor = receptor;
	}

	public SelloHacienda getSello() {
		return sello;
	}

	public void setSello(SelloHacienda sello) {
		this.sello = sello;
	}

	public Resumen getResumen() {
		return resumen;
	}

	public void setResumen(Resumen resumen) {
		this.resumen = resumen;
	}

	public List<CuerpoDocumento> getCuerpoDocumentoList() {
		return cuerpoDocumentoList;
	}

	public void setCuerpoDocumentoList(List<CuerpoDocumento> cuerpoDocumentoList) {
		this.cuerpoDocumentoList = cuerpoDocumentoList;
	}

	public String getQrCodeData() {
		return qrCodeData;
	}

	public void setQrCodeData(String qrCodeData) {
		this.qrCodeData = qrCodeData;
	}
    
    
    
    
}
