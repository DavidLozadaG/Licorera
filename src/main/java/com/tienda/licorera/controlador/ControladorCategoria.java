package com.tienda.licorera.controlador;

import java.util.List;

import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.sevicio.ICategoriaServicio;
import com.tienda.licorera.sevicio.ILicorServicio;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ICategoriaServicio  categoriaServicio;
    
    //Metodo para listar el licor segun su categoria
    @GetMapping("/categoria/{nomb_cat}")
    public String listarLicoresPorCat(@PathVariable("nomb_cat") String nomb_cat,Model model , Usuario usuario){
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera",nomb_cat+" | MaxLicor's");
        model.addAttribute("titulo",nomb_cat);
        model.addAttribute("categoria",nomb_cat);
        List<Categoria>listadoCategorias=categoriaServicio.listarTodas();
        Categoria categoria=categoriaServicio.buscarPorNombre(nomb_cat);
        List<Licor>listadoLicores=licorServicio.buscarPorCategoria(categoria.getCod_cat());
        model.addAttribute("licores",listadoLicores);
        model.addAttribute("categorias",listadoCategorias);
        System.out.println(nomb_cat);
        return "usuario/categoria";
    }

     //INICIO GESTION DE CATEGORIAS
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
 
     @PostMapping("/administrador/gestion/categorias/guardar")
     public String guardarCategoria(@ModelAttribute Categoria categoria) {
         categoriaServicio.guardarCategoria(categoria);
         return "redirect:/administrador/gestion/categorias";
     }
 
     @GetMapping("/administrador/gestion/categorias/eliminar/{cod_cat}")
     public String eliminarCategoria(@PathVariable("cod_cat") int cod_cat) {
         categoriaServicio.eliminarCat(cod_cat);
         return "redirect:/administrador/gestion/categorias";
     }
     //FINAL GESTION DE CATEGORIAS

}
