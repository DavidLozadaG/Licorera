package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Licor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioLicor extends CrudRepository<Licor, Integer>{
    
    @Transactional(readOnly = true)
    @Query("select u from #{#entityName} u where u.nomb_licor = ?1")
    Licor findByNomb_licor(String nomb_licor);

    @Transactional(readOnly = true)
    @Query("select u from #{#entityName} u where u.cod_cat.cod_cat = ?1")
    Iterable<Licor> findByCod_cat(int cod_cat);
}
