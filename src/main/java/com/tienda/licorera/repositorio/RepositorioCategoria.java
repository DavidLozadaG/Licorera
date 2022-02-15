package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Categoria;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioCategoria extends CrudRepository<Categoria, Integer> {

    @Transactional(readOnly = true)
    @Query("select u from #{#entityName} u where u.nomb_cat = ?1")
    Categoria findByNomb_cat(String nomb_cat);
}
