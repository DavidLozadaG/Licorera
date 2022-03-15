package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface RepositorioUsuario extends CrudRepository<Usuario, String>{
    
    public Usuario findByEmail(String email);

    @Query("select u from #{#entityName} u where u.restablecer_clave_token= ?1")
    public Usuario findByRestablecer_clave_token(String token);
} 