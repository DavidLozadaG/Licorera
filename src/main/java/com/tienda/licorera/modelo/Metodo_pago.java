package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "metodos_pagos")
public class Metodo_pago implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_metodo;
    private String nomb_metodo;
    
    public Metodo_pago() {
    }

    public Metodo_pago(int cod_metodo, String nomb_metodo) {
        super();
        this.cod_metodo = cod_metodo;
        this.nomb_metodo = nomb_metodo;
    }

    public int getCod_metodo() {
        return cod_metodo;
    }

    public void setCod_metodo(int cod_metodo) {
        this.cod_metodo = cod_metodo;
    }

    public String getNomb_metodo() {
        return nomb_metodo;
    }

    public void setNomb_metodo(String nomb_metodo) {
        this.nomb_metodo = nomb_metodo;
    }

    
}
