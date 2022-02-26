package com.tienda.licorera.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios")

public class Usuario implements Serializable{

    @Id
    @NotBlank
    @Size(min=4,max = 20)
    private String cedula;

    @NotBlank
    @Size(min=2,max = 45)
    private String nombres;

    @NotBlank
    @Size(min=2,max = 45)
    private String apellidos;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_nac;
  
    private int edad;

    @NotBlank
    @Size(min = 10,max=10)
    private String telefono;

    @NotBlank
    @Email
    @Size(max=256)
    private String email;

    @NotBlank
    @Size(max=45)
    private String direccion;

    @NotBlank
    @Size(min=8,max=256)
    private String clave;

    @Transient
    @Size(min=8,max=256)
    private String confirmarClave;

    @ManyToOne
    @JoinColumn(name="cod_rol")
    private Rol cod_rol;
    
    

    public Usuario() {
    }

    public Usuario(@NotBlank @Size(min = 4, max = 20) String cedula, @NotBlank @Size(min = 2, max = 45) String nombres,
            @NotBlank @Size(min = 2, max = 45) String apellidos, LocalDate fecha_nac, int edad,
            @NotBlank @Size(min = 10, max = 10) String telefono, @NotBlank @Email @Size(max = 256) String email,
            @NotBlank @Size(max = 45) String direccion, @NotBlank @Size(min = 8, max = 256) String clave,
            @Size(min = 8, max = 256) String confirmarClave, Rol cod_rol) {
        super();
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nac = fecha_nac;
        this.edad = edad;
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
                + ", edad=" + edad + ", email=" + email + ", fecha_nac=" + fecha_nac + ", nombres=" + nombres
                + ", telefono=" + telefono + "]";
    }
}
