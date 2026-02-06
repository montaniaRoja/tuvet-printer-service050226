package models;

public class Resumen {
    public Double totalGravada;
    public Double subTotalVentas;
    public Double totalDescu;
    public Double subTotal;
    public Double montoTotalOperacion;
    public Double totalPagar;
    public String totalLetras;
    public Double totalIva;
    public int condicionOperacion;
    public String condicion;

    public Resumen(){

    }

    public Double getTotalGravada() {
        return totalGravada;
    }

    public void setTotalGravada(Double totalGravada) {
        this.totalGravada = totalGravada;
    }

    public Double getSubTotalVentas() {
        return subTotalVentas;
    }

    public void setSubTotalVentas(Double subTotalVentas) {
        this.subTotalVentas = subTotalVentas;
    }

    public Double getTotalDescu() {
        return totalDescu;
    }

    public void setTotalDescu(Double totalDescu) {
        this.totalDescu = totalDescu;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getMontoTotalOperacion() {
        return montoTotalOperacion;
    }

    public void setMontoTotalOperacion(Double montoTotalOperacion) {
        this.montoTotalOperacion = montoTotalOperacion;
    }

    public Double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getTotalLetras() {
        return totalLetras;
    }

    public void setTotalLetras(String totalLetras) {
        this.totalLetras = totalLetras;
    }

    public Double getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(Double totalIva) {
        this.totalIva = totalIva;
    }

    public int getCondicionOperacion() {
        return condicionOperacion;
    }

    public void setCondicionOperacion(int condicionOperacion) {
        this.condicionOperacion = condicionOperacion;
    }

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
    
    
}
