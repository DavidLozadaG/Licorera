package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Empresa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEmpresa extends CrudRepository<Empresa, String> {
    
}
