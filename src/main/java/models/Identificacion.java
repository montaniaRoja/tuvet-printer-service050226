package models;

public class Identificacion {

    public String tipoDte;
    public String numeroControl;
    public String codigoGeneracion;
    public String fecEmi;
    public String horEmi;
    public String tipoMoneda;

    public Identificacion(){

    }

    public String getTipoDte() {
        return tipoDte;
    }

    public void setTipoDte(String tipoDte) {
        this.tipoDte = tipoDte;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getCodigoGeneracion() {
        return codigoGeneracion;
    }

    public void setCodigoGeneracion(String codigoGeneracion) {
        this.codigoGeneracion = codigoGeneracion;
    }

    public String getFecEmi() {
        return fecEmi;
    }

    public void setFecEmi(String fecEmi) {
        this.fecEmi = fecEmi;
    }

    public String getHorEmi() {
        return horEmi;
    }

    public void setHorEmi(String horEmi) {
        this.horEmi = horEmi;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }
}
