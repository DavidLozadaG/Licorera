package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estados_pedido")
public class Estado_pedido implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_estado;
    private String nomb_estado;

    public Estado_pedido() {
    }

    public Estado_pedido(int cod_estado, String nomb_estado) {
        super();
        this.cod_estado = cod_estado;
        this.nomb_estado = nomb_estado;
    }

    public int getCod_estado() {
        return cod_estado;
    }

    public void setCod_estado(int cod_estado) {
        this.cod_estado = cod_estado;
    }

    public String getNomb_estado() {
        return nomb_estado;
    }

    public void setNomb_estado(String nomb_estado) {
        this.nomb_estado = nomb_estado;
    }
    
}

