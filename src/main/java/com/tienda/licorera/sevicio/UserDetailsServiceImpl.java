package com.tienda.licorera.sevicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.repositorio.RepositorioUsuario;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface UserDetailsService.
 * 
 * @author Beelz
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Autowired
    IRolServicio rolServicio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario con el repositorio y si no existe lanzar una exepcion
        email = email.toUpperCase();
        Usuario usuario = repositorioUsuario.findByEmail(email);
        List<GrantedAuthority> roles = new ArrayList<>();
        String rol = rolServicio.buscarRol(usuario.getCod_rol().getCod_rol()).getNomb_rol();
        System.out.print("El rol es:" + rol);
        roles.add(new SimpleGrantedAuthority(rol));
        System.out.print("Entre el usuario es: " + usuario.toString());
        UserDetails userDet = new User(usuario.getEmail(), usuario.getClave(), roles);
        return userDet;
    }
}