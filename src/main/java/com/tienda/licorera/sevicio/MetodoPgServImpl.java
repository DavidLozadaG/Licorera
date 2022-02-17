package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Metodo_pago;
import com.tienda.licorera.repositorio.RepositorioMetodoPg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetodoPgServImpl implements IMetodoPgServicio {
    @Autowired
    private RepositorioMetodoPg metodopgRepositorio;

    @Override
    public Metodo_pago buscarMet(int cod_metodo) {
        
        return metodopgRepositorio.findById(cod_metodo).orElse(null);
    }

    @Override
    public void eliminarMet(int cod_metodo) {
        
        metodopgRepositorio.deleteById(cod_metodo);
    }

    @Override
    public void guardarMet(Metodo_pago metodo) {
        
        metodopgRepositorio.save(metodo);
    }

    @Override
    public List<Metodo_pago> listarTodos() {
       
        return (List<Metodo_pago>)metodopgRepositorio.findAll();
    }
    
    
}
