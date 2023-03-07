package com.tienda.licorera.modelo;

import java.io.Serializable;
import java.util.Objects;

public class DetalleId implements Serializable{

    private int cod_pedido;
    private int cod_licor;
    
    public DetalleId() {
    }

    public DetalleId(int cod_pedido, int cod_licor) {
        this.cod_pedido = cod_pedido;
        this.cod_licor = cod_licor;
    }

    public int getcod_pedido() {
        return cod_pedido;
    }

    public void setcod_pedido(int cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public int getCod_licor() {
        return cod_licor;
    }

    public void setCod_licor(int cod_licor) {
        this.cod_licor = cod_licor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleId detalleId = (DetalleId) o;
        return cod_pedido==(detalleId.cod_pedido) &&
                cod_licor==(detalleId.cod_licor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod_pedido, cod_licor);
    }

    
}
