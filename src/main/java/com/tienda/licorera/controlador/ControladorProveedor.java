package com.tienda.licorera.controlador;

import java.util.List;

import com.tienda.licorera.modelo.Empresa;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.modelo.Proveedor;
import com.tienda.licorera.modelo.ProveedorId;
import com.tienda.licorera.sevicio.IEmpresaServicio;
import com.tienda.licorera.sevicio.ILicorServicio;
import com.tienda.licorera.sevicio.IProveedorServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorProveedor {
    
    @Autowired 
    IProveedorServicio proveedorServicio;

    @Autowired 
    ILicorServicio licorServicio;

    @Autowired 
    IEmpresaServicio empresaServicio;

    //INICIO GESTION DE PROVEEDORES
    @GetMapping("/administrador/gestion/proveedores")
    public String gestionPro(Model modelo, Proveedor proveedor) {
        List<Licor> listadoLicor = licorServicio.listarTodos();
        List<Empresa> listadoEmpresa = empresaServicio.listarTodas();
        List<Proveedor> listadoProveedor = proveedorServicio.listarTodos();
        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Proveedores | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE PROVEEDORES");
        modelo.addAttribute("pagina", "proveedores");
        modelo.addAttribute("licores", listadoLicor);
        modelo.addAttribute("empresas", listadoEmpresa);
        modelo.addAttribute("proveedores", listadoProveedor);
        modelo.addAttribute("proveedor", proveedor);

        return "administrador/indexAdmin";
    }

    @PostMapping("/administrador/gestion/proveedores/guardar")
    public String guardarPro(@ModelAttribute Proveedor proveedor) {
        proveedorServicio.guardarPro(proveedor);
        return "redirect:/administrador/gestion/proveedores";
    }

    @GetMapping("/administrador/gestion/proveedores/eliminar/{cod_licor}/{nit_empresa}")
    public String eliminarPro(@PathVariable("cod_licor") Integer cod_licor, @PathVariable("nit_empresa") String nit_empresa, ProveedorId proveedorId) {
        proveedorId.setCod_licor(cod_licor);
        proveedorId.setNit_empresa(nit_empresa);
        proveedorServicio.eliminarPro(proveedorId);
        return "redirect:/administrador/gestion/proveedores";
    }
    //FINAL GESTION DE PROVEEDORES
}

