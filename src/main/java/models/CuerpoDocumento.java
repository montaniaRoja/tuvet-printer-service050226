package models;

public class CuerpoDocumento {
    public int cantidad;
    public String unidad="Unidad";
    public String descripcion;
    public Double precioUni;
    public Double montoDescu;
    public Double ventaNoSuj;
    public Double ventaExenta;
    public Double ventaGravada;
    public Double precioSugerido;
    public Double ivaItem;

    public CuerpoDocumento(){

    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioUni() {
        return precioUni;
    }

    public void setPrecioUni(Double precioUni) {
        this.precioUni = precioUni;
    }

    public Double getMontoDescu() {
        return montoDescu;
    }

    public void setMontoDescu(Double montoDescu) {
        this.montoDescu = montoDescu;
    }

    public Double getVentaNoSuj() {
        return ventaNoSuj;
    }

    public void setVentaNoSuj(Double ventaNoSuj) {
        this.ventaNoSuj = ventaNoSuj;
    }

    public Double getVentaExenta() {
        return ventaExenta;
    }

    public void setVentaExenta(Double ventaExenta) {
        this.ventaExenta = ventaExenta;
    }

    public Double getVentaGravada() {
        return ventaGravada;
    }

    public void setVentaGravada(Double ventaGravada) {
        this.ventaGravada = ventaGravada;
    }

    public Double getPrecioSugerido() {
        return precioSugerido;
    }

    public void setPrecioSugerido(Double precioSugerido) {
        this.precioSugerido = precioSugerido;
    }

    public Double getIvaItem() {
        return ivaItem;
    }

    public void setIvaItem(Double ivaItem) {
        this.ivaItem = ivaItem;
    }
}
