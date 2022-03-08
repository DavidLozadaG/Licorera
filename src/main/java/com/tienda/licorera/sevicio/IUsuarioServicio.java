package com.tienda.licorera.sevicio;

import java.time.LocalDate;
import java.util.List;
import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Usuario;
/**
 * 
 * Esta interface define los servicios que tiene el usuario 
 * @author Beelz
 */
public interface IUsuarioServicio {

    public List<Usuario>listarTodos();
    public Usuario guardar(Usuario usuario) throws ErrorServicio;
    public Usuario buscarPorEmail(String email);
    public Usuario buscarPorCedula(String cedula);
    public void eliminar(String cedula);
    public void validarUsuario(Usuario usuario) throws ErrorServicio;
    public int calcularEdad(LocalDate fecha_nac);
} 
 