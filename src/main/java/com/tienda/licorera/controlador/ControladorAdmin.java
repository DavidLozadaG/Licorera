package com.tienda.licorera.controlador;

import javax.servlet.http.HttpSession;
import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.sevicio.IUsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ControladorAdmin {
    @Autowired
    private IUsuarioServicio usuarioServicio;
    
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/administrador")
    public String home(Authentication auth, HttpSession session,Model modelo) {
        modelo.addAttribute("cabecera", "Admin MaxLicor's");
        modelo.addAttribute("pagina", "administrador");
        String email = auth.getName();
        if(session.getAttribute("usuario")==null){
            Usuario usuario = usuarioServicio.buscarPorEmail(email);
            usuario.setClave(null); 
            session.setAttribute("usuario", usuario);
        }
        return "administrador/indexAdmin";
    }

}
