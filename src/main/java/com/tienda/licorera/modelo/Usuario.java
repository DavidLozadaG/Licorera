package com.tienda.licorera.modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")

public class Usuario implements Serializable{
    @Id
    private String cedula;
    private String nombres;
    private String apellidos;
    private Date fecha_nac;
    private int edad;
    private String telefono;
    private String email;
    private String clave;

    @ManyToOne
    @JoinColumn(name="cod_rol")
    private Rol cod_rol;
    
    

    public Usuario() {
    }

    public Usuario(String cedula, String nombres, String apellidos, Date fecha_nac, int edad, String telefono,
            String email, String clave, Rol cod_rol) {
        super();
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nac = fecha_nac;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
        this.cod_rol = cod_rol;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rol getCod_rol() {
        return cod_rol;
    }

    public void setCod_rol(Rol cod_rol) {
        this.cod_rol = cod_rol;
    }

    
    
}
