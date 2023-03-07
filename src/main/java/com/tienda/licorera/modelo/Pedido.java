package com.tienda.licorera.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedidos")

public class Pedido implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_pedido;
    private Timestamp fecha_pedido;

    @ManyToOne
    @JoinColumn(name="cedula")
    private Usuario cedula;

    @ManyToOne
    @JoinColumn(name="cod_metodo")
    private Metodo_pago cod_metodo;

    @ManyToOne
    @JoinColumn(name="cod_estado")
    private Estado_pedido cod_estado;
    private String observacion;

    public Pedido() {
    }

    public Pedido(int cod_pedido, Timestamp fecha_pedido, Usuario cedula, Metodo_pago cod_metodo, Estado_pedido cod_estado, String observacion) {
        super();
        this.cod_pedido = cod_pedido;
        this.fecha_pedido = fecha_pedido;
        this.cedula = cedula;
        this.cod_metodo = cod_metodo;
        this.cod_estado = cod_estado;
        this.observacion = observacion;
    }

    public int getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(int cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public Timestamp getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Timestamp fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public Usuario getCedula() {
        return cedula;
    }

    public void setCedula(Usuario cedula) {
        this.cedula = cedula;
    }

    public Metodo_pago getCod_metodo() {
        return cod_metodo;
    }

    public void setCod_metodo(Metodo_pago cod_metodo) {
        this.cod_metodo = cod_metodo;
    }

    public Estado_pedido getCod_estado() {
        return cod_estado;
    }

    public void setCod_estado(Estado_pedido cod_estado) {
        this.cod_estado = cod_estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return "Pedido [cedula=" + cedula + ", cod_estado=" + cod_estado + ", cod_metodo=" + cod_metodo
                + ", cod_pedido=" + cod_pedido + ", fecha_pedido=" + fecha_pedido + ", observacion=" + observacion
                + "]";
    }
    
}
