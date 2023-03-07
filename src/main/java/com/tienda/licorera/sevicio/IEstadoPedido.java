package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Estado_pedido;

public interface IEstadoPedido {
    
    public List<Estado_pedido> listarTodas(Estado_pedido estado_pedido);

    public Estado_pedido buscarEstado_pedido(int cod_estado);

    public Estado_pedido guardarEstado_pedido(Estado_pedido estado_pedido);

    public void eliminarEstado_pedido(int cod_estado);
}
