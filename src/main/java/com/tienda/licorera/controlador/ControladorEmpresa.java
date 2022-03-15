package com.tienda.licorera.controlador;

import java.util.List;

import com.tienda.licorera.modelo.Empresa;
import com.tienda.licorera.sevicio.IEmpresaServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorEmpresa {
    @Autowired
    private IEmpresaServicio empresaServicio;

    // INICIO GESTION DE EMPRESAS

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/empresas")
    public String gestionRoles(Model modelo, Empresa empresa) {
        List<Empresa> listadoEmp = empresaServicio.listarTodas();
        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Empresas | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE EMPRESAS");
        modelo.addAttribute("pagina", "empresas");
        modelo.addAttribute("empresas", listadoEmp);
        modelo.addAttribute("empresa", empresa);

        return "administrador/indexAdmin";
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/administrador/gestion/empresas/guardar")
    public String guardarRol(@ModelAttribute Empresa empresa) {
        empresaServicio.guardarEmp(empresa);
        return "redirect:/administrador/gestion/empresas";
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/empresas/eliminar/{nit_empresa}")
    public String eliminarRol(@PathVariable("nit_empresa") String nit_empresa) {
        empresaServicio.elimiarEmp(nit_empresa);
        return "redirect:/administrador/gestion/empresas";
    }
    // FINAL GESTION DE EMPRESAS
}
