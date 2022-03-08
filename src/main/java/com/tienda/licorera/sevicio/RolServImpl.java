package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.repositorio.RepositorioRol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface IRolServicio.
 * 
 * @author Beelz
 */
@Service
public class RolServImpl implements IRolServicio{
    @Autowired
    private RepositorioRol rolRepositorio;

    
    /** 
    * Metodo que lista todos los roles del repositirio
     * @return retorna la lista de los roles
     * @author Beelz
     */
    @Override
    public List<Rol> ListarTodos() {
       
        return (List<Rol>)rolRepositorio.findAll();
    }

    
    /** 
     * Metodo que busca un rol en el repositirio
     * @param cod_rol recibe como parametro el codigo del rol a buscar
     * @return retorna el rol encontrado
     * @author Beelz
     */
    @Override
    public Rol buscarRol(int cod_rol) {
       
        return rolRepositorio.findById(cod_rol).orElse(null);
    }

    
    /** 
     * Metodo que elimina un rol del repositirio
     * @param cod_rol recibe como parametro el codigo del rol a eliminar
     * @author Beelz
     */
    @Override
    public void eliminar(int cod_rol) {
       rolRepositorio.deleteById(cod_rol);
        
    }

    
    /** 
     * Metodo que guarda un rol en el repositirio
     * @param cod_rol recibe como parametro el objeto rol a guardar
     * @author Beelz
     */
    @Override
    public void guardar(Rol rol) {
        rolRepositorio.save(rol);
        
    }
}
