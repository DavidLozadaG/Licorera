package com.tienda.licorera.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios")

public class Usuario implements Serializable{

    @Id
    private String cedula;
    private String nombres;
    private String apellidos;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_nac;
  
    private String telefono;
    private String email;
    private String direccion;
    private String clave;
    @Transient
    private String confirmarClave;
    @ManyToOne
    @JoinColumn(name="cod_rol")
    private Rol cod_rol;
    

    public Usuario() {
    }
 
    public Usuario(String cedula, String nombres, String apellidos, LocalDate fecha_nac, String telefono,
            String email, String direccion, String clave, String confirmarClave, Rol cod_rol) {
        super();
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nac = fecha_nac;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.clave = clave;
        this.confirmarClave = confirmarClave;
        this.cod_rol = cod_rol;
    }



    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
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

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public Rol getCod_rol() {
        return cod_rol;
    }

    public void setCod_rol(Rol cod_rol) {
        this.cod_rol = cod_rol;
    }

    @Override
    public String toString() {
        return "Usuario [apellidos=" + apellidos + ", cedula=" + cedula + ", clave=" + clave + ", cod_rol=" + cod_rol
                + ", email=" + email + ", fecha_nac=" + fecha_nac + ", nombres=" + nombres
                + ", telefono=" + telefono + "]";
    }
}
