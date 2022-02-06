package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Usuario;
import com.tienda.licorera.repositorio.RepositorioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServImpl implements IUsuarioServicio{
    
    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    @Override
    public List<Usuario> listarTodos() {
        
        return (List<Usuario>)usuarioRepositorio.findAll();
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {
        
        return usuarioRepositorio.findById(cedula).orElse(null);
    }

    @Override
    public void guardar(Usuario persona) {
        usuarioRepositorio.save(persona);
        
    }

    @Override
    public void eliminar(String cedula) {
        usuarioRepositorio.deleteById(cedula);
        
    }
    
}
