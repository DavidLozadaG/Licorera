package com.tienda.licorera.controlador;

import java.util.List;


import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Licor;

import com.tienda.licorera.sevicio.ICategoriaServicio;
import com.tienda.licorera.sevicio.ILicorServicio;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorPublico {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private IRolServicio rolServicio;

    @Autowired
    private ICategoriaServicio categoriaServicio;

    @Autowired
    private ILicorServicio licorServicio;

    // METODO PARA LISTAR LOS LICORES EN EL HOME
    @GetMapping({"/", "/home", "/index" })
    public String listarLicores(Model model, Usuario usuario) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Licores Disponibles | MaxLicor's");
        model.addAttribute("titulo", "LICORES");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        List<Licor> listadoLicores = licorServicio.listarTodos();
        model.addAttribute("categorias", listadoCategorias);
        model.addAttribute("licores", listadoLicores);
        return "publico/index";
    }

    // Metodo para guardar los datos de un cliente cuando este se registra
    @PostMapping("/registro")
    public String guardar(@ModelAttribute("usuario") Usuario usuario, Model model) {
        Rol rol = rolServicio.buscarRol(2);
        usuario.setCod_rol(rol);
        // usuario.toString();
        try {
            usuarioServicio.guardar(usuario);
        } catch (ErrorServicio ex) {
            System.out.println("Hay errores en el formulario:" + ex.getMessage());
            model.addAttribute("usuario", usuario);
            model.addAttribute("cabecera", "Licores Disponibles | MaxLicor's");
            model.addAttribute("titulo", "LICORES");
            List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
            List<Licor> listadoLicores = licorServicio.listarTodos();
            model.addAttribute("categorias", listadoCategorias);
            model.addAttribute("licores", listadoLicores);
            model.addAttribute("error", ex.getMessage());
            return "publico/index";
        }

        return "redirect:/registro/realizado";
    }

    @GetMapping("/registro")
    public String registro(Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Licores Disponibles | MaxLicor's");
        model.addAttribute("titulo", "LICORES");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        List<Licor> listadoLicores = licorServicio.listarTodos();
        model.addAttribute("categorias", listadoCategorias);
        model.addAttribute("licores", listadoLicores);
        return "publico/index";
    }

    @GetMapping("/registro/realizado")
    public String registroRealizo(Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Licores Disponibles | MaxLicor's");
        model.addAttribute("titulo", "LICORES");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        List<Licor> listadoLicores = licorServicio.listarTodos();
        model.addAttribute("categorias", listadoCategorias);
        model.addAttribute("licores", listadoLicores);
        return "publico/index";
    }

    @GetMapping("/login")
    public String login(Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Licores Disponibles | MaxLicor's");
        model.addAttribute("titulo", "LICORES");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        List<Licor> listadoLicores = licorServicio.listarTodos();
        model.addAttribute("categorias", listadoCategorias);
        model.addAttribute("licores", listadoLicores);
        return "publico/index";
    }

    @GetMapping("/carrito")
    public String carrito(Model model, Usuario usuario) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Carrito | MaxLicor's");
        model.addAttribute("titulo", "Carrito");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        model.addAttribute("categorias", listadoCategorias);
        return "usuario/carrito";
    }

    @GetMapping("/MapaDelSitio")
    public String mapa(Model model, Usuario usuario) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Mapa del sitio | MaxLicor's");
        model.addAttribute("titulo", "Mapa del sitio");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        model.addAttribute("categorias", listadoCategorias);
        return "publico/mapadesitio";
    }

    // Metodo para encripar la contraseña del usuario con el metodo SHA-256
    /*
     * public String encriptar(String password) {
     * MessageDigest md = null;
     * try {
     * md = MessageDigest.getInstance("SHA-256");
     * } catch (NoSuchAlgorithmException e) {
     * e.printStackTrace();
     * return null;
     * }
     * byte[] hash = md.digest(password.getBytes());
     * StringBuffer sb = new StringBuffer();
     * 
     * for (byte b : hash) {
     * sb.append(String.format("%02x", b));
     * }
     * return sb.toString();
     * }
     */
}
