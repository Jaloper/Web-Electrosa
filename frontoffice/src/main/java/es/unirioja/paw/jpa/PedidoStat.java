/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.jpa;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PedidoStat {
    private String codigoPedido;
    private LocalDate fechaEnvio;
    private LocalDate fechaEntrega;
    private long difFecha;
    private int mesEnvio;
    private String regionEnvio; // PB: Península/Baleares, IB: Islas Canarias, IN: Internacional

    public PedidoStat() {
    }

    public PedidoStat(String codigoPedido, LocalDate fechaEnvio, LocalDate fechaEntrega, String shippingRegion) {
        this.codigoPedido = codigoPedido;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntrega = fechaEntrega;
        this.regionEnvio = shippingRegion;
        this.difFecha = calcularDiferencia();
        this.mesEnvio = fechaEnvio.getMonthValue();
    }

    private long calcularDiferencia() {
        if (fechaEnvio == null || fechaEntrega == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(fechaEnvio, fechaEntrega);
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
        if (fechaEnvio != null) {
            this.mesEnvio = fechaEnvio.getMonthValue();
        }
        this.difFecha = calcularDiferencia();
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate deliveryAt) {
        this.fechaEntrega = deliveryAt;
        this.difFecha = calcularDiferencia();
    }

    public long getDifFecha() {
        return difFecha;
    }

    public int getMesEnvio() {
        return mesEnvio;
    }

    public String getRegionEnvio() {
        return regionEnvio;
    }

    public void setRegionEnvio(String shippingRegion) {
        this.regionEnvio = shippingRegion;
    }

    @Override
    public String toString() {
        return "PedidoStat{" +
                "codigoPedido='" + codigoPedido + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", fechaEntrega=" + fechaEntrega +
                ", difFecha=" + difFecha +
                ", mesEnvio=" + mesEnvio +
                ", regionEnvio='" + regionEnvio + '\'' +
                '}';
    }
}
