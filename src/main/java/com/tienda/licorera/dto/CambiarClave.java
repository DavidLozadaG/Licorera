package com.tienda.licorera.dto;

import javax.validation.constraints.NotNull;

public class CambiarClave {
    @NotNull
    private String id;

    private String claveActual;
    private String claveNueva;
    private String confirmarClaveNueva;
    
    public CambiarClave() {
    }

    public CambiarClave(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getConfirmarClaveNueva() {
        return confirmarClaveNueva;
    }

    public void setConfirmarClaveNueva(String confirmarClaveNueva) {
        this.confirmarClaveNueva = confirmarClaveNueva;
    }

   
    


}
