package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Pedido;

public interface IPedidoServicio {
    public List<Pedido> listarTodos();

    public void guardarPedido(Pedido pedido);

    public void buscarPedido(int cod_pedido);

    public void eliminarPedido(int cod_pedido);
    
}
