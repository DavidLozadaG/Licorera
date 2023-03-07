package com.tienda.licorera.controlador;

import com.tienda.licorera.dto.CambiarClave;
import com.tienda.licorera.dto.CrearPedido;
import com.tienda.licorera.dto.IngresarDetalle;
import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.modelo.Detalle;
import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.modelo.Metodo_pago;
import com.tienda.licorera.modelo.Pedido;
import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.sevicio.ICategoriaServicio;
import com.tienda.licorera.sevicio.IDetalleServicio;
import com.tienda.licorera.sevicio.IEstadoPedido;
import com.tienda.licorera.sevicio.ILicorServicio;
import com.tienda.licorera.sevicio.IMetodoPgServicio;
import com.tienda.licorera.sevicio.IPedidoServicio;
import com.tienda.licorera.sevicio.IRolServicio;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ControladorUsuario {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private ICategoriaServicio categoriaServicio;

    @Autowired
    private ILicorServicio licorServicio;

    @Autowired
    private IRolServicio rolServicio;

    @Autowired
    private IMetodoPgServicio metodoServicio;

    @Autowired
    private IPedidoServicio pedidoServicio;

    @Autowired
    private IEstadoPedido estado_pedidoServicio;

    @Autowired
    private IDetalleServicio detalleServicio;

    @PreAuthorize("hasAnyRole('CLIENTE' OR 'ADMINISTRADOR')")
    @PostMapping("/crearPedido")
    public ResponseEntity<String> crearPedido(@Valid @RequestBody CrearPedido crearPedido, Errors errors) {
        try {
            if (errors.hasErrors()) {
                String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));
                throw new Exception(result);
            }
            Pedido pedido = new Pedido();
            System.out.println(crearPedido.toString());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            pedido.setFecha_pedido(Timestamp.valueOf(dtf.format(LocalDateTime.now())));
            pedido.setCedula(usuarioServicio.buscarPorCedula(crearPedido.getCedula()));
            pedido.setCod_estado(estado_pedidoServicio.buscarEstado_pedido(crearPedido.getCod_estado()));
            pedido.setCod_metodo(metodoServicio.buscarMet(crearPedido.getCod_metodo()));
            pedidoServicio.guardarPedido(pedido);
            System.out.println("*********DESPUES Este es el codigo del pedido***********"+pedido.getCod_pedido());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @PreAuthorize("hasAnyRole('CLIENTE' OR 'ADMINISTRADOR')")
    @PostMapping("/ingresarDetalles")
    public ResponseEntity<String> ingresarDetalles(@Valid @RequestBody IngresarDetalle ingresarDetalle, Errors errors) {
        Pedido pedido = new Pedido();
        Detalle detalle = new Detalle();
        try {
            if (errors.hasErrors()) {
                String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));
                throw new Exception(result);
            }
           
            pedido = (pedidoServicio.listarTodos().get(pedidoServicio.listarTodos().size()-1));
            System.out.println("*********Este es el codigo del pedido***********"+pedido.getCod_pedido());
            detalle.setCod_pedido(pedido);
            detalle.setCod_licor(licorServicio.buscarLicor(ingresarDetalle.getCod_licor()));
            detalle.setCantidad(ingresarDetalle.getCantidad());
            detalleServicio.guardarDetalle(detalle);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @PreAuthorize("hasAnyRole('CLIENTE' OR 'ADMINISTRADOR')")
    @GetMapping("/perfil")
    public String perfil(Authentication auth, HttpSession session, Model model, Usuario usuario) {
        String email = auth.getName();
        usuario = usuarioServicio.buscarPorEmail(email);
        model.addAttribute("usuario", usuario);
        model.addAttribute("cabecera", "Perfil | MaxLicor's");
        model.addAttribute("titulo", "Perfil");
        List<Metodo_pago> listadoMetodos = metodoServicio.listarTodos();
        model.addAttribute("metodospago", listadoMetodos);
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        model.addAttribute("categorias", listadoCategorias);
        model.addAttribute("CambiarClave", new CambiarClave(usuario.getCedula()));
        return "usuario/perfil";
    }

    @PreAuthorize("hasAnyRole('CLIENTE' OR 'ADMINISTRADOR')")
    @PostMapping("/perfil/cambiarClave")
    public ResponseEntity<String> cambiarClave(@Valid @RequestBody CambiarClave cambiarClave, Errors errors) {
        try {
            if (errors.hasErrors()) {
                String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));
                throw new Exception(result);
            }
            usuarioServicio.cambiarClave(cambiarClave);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @PreAuthorize("hasAnyRole('CLIENTE' OR 'ADMINISTRADOR')")
    @PostMapping("/perfil/actualizar")
    public String editarUsuario(Authentication auth, HttpSession session, Model model, Usuario usuario)
            throws ErrorServicio {
        try {
            String email = auth.getName();
            Usuario sesion = usuarioServicio.buscarPorEmail(email);
            usuario.setCedula(sesion.getCedula());
            usuario.setEmail(sesion.getEmail());
            usuario.setCod_rol(sesion.getCod_rol());
            usuario.setFecha_nac(sesion.getFecha_nac());
            usuario.setClave(sesion.getClave());
            usuarioServicio.editar(usuario);
            if (session.getAttribute("usuario") != null) {
                usuario.setClave(null);
                session.setAttribute("usuario", usuario);
            }
        } catch (ErrorServicio ex) {
            System.out.println("Hay errores en el formulario:" + ex.getMessage());
            model.addAttribute("cabecera", "Perfil | MaxLicor's");
            model.addAttribute("titulo", "Perfil");
            model.addAttribute("usuario", usuario);
            List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
            model.addAttribute("categorias", listadoCategorias);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("CambiarClave", new CambiarClave(usuario.getCedula()));
            return "usuario/perfil";
        }
        return "redirect:/perfil";
    }

    // METODO PARA LISTAR LOS LICORES EN EL HOME
    @PreAuthorize("hasAnyRole('CLIENTE' OR 'ADMINISTRADOR')")
    @GetMapping("/licorera")
    public String listarLicores(Authentication auth, HttpSession session, Model model) {
        model.addAttribute("cabecera", "Licores Disponibles | MaxLicor's");
        model.addAttribute("titulo", "LICORES");
        List<Categoria> listadoCategorias = categoriaServicio.listarTodas();
        List<Licor> listadoLicores = licorServicio.listarTodos();
        model.addAttribute("categorias", listadoCategorias);
        model.addAttribute("licores", listadoLicores);
        List<Metodo_pago> listadoMetodos = metodoServicio.listarTodos();
        model.addAttribute("metodospago", listadoMetodos);
        String email = auth.getName();
        if (session.getAttribute("usuario") == null) {
            Usuario usuario = usuarioServicio.buscarPorEmail(email);
            usuario.setClave(null);
            session.setAttribute("usuario", usuario);
        }
        return "usuario/indexUsuario";
    }

    // INICIO GESTION DE USUARIO
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador/gestion/usuarios")
    public String listarUsuarios(Model modelo, Usuario usuario) {
        List<Usuario> listadoUsuario = usuarioServicio.listarTodos();
        List<Rol> listadoRol = rolServicio.ListarTodos();
        for (int j = 0; j < listadoUsuario.size(); j++) {
            Usuario user = usuarioServicio.buscarPorCedula(listadoUsuario.get(j).getCedula());
            user.setEdad(usuarioServicio.calcularEdad(user.getFecha_nac()));
        }
        modelo.addAttribute("navbar", "Admin MaxLicor's");
        modelo.addAttribute("cabecera", "Lista Usuarios | Admin MaxLicor's");
        modelo.addAttribute("titulo", "LISTA DE USUARIOS");
        modelo.addAttribute("pagina", "usuarios");
        modelo.addAttribute("roles", listadoRol);
        modelo.addAttribute("usuarios", listadoUsuario);
        modelo.addAttribute("usuario", usuario);
        return "administrador/indexAdmin";
    }

    /*
     * @PostMapping("/administrador/gestion/usuarios/guardar")
     * public String guardar(@ModelAttribute Usuario usuario) {
     * String clave = generarPassword();
     * usuario.setClave(encriptar(clave));
     * usuarioServicio.guardar(usuario);
     * enviarEmail(usuario.getEmail(), usuario.getNombres(), clave);
     * return "redirect:/administrador/gestion/usuarios";
     * }
     * 
     * @GetMapping("/administrador/gestion/usuarios/eliminar/{cedula}")
     * public String eliminar(@PathVariable("cedula") String cedula) {
     * usuarioServicio.eliminar(cedula);
     * 
     * return "redirect:/administrador/gestion/usuarios";
     * }
     */
    // FINAL GESTION USUARIO
    /*
     * //Metodo para crear una contraseña al usuario creado por el administrador
     * public String generarPassword() {
     * String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
     * String CHAR_UPPER = CHAR_LOWER.toUpperCase();
     * String NUMBER = "0123456789";
     * int length = 9;
     * String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
     * SecureRandom random = new SecureRandom();
     * if (length < 1)
     * throw new IllegalArgumentException();
     * StringBuilder sb = new StringBuilder(length);
     * for (int i = 0; i < length; i++) {
     * // 0-62 (exclusive), retornos aleatorios 0-61
     * int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
     * char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
     * sb.append(rndChar);
     * }
     * return sb.toString();
     * }
     * 
     * 
     * //Metodo para enviar un email con la contraseña al usuario que fue creado por
     * el administrador
     * public void enviarEmail(String email, String nombres, String clave) {
     * emailServicio.sendEmail(email,
     * "Bienvenido a MaxLicor's " + nombres,
     * "Su clave para iniciar sesion es:" + clave
     * +
     * "\n Le sugerimos que una vez inicie sesión, realize el cambio de contraseña."
     * );
     * }
     */

}
