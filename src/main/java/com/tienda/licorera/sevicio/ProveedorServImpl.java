package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Proveedor;
import com.tienda.licorera.modelo.ProveedorId;
import com.tienda.licorera.repositorio.RepositorioProveedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServImpl implements IProveedorServicio{
    @Autowired
    private RepositorioProveedor proveedorRepositorio;

    @Override
    public Proveedor buscarPro(ProveedorId proveedorId) {
        
        return proveedorRepositorio.findById(proveedorId).orElse(null);
    }

    @Override
    public void eliminarPro(ProveedorId proveedorId) {
        
        proveedorRepositorio.deleteById(proveedorId);;
    }

    @Override
    public void guardarPro(Proveedor proveedor) {
       
        proveedorRepositorio.save(proveedor);
    }

    @Override
    public List<Proveedor> listarTodos() {
      
        return (List<Proveedor>)proveedorRepositorio.findAll();
    }

    
}
