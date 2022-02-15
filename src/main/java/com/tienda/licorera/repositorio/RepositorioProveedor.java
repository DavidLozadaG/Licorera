package com.tienda.licorera.repositorio;

import com.tienda.licorera.modelo.Proveedor;
import com.tienda.licorera.modelo.ProveedorId;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProveedor extends CrudRepository<Proveedor, ProveedorId>{


}
