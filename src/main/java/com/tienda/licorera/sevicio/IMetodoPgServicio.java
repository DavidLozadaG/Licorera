package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Metodo_pago;

public interface IMetodoPgServicio {
    
    public List<Metodo_pago>listarTodos();
    public void guardarMet(Metodo_pago metodo);
    public Metodo_pago buscarMet(int cod_metodo);
    public void eliminarMet(int cod_metodo);

}
