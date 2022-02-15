package com.tienda.licorera.controlador;

import java.util.List;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.sevicio.IRolServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorRol {
    @Autowired
    private IRolServicio rolServicio;

    //INICIO GESTION DE ROLES
    @GetMapping("/administrador/gestion/roles")
    public String gestionRoles(Model modelo, Rol rol) {
        List<Rol> listadoRol = rolServicio.ListarTodos();
        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Roles | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE ROLES");
        modelo.addAttribute("pagina", "roles");
        modelo.addAttribute("roles", listadoRol);
        modelo.addAttribute("rol", rol);

        return "administrador/indexAdmin";
    }

    @PostMapping("/administrador/gestion/roles/guardar")
    public String guardarRol(@ModelAttribute Rol rol) {
        rolServicio.guardar(rol);
        return "redirect:/administrador/gestion/roles";
    }

    @GetMapping("/administrador/gestion/roles/eliminar/{cod_rol}")
    public String eliminarRol(@PathVariable("cod_rol") int cod_rol) {
        rolServicio.eliminar(cod_rol);
        return "redirect:/administrador/gestion/roles";
    }
    //FINAL GESTION DE ROLES
}
