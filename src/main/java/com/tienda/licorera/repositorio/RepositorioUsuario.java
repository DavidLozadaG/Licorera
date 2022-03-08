package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends CrudRepository<Usuario, String>{
    
    public Usuario findByEmail(String email);
} 