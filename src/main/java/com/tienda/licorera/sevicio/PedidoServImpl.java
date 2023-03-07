package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Pedido;
import com.tienda.licorera.repositorio.RepositorioPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServImpl implements IPedidoServicio{

    @Autowired
    private RepositorioPedido pedidoRepositorio;

    @Override
    public List<Pedido> listarTodos() {
        
        return (List<Pedido>)pedidoRepositorio.findAll();
    }

    @Override
    public void guardarPedido(Pedido pedido) {
        pedidoRepositorio.save(pedido);
        
    }

    @Override
    public void buscarPedido(int cod_pedido) {
        pedidoRepositorio.findById(cod_pedido).orElse(null);
    }

    @Override
    public void eliminarPedido(int cod_pedido) {
        pedidoRepositorio.deleteById(cod_pedido);
    
    }
    
}
