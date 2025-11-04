package es.unirioja.paw.model;

import java.io.Serializable;

public class Almacen implements Serializable {

    private String codigo;
    private String calle;
    private String ciudad;
    private String cp;
    private String provincia;

    public Almacen() {
    }

    public Almacen(String codigo, String calle, String ciudad, String cp, String provincia) {
        this.codigo = codigo;
        this.calle = calle;
        this.ciudad = ciudad;
        this.cp = cp;
        this.provincia = provincia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
