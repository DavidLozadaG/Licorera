package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="detalles")
public class Detalle implements Serializable{

    @ManyToOne
    @JoinColumn(name="cod_pedido")
    @Id
    private Pedido cod_pedido;

    @ManyToOne
    @JoinColumn(name="cod_licor")
    @Id
    private Licor cod_licor;

    private int cantidad;

    public Detalle() {
    }

    public Detalle(Pedido cod_pedido, Licor cod_licor, int cantidad) {
        super();
        this.cod_pedido = cod_pedido;
        this.cod_licor = cod_licor;
        this.cantidad = cantidad;
    }

    public Pedido getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(Pedido cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public Licor getCod_licor() {
        return cod_licor;
    }

    public void setCod_licor(Licor cod_licor) {
        this.cod_licor = cod_licor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
