package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="categorias")
public class Categoria implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_cat;
    private String nomb_cat;
    
    public Categoria() {
    }

    public Categoria(int cod_cat, String nomb_cat) {
        super();
        this.cod_cat = cod_cat;
        this.nomb_cat = nomb_cat;
    }

    public int getCod_cat() {
        return cod_cat;
    }

    public void setCod_cat(int cod_cat) {
        this.cod_cat = cod_cat;
    }

    public String getNomb_cat() {
        return nomb_cat;
    }

    public void setNomb_cat(String nomb_cat) {
        this.nomb_cat = nomb_cat;
    }

    @Override
    public String toString() {
        return "Categoria [cod_cat=" + cod_cat + ", nomb_cat=" + nomb_cat + "]";
    }
    
    
}
