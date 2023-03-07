package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Pedido;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioPedido  extends CrudRepository<Pedido , Integer> {
    
}
