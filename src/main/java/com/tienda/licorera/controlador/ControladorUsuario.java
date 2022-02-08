package com.tienda.licorera.controlador;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

@Controller
public class ControladorUsuario {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private IRolServicio rolServicio;

    @PostMapping("/cliente/registro/crear")
    public String guardar(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        Rol rol = rolServicio.buscarRol(2);
        usuario.setCod_rol(rol);
        usuario.toString();
        if(result.hasErrors()){
            model.addAttribute("usuario", usuario);
            System.out.println("Hay errores en el formulario:"+usuario.toString());
            return "home";
        }
        //usuario.setClave(encriptar(usuario.getClave()));
        //usuarioServicio.guardar(usuario);
        return "redirect:/home";
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
