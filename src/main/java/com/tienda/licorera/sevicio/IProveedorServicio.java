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
    
    public List<Proveedor>listarTodos();
    public void guardarPro(Proveedor proveedor);
    public Proveedor buscarPro(ProveedorId proveedorId);
    public void eliminarPro(ProveedorId proveedorId);
}
