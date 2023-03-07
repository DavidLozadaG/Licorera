package com.tienda.licorera.dto;

public class IngresarDetalle {
    private int cod_licor;
    private int cantidad;
    
    public IngresarDetalle() {
    }

    public IngresarDetalle(int cod_licor, int cantidad) {
        this.cod_licor = cod_licor;
        this.cantidad = cantidad;
    }

    public int getCod_licor() {
        return cod_licor;
    }

    public void setCod_licor(int cod_licor) {
        this.cod_licor = cod_licor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "IngresarDetalle [cantidad=" + cantidad + ", cod_licor=" + cod_licor +"]";
    }

}
