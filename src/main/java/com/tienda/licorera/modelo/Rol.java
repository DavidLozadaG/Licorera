package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")

public class Rol implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_rol;
    private String nomb_rol;
    
    public Rol() {
    }

    public Rol(int cod_rol, String nomb_rol) {
        super();
        this.cod_rol = cod_rol;
        this.nomb_rol = nomb_rol;
    }
    public int getCod_rol() {
        return cod_rol;
    }
    public void setCod_rol(int cod_rol) {
        this.cod_rol = cod_rol;
    }
    public String getNomb_rol() {
        return nomb_rol;
    }
    public void setNomb_rol(String nomb_rol) {
        this.nomb_rol = nomb_rol;
    }
}
