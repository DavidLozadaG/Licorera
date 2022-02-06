package com.tienda.licorera.controlador;

import java.security.SecureRandom;
import java.util.List;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.sevicio.IEmailServicio;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class ControladorAdmin {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private IRolServicio rolServicio;

    @Autowired
    private IEmailServicio emailServicio;

    @GetMapping("")
    public String home(Model modelo) {
        modelo.addAttribute("cabecera", "Admin MaxLicor's");
        modelo.addAttribute("titulo", "Administrador MaxLicors");
        return "/administrador/homeAdmin";

    }

    @GetMapping("/gestion/usuarios")
    public String gestionUsuarios(Model modelo) {
        Usuario usuario = new Usuario();

        List<Usuario> listadoUsuario = usuarioServicio.listarTodos();
        List<Rol> listadoRol = rolServicio.ListarTodos();

        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Usuarios | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE USUARIOS");
        modelo.addAttribute("roles", listadoRol);
        modelo.addAttribute("usuarios", listadoUsuario);
        modelo.addAttribute("usuario", usuario);

        return "/administrador/gestion/usuarios";
    }

    @PostMapping("/gestion/usuarios/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        ControladorUsuario us = new ControladorUsuario();
        String clave=generarPassword();
        usuario.setClave(us.encriptar(clave));
        usuarioServicio.guardar(usuario);
        enviarEmail(usuario.getEmail(),usuario.getNombres(),clave);
        return "redirect:/administrador/gestion/usuarios";
    }

    @GetMapping("/gestion/usuarios/eliminar/{cedula}")
    public String eliminar(@PathVariable("cedula") String cedula) {
        usuarioServicio.eliminar(cedula);

        return "redirect:/administrador/gestion/usuarios";
    }

    public String generarPassword() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";
        int length = 9;
        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();
        if (length < 1)
            throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), retornos aleatorios 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    public void enviarEmail(String email,String nombres,String clave) {
        emailServicio.sendEmail(email,
                "Bienvenido a MaxLicor's " + nombres,
                "Su clave para iniciar sesion es:" + clave
                        + "\n Le sugerimos que una vez inicie sesión, realize el cambio de contraseña.");
    }
}
