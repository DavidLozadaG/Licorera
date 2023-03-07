package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Detalle;
import com.tienda.licorera.modelo.DetalleId;
import com.tienda.licorera.repositorio.RepositorioDetalle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleServImpl implements IDetalleServicio{
    @Autowired
    private RepositorioDetalle detalleRepositorio;
    @Override
    public List<Detalle> listarTodos() {
        
        return (List<Detalle>)detalleRepositorio.findAll();
    }

    @Override
    public void guardarDetalle(Detalle detalle) {
        detalleRepositorio.save(detalle);
    }

    @Override
    public void buscarDetalle(DetalleId detalleId) {
        detalleRepositorio.findById(detalleId);
    }

    @Override
    public void eliminarDetalle(DetalleId detalleId) {
        detalleRepositorio.deleteById(detalleId);
    }
    
}
