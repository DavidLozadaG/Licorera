package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Licor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLicor extends CrudRepository<Licor, Integer>{
    
}
