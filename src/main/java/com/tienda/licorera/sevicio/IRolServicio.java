package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Rol;

/**
 * 
 * Esta interface define los servicios que tiene el rol
 * 
 * @author Beelz
 */
public interface IRolServicio {
    
    public List<Rol>ListarTodos();
    public void guardar(Rol rol);
    public Rol buscarRol(int cod_rol);
    public void eliminar(int cod_rol);
}
