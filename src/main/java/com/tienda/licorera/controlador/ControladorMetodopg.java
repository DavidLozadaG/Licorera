package com.tienda.licorera.controlador;

import java.util.List;

import com.tienda.licorera.modelo.Metodo_pago;
import com.tienda.licorera.sevicio.IMetodoPgServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorMetodopg {
    
    @Autowired
    IMetodoPgServicio metodo_pagoServicio;

    //INICIO GESTION DE METODOS DE PAGO
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/metodospg")
    public String gestionMet(Model modelo, Metodo_pago metodo_pago) {
        List<Metodo_pago> listadometodo_pago = metodo_pagoServicio.listarTodos();
        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Metodos De Pago | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE METODOS DE PAGO");
        modelo.addAttribute("pagina", "metodospg");
        modelo.addAttribute("metodos_pagos", listadometodo_pago);
        modelo.addAttribute("metodo_pago", metodo_pago);

        return "administrador/indexAdmin";
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/administrador/gestion/metodospg/guardar")
    public String guardarMet(@ModelAttribute Metodo_pago metodo_pago) {
        metodo_pagoServicio.guardarMet(metodo_pago);
        return "redirect:/administrador/gestion/metodospg";
    }
    
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/metodospg/eliminar/{cod_metodo}")
    public String eliminarPro(@PathVariable("cod_metodo") Integer cod_metodo) {
        metodo_pagoServicio.eliminarMet(cod_metodo);
        return "redirect:/administrador/gestion/metodospg";
    }
    //FINAL GESTION DE METODOS DE PAGO
}   
    
