package com.tienda.licorera.controlador;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.sevicio.ICategoriaServicio;
import com.tienda.licorera.sevicio.IEmailServicio;
import com.tienda.licorera.sevicio.ILicorServicio;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    @Autowired
    private IEmailServicio emailServicio;

    //INICIO GESTION DE USUARIO 
    @GetMapping("/administrador/gestion/usuarios")
    public String gestionUsuarios(Model modelo, Usuario usuario) {
        List<Usuario> listadoUsuario = usuarioServicio.listarTodos();
        List<Rol> listadoRol = rolServicio.ListarTodos();

        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Gestión Usuarios | Admin MaxLicor's");
        modelo.addAttribute("titulo", "GESTIÓN DE USUARIOS");
        modelo.addAttribute("pagina", "usuarios");
        modelo.addAttribute("roles", listadoRol);
        modelo.addAttribute("usuarios", listadoUsuario);
        modelo.addAttribute("usuario", usuario);

        return "administrador/indexAdmin";
    }

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
    //FINAL GESTION USUARIO 

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


    //Metodo para guardar los datos de un cliente cuando este se registra
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
        return "redirect:/home";
    }


    @GetMapping("/carrito")
    public String carrito(Model model, Usuario usuario){
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera","Carrito | MaxLicor's");
        model.addAttribute("titulo","Carrito");
        List<Categoria>listadoCategorias=categoriaServicio.listarTodas();
        model.addAttribute("categorias",listadoCategorias);
        return "/usuario/carrito";
    }
    
    //Metodo para encripar la contraseña del usuario con el metodo SHA-256
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
