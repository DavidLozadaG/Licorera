package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Licor;

public interface ILicorServicio {
    
    public List<Licor>listarTodos();
    public Licor buscarPorNombre(String nomb_licor);
    public List<Licor>buscarPorCategoria(int cod_cat);
    public void guardarLicor(Licor licor);
    public Licor buscarLicor(int cod_licor);
    public void eliminarLicor(int cod_licor);
}
