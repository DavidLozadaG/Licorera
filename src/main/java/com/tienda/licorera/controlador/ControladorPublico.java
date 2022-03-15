package com.tienda.licorera.controlador;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.seguridad.Utility;
import com.tienda.licorera.dto.CambiarClave;
import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.sevicio.EmailServImpl;
import com.tienda.licorera.sevicio.ICategoriaServicio;
import com.tienda.licorera.sevicio.ILicorServicio;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.bytebuddy.utility.RandomString;

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

    @Autowired
    EmailServImpl emailServImpl;

    // METODO PARA LISTAR LOS LICORES EN EL HOME
    @GetMapping({ "/", "/home", "/index" })
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

    public void enviarEmail(String email, String nombres, String resetPasswordLink)
            throws UnsupportedEncodingException, MessagingException {
        try {
            emailServImpl.sendEmail(email, nombres, resetPasswordLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new UnsupportedEncodingException("Error durante el envio del correo");
        }
    }

    @PostMapping("/olvidoClave")
    public String procesoRecuperarClave(HttpServletRequest request, Model model, Usuario usuario) {
        String email = request.getParameter("email").toUpperCase();
        String token = RandomString.make(30);
        try {
            usuarioServicio.actualizarTokenClave(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/recuperarContraseña?token=" + token;
            enviarEmail(email, usuarioServicio.buscarPorEmail(email).getNombres(), resetPasswordLink);
            model.addAttribute("message",
                    "Hemos enviado un link para restablecer su contraseña, por favor revise su correo electronico.");
        } catch (Exception ex) {
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
        return "publico/index";
    }

    @GetMapping("/recuperarContraseña")
    public String VerFormRestablecerClave(@Param(value = "token") String token, Model model) {
        Usuario usuario = usuarioServicio.buscarToken(token);
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("cabecera", "Licores Disponibles | MaxLicor's");
        model.addAttribute("titulo", "LICORES");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        List<Licor> listadoLicores = licorServicio.listarTodos();
        model.addAttribute("categorias", listadoCategorias);
        model.addAttribute("licores", listadoLicores);
        model.addAttribute("token", token);
        if (usuario == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        return "publico/recuperarContraseña";
    }

    @PostMapping("/recuperarContraseña")
    public ResponseEntity<String>RestablecerClave(@Valid @RequestBody CambiarClave cambiarClave, Errors errors) {
        try {
            if (errors.hasErrors()) {
                String result = errors.getAllErrors()
                .stream().map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(""));
                throw new Exception(result);
            }
            Usuario usuario = usuarioServicio.buscarToken(cambiarClave.getId());
            usuarioServicio.actualizarClave(usuario,cambiarClave.getClaveNueva(), cambiarClave.getConfirmarClaveNueva());
        } catch (Exception e) {
             return ResponseEntity.badRequest().body(e.getMessage());
        }
     return ResponseEntity.ok("Success");
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

    @GetMapping("/MapaDelSitio")
    public String mapa(Model model, Usuario usuario) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Mapa del sitio | MaxLicor's");
        model.addAttribute("titulo", "Mapa del sitio");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        model.addAttribute("categorias", listadoCategorias);
        return "publico/mapadesitio";
    }

    @GetMapping("/contactoSoporte")
    public String contacto(Model model, Usuario usuario) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Contacto Soporte | MaxLicor's");
        model.addAttribute("titulo", "Contacto Soporte");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        model.addAttribute("categorias", listadoCategorias);
        return "publico/contactoSoporte";
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
