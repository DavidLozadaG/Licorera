package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Rol;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRol extends CrudRepository<Rol, Integer> {
    
}
