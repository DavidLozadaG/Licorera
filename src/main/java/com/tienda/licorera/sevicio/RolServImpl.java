package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Rol;
import com.tienda.licorera.repositorio.RepositorioRol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServImpl implements IRolServicio{
    @Autowired
    private RepositorioRol rolRepositorio;

    @Override
    public List<Rol> ListarTodos() {
       
        return (List<Rol>)rolRepositorio.findAll();
    }

    @Override
    public Rol buscarRol(int cod_rol) {
       
        return rolRepositorio.findById(cod_rol).orElse(null);
    }

    @Override
    public void eliminar(int cod_rol) {
       rolRepositorio.deleteById(cod_rol);
        
    }

    @Override
    public void guardar(Rol rol) {
        rolRepositorio.save(rol);
        
    }
}
