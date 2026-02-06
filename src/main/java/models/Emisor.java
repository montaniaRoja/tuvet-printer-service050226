package models;


public class Emisor {
    public String nit;
    public String nrc;
    public String nombre;
    public String codActividad;
    public String descActividad;
    public String nombreComercial;
    public String tipoEstablecimiento;
    public String direccion;
    public String telefono;
    public String correo;
    public String codEstableMH;
    public String codEstable;
    public String codPuntoVentaMH;
    public String conPuntoVenta;

    public Emisor(){

    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(String codActividad) {
        this.codActividad = codActividad;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String string) {
        this.direccion = string;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodEstableMH() {
        return codEstableMH;
    }

    public void setCodEstableMH(String codEstableMH) {
        this.codEstableMH = codEstableMH;
    }

    public String getCodEstable() {
        return codEstable;
    }

    public void setCodEstable(String codEstable) {
        this.codEstable = codEstable;
    }

    public String getCodPuntoVentaMH() {
        return codPuntoVentaMH;
    }

    public void setCodPuntoVentaMH(String codPuntoVentaMH) {
        this.codPuntoVentaMH = codPuntoVentaMH;
    }

    public String getConPuntoVenta() {
        return conPuntoVenta;
    }

    public void setConPuntoVenta(String conPuntoVenta) {
        this.conPuntoVenta = conPuntoVenta;
    }

	public String getDescActividad() {
		return descActividad;
	}

	public void setDescActividad(String descActividad) {
		this.descActividad = descActividad;}   
    
    
}
