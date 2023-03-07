package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Detalle;
import com.tienda.licorera.modelo.DetalleId;

public interface IDetalleServicio {

    public List<Detalle> listarTodos();

    public void guardarDetalle(Detalle detalle);

    public void buscarDetalle(DetalleId detalleId);

    public void eliminarDetalle(DetalleId detalleId);
}
