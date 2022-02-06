package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Categoria;
import com.tienda.licorera.repositorio.RepositorioCategoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServImpl implements ICategoriaServicio {

    @Autowired
    private RepositorioCategoria categoriaRepositorio;

    @Override
    public Categoria buscarCat(int cod_cat) {
        
        return categoriaRepositorio.findById(cod_cat).orElse(null);
    }

    @Override
    public void eliminarCat(int cod_cat) {
        categoriaRepositorio.deleteById(cod_cat);
    }

    @Override
    public void guardarCategoria(Categoria categoria) {
        categoriaRepositorio.save(categoria);
    }

    @Override
    public List<Categoria> listarTodas() {
        
        return (List<Categoria>)categoriaRepositorio.findAll();
    }

    
}
