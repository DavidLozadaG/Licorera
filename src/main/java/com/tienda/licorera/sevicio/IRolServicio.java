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
    /**
     * Metodo que lista todos los roles del repositirio
     * 
     * @return retorna la lista de los roles
     * @author Beelz
     */
    public List<Rol> ListarTodos();

    /**
     * Metodo que guarda un rol en el repositirio
     * 
     * @param cod_rol recibe como parametro el objeto rol a guardar
     * @author Beelz
     */
    public void guardar(Rol rol);

    /**
     * Metodo que busca un rol en el repositirio
     * 
     * @param cod_rol recibe como parametro el codigo del rol a buscar
     * @return retorna el rol encontrado
     * @author Beelz
     */
    public Rol buscarRol(int cod_rol);

    /**
     * Metodo que elimina un rol del repositirio
     * 
     * @param cod_rol recibe como parametro el codigo del rol a eliminar
     * @author Beelz
     */
    public void eliminar(int cod_rol);
}
