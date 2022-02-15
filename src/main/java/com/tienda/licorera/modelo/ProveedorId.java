package com.tienda.licorera.modelo;

import java.io.Serializable;
import java.util.Objects;
public class ProveedorId implements Serializable {

    private int cod_licor;
    private String nit_empresa;

    
    public ProveedorId() {
    }
    

    public ProveedorId(int cod_licor, String nit_empresa) {
        super();
        this.cod_licor = cod_licor;
        this.nit_empresa = nit_empresa;
    }

    
    public int getCod_licor() {
        return cod_licor;
    }


    public void setCod_licor(int cod_licor) {
        this.cod_licor = cod_licor;
    }


    public String getNit_empresa() {
        return nit_empresa;
    }


    public void setNit_empresa(String nit_empresa) {
        this.nit_empresa = nit_empresa;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedorId proveedorId = (ProveedorId) o;
        return cod_licor==(proveedorId.cod_licor) &&
                nit_empresa.equals(proveedorId.nit_empresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod_licor, nit_empresa);
    }
}
