package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Categoria;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioCategoria extends CrudRepository<Categoria, Integer> {
    
}
