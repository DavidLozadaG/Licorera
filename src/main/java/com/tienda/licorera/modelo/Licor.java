package com.tienda.licorera.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="licores")

public class Licor implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_licor;
    private String nomb_licor;
    private String foto;
    private Double grado_alcoh;
    private String descripcion;
    private int cantidad;
    private int precio;

    @ManyToOne
    @JoinColumn(name="cod_cat")
    private Categoria cod_cat;

    public Licor() {
    }

    public Licor(int cod_licor, String nomb_licor, String foto, Double grado_alcoh, String descripcion,
            int cantidad, int precio, Categoria cod_cat) {
        super();
        this.cod_licor = cod_licor;
        this.nomb_licor = nomb_licor;
        this.foto = foto;
        this.grado_alcoh = grado_alcoh;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.cod_cat = cod_cat;
    }

    public int getCod_licor() {
        return cod_licor;
    }

    public void setCod_licor(int cod_licor) {
        this.cod_licor = cod_licor;
    }

    public String getNomb_licor() {
        return nomb_licor;
    }

    public void setNomb_licor(String nomb_licor) {
        this.nomb_licor = nomb_licor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Double getGrado_alcoh() {
        return grado_alcoh;
    }

    public void setGrado_alcoh(Double grado_alcoh) {
        this.grado_alcoh = grado_alcoh;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Categoria getCod_cat() {
        return cod_cat;
    }

    public void setCod_cat(Categoria cod_cat) {
        this.cod_cat = cod_cat;
    }

    
    
    
}
