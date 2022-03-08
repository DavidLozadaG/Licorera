package com.tienda.licorera.controlador;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class ControladorUsuario {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private IRolServicio rolServicio;

    //INICIO GESTION DE USUARIO 
    @GetMapping("/administrador/gestion/usuarios")
    public String listarUsuarios(Model modelo, Usuario usuario) {
        List<Usuario> listadoUsuario = usuarioServicio.listarTodos();
        List<Rol> listadoRol = rolServicio.ListarTodos();

        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Lista Usuarios | Admin MaxLicor's");
        modelo.addAttribute("titulo", "LISTA DE USUARIOS");
        modelo.addAttribute("pagina", "usuarios");
        modelo.addAttribute("roles", listadoRol);
        modelo.addAttribute("usuarios", listadoUsuario);
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("edad", usuarioServicio.calcularEdad(listadoUsuario.iterator().next().getFecha_nac()));
        return "administrador/indexAdmin";
    }
    /*
    @PostMapping("/administrador/gestion/usuarios/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        String clave = generarPassword();
        usuario.setClave(encriptar(clave));
        usuarioServicio.guardar(usuario);
        enviarEmail(usuario.getEmail(), usuario.getNombres(), clave);
        return "redirect:/administrador/gestion/usuarios";
    }

    @GetMapping("/administrador/gestion/usuarios/eliminar/{cedula}")
    public String eliminar(@PathVariable("cedula") String cedula) {
        usuarioServicio.eliminar(cedula);

        return "redirect:/administrador/gestion/usuarios";
    }
    */
    //FINAL GESTION USUARIO 
    /*
    //Metodo para crear una contraseña al usuario creado por el administrador
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


    //Metodo para enviar un email con la contraseña al usuario que fue creado por el administrador
    public void enviarEmail(String email, String nombres, String clave) {
        emailServicio.sendEmail(email,
                "Bienvenido a MaxLicor's " + nombres,
                "Su clave para iniciar sesion es:" + clave
                        + "\n Le sugerimos que una vez inicie sesión, realize el cambio de contraseña.");
    }
    */

}
