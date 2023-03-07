package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Estado_pedido;
import com.tienda.licorera.repositorio.RepositorioEstadoPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoPedidoServImpl implements IEstadoPedido{

    @Autowired
    private RepositorioEstadoPedido estado_pedidoRepositorio;

    @Override
    public List<Estado_pedido> listarTodas(Estado_pedido estado_pedido) {
        
        return (List<Estado_pedido>) estado_pedidoRepositorio.findAll();
    }

    @Override
    public Estado_pedido buscarEstado_pedido(int cod_estado) {
        
        return estado_pedidoRepositorio.findById(cod_estado).orElse(null);
    }

    @Override
    public Estado_pedido guardarEstado_pedido(Estado_pedido estado_pedido) {
        
        return estado_pedidoRepositorio.save(estado_pedido);
    }

    @Override
    public void eliminarEstado_pedido(int cod_estado) {
        
        estado_pedidoRepositorio.deleteById(cod_estado);
    }
    
}
