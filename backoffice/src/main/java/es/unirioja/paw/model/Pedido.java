package es.unirioja.paw.model;

import java.io.Serializable;

public class Pedido implements Serializable {

    private String codigo;
    private String codigoCliente;
    private String cp;
    private String calle;
    private String ciudad;
    private String provincia;

    public Pedido() {
    }

    public Pedido(String codigo, String codigoCliente, String cp, String calle, String ciudad, String provincia) {
        this.codigo = codigo;
        this.codigoCliente = codigoCliente;
        this.cp = cp;
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
