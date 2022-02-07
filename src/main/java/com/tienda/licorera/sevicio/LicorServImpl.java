package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Licor;
import com.tienda.licorera.repositorio.RepositorioLicor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicorServImpl implements ILicorServicio {
    
    @Autowired
    private RepositorioLicor licorRepositorio;

    @Override
    public Licor buscarLicor(int cod_licor) {

        return licorRepositorio.findById(cod_licor).orElse(null);
    }

    @Override
    public void eliminarLicor(int cod_licor) {

        licorRepositorio.deleteById(cod_licor);
    }

    @Override
    public void guardarLicor(Licor licor) {

        licorRepositorio.save(licor);
    }

    @Override
    public List<Licor> listarTodos() {
        
        return (List<Licor>)licorRepositorio.findAll();
    }

    @Override
    public Licor buscarPorNombre(String nomb_licor) {
        return  licorRepositorio.findByNomb_licor(nomb_licor);
    }

    @Override
    public List<Licor> buscarPorCategoria(int cod_cat) {
        
        return (List<Licor>)licorRepositorio.findByCod_cat(cod_cat);
    }

    
}
