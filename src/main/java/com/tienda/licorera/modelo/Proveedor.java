package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="proveedores")
public class Proveedor implements Serializable {

    @ManyToOne
    @JoinColumn(name="cod_licor")
    @Id
    private Licor cod_licor;

    @ManyToOne
    @JoinColumn(name="nit_empresa")
    @Id
    private Empresa nit_empresa;

    public Proveedor() {
    }

    public Proveedor(Licor cod_licor, Empresa nit_empresa) {
        super();
        this.cod_licor = cod_licor;
        this.nit_empresa = nit_empresa;
    }

    public Licor getCod_licor() {
        return cod_licor;
    }

    public void setCod_licor(Licor cod_licor) {
        this.cod_licor = cod_licor;
    }

    public Empresa getNit_empresa() {
        return nit_empresa;
    }

    public void setNit_empresa(Empresa nit_empresa) {
        this.nit_empresa = nit_empresa;
    }
    
}
