package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Detalle;
import com.tienda.licorera.modelo.DetalleId;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioDetalle extends CrudRepository<Detalle, DetalleId> {
    
}
