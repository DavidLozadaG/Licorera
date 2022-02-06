package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empresas")
public class Empresa implements Serializable{
    @Id
    private String nit_empresa;
    private String nomb_empresa;
    private String direccion;
    private String telefono;

    public Empresa() {
    }

    public Empresa(String nit_empresa, String nomb_empresa, String direccion, String telefono) {
        super();
        this.nit_empresa = nit_empresa;
        this.nomb_empresa = nomb_empresa;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNit_empresa() {
        return nit_empresa;
    }

    public void setNit_empresa(String nit_empresa) {
        this.nit_empresa = nit_empresa;
    }

    public String getNomb_empresa() {
        return nomb_empresa;
    }

    public void setNomb_empresa(String nomb_empresa) {
        this.nomb_empresa = nomb_empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
