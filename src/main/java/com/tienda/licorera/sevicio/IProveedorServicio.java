package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Proveedor;
import com.tienda.licorera.modelo.ProveedorId;

/**
 * 
 * Esta interface define los servicios que tiene el proveedor
 * 
 * @author Beelz
 */
public interface IProveedorServicio {

    /**
     * Metodo que lista todos los proveedores del repositirio
     * 
     * @return retorna la lista con los proveedores
     * @author Beelz
     */
    public List<Proveedor> listarTodos();

    /**
     * Metodo que guarda un proveedor en el repositorio
     * 
     * @param proveedor recibe como parametro el objeto proveedor a guardar
     * @author Beelz
     */
    public void guardarPro(Proveedor proveedor);

    /**
     * Metodo que busca el proveedor por id en el repositirio
     * 
     * @param proveedorId recibe como parametro el id del proveedor a buscar
     * @return retorna el objeto encontrado
     * @author Beelz
     */
    public Proveedor buscarPro(ProveedorId proveedorId);

    /**
     * Metodo que elimina un proveedor del repositorio
     * 
     * @param proveedorId recibe como parametro el id del proveedor a eliminar
     * @author Beelz
     */
    public void eliminarPro(ProveedorId proveedorId);
}
