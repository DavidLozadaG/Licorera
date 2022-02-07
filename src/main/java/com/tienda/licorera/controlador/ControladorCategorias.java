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
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ControladorCategorias {

    @Autowired
    private ILicorServicio licorServicio;

    @Autowired
    private ICategoriaServicio  categoriaServicio;
    
    @GetMapping("/{nomb_cat}")
    public String listarLicoresPorCat(@PathVariable("nomb_cat") String nomb_cat,Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera",nomb_cat+" | MaxLicor's");
        model.addAttribute("titulo",nomb_cat+" PAGINA");
        List<Categoria>listadoCategorias=categoriaServicio.listarTodas();
        Categoria categoria=categoriaServicio.buscarPorNombre(nomb_cat);
        List<Licor>listadoLicores=licorServicio.buscarPorCategoria(categoria.getCod_cat());
        model.addAttribute("licores",listadoLicores);
        model.addAttribute("categorias",listadoCategorias);
        return "categoria";
    }


    
}
