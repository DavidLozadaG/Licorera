package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Proveedor;
import com.tienda.licorera.modelo.ProveedorId;
import com.tienda.licorera.repositorio.RepositorioProveedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios de la interface IProveedorServicio.
 * 
 * @author Beelz
 */
@Service
public class ProveedorServImpl implements IProveedorServicio{
    @Autowired
    private RepositorioProveedor proveedorRepositorio;

    
    /** 
     * Metodo que busca el proveedor por id en el repositirio
     * @param proveedorId recibe como parametro el id del proveedor a buscar
     * @return retorna el objeto encontrado
     * @author Beelz
     */
    @Override
    public Proveedor buscarPro(ProveedorId proveedorId) {
        
        return proveedorRepositorio.findById(proveedorId).orElse(null);
    }

    
    /** 
     * Metodo que elimina un proveedor del repositorio
     * @param proveedorId recibe como parametro el id del proveedor a eliminar
     * @author Beelz
     */
    @Override
    public void eliminarPro(ProveedorId proveedorId) {
        
        proveedorRepositorio.deleteById(proveedorId);;
    }

    
    /** 
     * Metodo que guarda un proveedor en el repositorio 
     * @param proveedor recibe como parametro el objeto proveedor a guardar
     * @author Beelz
     */
    @Override
    public void guardarPro(Proveedor proveedor) {
       
        proveedorRepositorio.save(proveedor);
    }

    
    /** 
     * Metodo que lista todos los proveedores del repositirio
     * @return retorna la lista con los proveedores
     * @author Beelz
     */
    @Override
    public List<Proveedor> listarTodos() {
      
        return (List<Proveedor>)proveedorRepositorio.findAll();
    }

    
}
