package com.tienda.licorera.controlador;

import java.util.List;

import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.modelo.Metodo_pago;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.sevicio.ICategoriaServicio;
import com.tienda.licorera.sevicio.ILicorServicio;
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
public class ControladorCategoria {

    @Autowired
    private ILicorServicio licorServicio;

    @Autowired
    private ICategoriaServicio categoriaServicio;
    
    @Autowired
    private IMetodoPgServicio metodoServicio;
    
    // Metodo para listar el licor segun su categoria
    @GetMapping("/categoria/{nomb_cat}")
    public String listarLicoresPorCat(@PathVariable("nomb_cat") String nomb_cat, Model model, Usuario usuario) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", nomb_cat + " | MaxLicor's");
        model.addAttribute("titulo", nomb_cat);
        model.addAttribute("categoria", nomb_cat);
        List<Metodo_pago> listadoMetodos = metodoServicio.listarTodos();
        model.addAttribute("metodospago",listadoMetodos);
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        Categoria categoria = categoriaServicio.buscarPorNombre(nomb_cat);
        List<Licor> listadoLicores = licorServicio.buscarPorCategoria(categoria.getCod_cat());
        model.addAttribute("licores", listadoLicores);
        model.addAttribute("categorias", listadoCategorias);
        System.out.println(nomb_cat);
        return "publico/categoria";
    }

    // INICIO GESTION DE CATEGORIAS
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/categorias")
    public String gestionCategorias(Model modelo, Categoria categoria) {

        List<Categoria> listadoCategoria = categoriaServicio.listarTodas();
        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Categorias | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE CATEGORIAS");
        modelo.addAttribute("pagina", "categorias");
        modelo.addAttribute("categorias", listadoCategoria);
        modelo.addAttribute("categoria", categoria);
        return "administrador/indexAdmin";
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/administrador/gestion/categorias/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria, Model modelo) {
        try {
            categoriaServicio.guardarCategoria(categoria);
        } catch (ErrorServicio ex) {
            System.out.println("Hay errores en el formulario:" + ex.getMessage());
            modelo.addAttribute("categoria", categoria);
            modelo.addAttribute("cabecera", "Gestión Categorias | Admin MaxLicor's");
            modelo.addAttribute("titulo", "GESTIÓN DE CATEGORIAS");
            modelo.addAttribute("pagina", "categorias");
            List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
            modelo.addAttribute("categorias", listadoCategorias);
            modelo.addAttribute("error", ex.getMessage());
            return "administrador/indexAdmin";
        }
        return "redirect:/administrador/gestion/categorias";
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/categorias/eliminar/{cod_cat}")
    public String eliminarCategoria(@PathVariable("cod_cat") int cod_cat, Model modelo) {
        try {
            categoriaServicio.eliminarCat(cod_cat); 
        } catch (ErrorServicio ex) {
            System.out.println("Hay errores en el formulario:" + ex.getMessage());
            return "redirect:/administrador/gestion/categorias";
        }
        return "redirect:/administrador/gestion/categorias";
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/categorias/editar/{cod_cat}")
    public String editarCategoria(@PathVariable("cod_cat") int cod_cat, Model modelo) {
        Categoria categoria = categoriaServicio.buscarCat(cod_cat);
        List<Categoria> listadoCategoria = categoriaServicio.listarTodas();
        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Categorias | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE CATEGORIAS");
        modelo.addAttribute("categoria", categoria);
        modelo.addAttribute("categorias", listadoCategoria);
        modelo.addAttribute("pagina", "categorias");
        return "administrador/indexAdmin";
    }
    
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/administrador/gestion/categorias/actualizar")
    public String actualizarCategoria(Categoria categoria, Model modelo) {
        try {
            categoriaServicio.actualizarCategoria(categoria);
        } catch (ErrorServicio ex) {
            System.out.println("Hay errores en el formulario:" + ex.getMessage());
            modelo.addAttribute("categoria", categoria);
            modelo.addAttribute("cabecera", "Gestión Categorias | Admin MaxLicor's");
            modelo.addAttribute("titulo", "GESTIÓN DE CATEGORIAS");
            modelo.addAttribute("pagina", "categorias");
            List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
            modelo.addAttribute("categorias", listadoCategorias);
            modelo.addAttribute("error", ex.getMessage());
            return "administrador/indexAdmin";
        }
        return "redirect:/administrador/gestion/categorias";
    }
    // FINAL GESTION DE CATEGORIAS

}
