package com.tienda.licorera.controlador;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.sevicio.ICategoriaServicio;
import com.tienda.licorera.sevicio.ILicorServicio;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

@Controller
public class ControladorUsuario {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private IRolServicio rolServicio;

    @Autowired
    private ICategoriaServicio categoriaServicio;

    @Autowired
    private ILicorServicio licorServicio;
    @PostMapping("/cliente/registro/crear")
    public String guardar(@Valid @ModelAttribute("usuario")Usuario usuario, BindingResult result, Model model) {
        Rol rol = rolServicio.buscarRol(2);
        usuario.setCod_rol(rol);
        usuario.toString();
        if(result.hasErrors()){
            System.out.println("Hay errores en el formulario:"+usuario.toString());
            model.addAttribute("usuario", usuario);
            model.addAttribute("cabecera","Licores Disponibles | MaxLicor's");
            model.addAttribute("titulo","LICORES DISPONIBLES");
            List<Categoria>listadoCategorias=categoriaServicio.listarTodas();
            List<Licor>listadoLicores=licorServicio.listarTodos();
            model.addAttribute("categorias",listadoCategorias);
            model.addAttribute("licores",listadoLicores);
            return "index";
        }
        usuario.setClave(encriptar(usuario.getClave()));
        usuarioServicio.guardar(usuario);
        return "redirect:/index";
    }


    @GetMapping("/carrito")
    public String carrito(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera","Carrito | MaxLicor's");
        model.addAttribute("titulo","Carrito");
        List<Categoria>listadoCategorias=categoriaServicio.listarTodas();
        model.addAttribute("categorias",listadoCategorias);
        return "/usuario/carrito";
    }
    
    public String encriptar(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
