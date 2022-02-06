package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Usuario;

public interface IUsuarioServicio {

    public List<Usuario>listarTodos();
    public void guardar(Usuario persona);
    public Usuario buscarPorCedula(String cedula);
    public void eliminar(String cedula);
} 
 