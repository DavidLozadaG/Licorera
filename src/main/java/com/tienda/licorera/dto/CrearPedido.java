package com.tienda.licorera.dto;

public class CrearPedido {
    
    private String cedula;
    private int cod_metodo;
    private int cod_estado;
    
    public CrearPedido() {
    }

    public CrearPedido(String cedula, int cod_metodo, int cod_estado) {
        this.cedula = cedula;
        this.cod_metodo = cod_metodo;
        this.cod_estado = cod_estado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getCod_metodo() {
        return cod_metodo;
    }

    public void setCod_metodo(int cod_metodo) {
        this.cod_metodo = cod_metodo;
    }

    public int getCod_estado() {
        return cod_estado;
    }

    public void setCod_estado(int cod_estado) {
        this.cod_estado = cod_estado;
    }

    @Override
    public String toString() {
        return "CrearPedido [cedula=" + cedula + ", cod_estado=" + cod_estado + ", cod_metodo=" + cod_metodo + "]";
    }

        
    
}
