package es.unirioja.paw.model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String codigo;
    private String username;
    private String nombre;
    private String email;
    private String codigoPostal;
    private String ciudad;
    private String provincia;

    public Cliente() {
    }

    public Cliente(String codigo, String username, String nombre, String email, String codigoPostal, String ciudad, String provincia) {
        this.codigo = codigo;
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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
