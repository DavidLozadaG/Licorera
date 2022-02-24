package com.tienda.licorera.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ControladorAdmin {

    @GetMapping("/administrador")
    public String home(Model modelo) {
        modelo.addAttribute("cabecera", "Admin MaxLicor's");
        modelo.addAttribute("pagina", "administrador");
        return "administrador/indexAdmin";
    }

}
